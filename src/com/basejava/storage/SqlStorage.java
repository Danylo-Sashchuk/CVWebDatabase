package com.basejava.storage;

import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.*;
import com.basejava.sql.ExceptionUtil;
import com.basejava.sql.SqlConductor;
import com.basejava.sql.SqlTemplate;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlTemplate sqlTemplate;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlTemplate = new SqlTemplate(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlTemplate.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save resume " + resume);
        sqlTemplate.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name)" +
                                                              "  VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, " +
                                                              "value)   VALUES (?, ?, ?)")) {
                saveContacts(resume, ps);
            }
            saveSections(resume, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete resume: " + uuid);
        sqlTemplate.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            int deleted = ps.executeUpdate();
            if (deleted == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get resume: " + uuid);

        return sqlTemplate.execute("""
                SELECT resume.uuid,
                       resume.full_name,
                       contact.type      AS contact_type,
                       contact.value     AS contact_value,
                       text_section.type AS text_section_type,
                       text_section.text AS text_section_text,
                       list_section.type AS list_section_type,
                       list_section.text AS list_section_text
                  FROM resume
                           LEFT JOIN contact ON resume.uuid = contact.resume_uuid
                           LEFT JOIN text_section ON resume.uuid = text_section.resume_uuid
                           LEFT JOIN list_section ON resume.uuid = list_section.resume_uuid
                 WHERE resume.uuid = ?""", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = new Resume(uuid, rs.getString("full_name"));
            Map<String, ContactType> processedContacts = new HashMap<>();
            Map<String, SectionType> processedSections = new HashMap<>();
            do {
                addContact(resume, processedContacts, rs);
                addTextSection(resume, processedSections, rs);
                addListSection(resume, processedSections, rs);
            } while (rs.next());


            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("get all sorted");
        return sqlTemplate.execute("""
                SELECT resume.uuid,
                       resume.full_name,
                       contact.type      AS contact_type,
                       contact.value     AS contact_value,
                       text_section.type AS text_section_type,
                       text_section.text AS text_section_text,
                       list_section.type AS list_section_type,
                       list_section.text AS list_section_text
                  FROM resume
                           LEFT JOIN contact ON resume.uuid = contact.resume_uuid
                           LEFT JOIN text_section ON resume.uuid = text_section.resume_uuid
                           LEFT JOIN list_section ON resume.uuid = list_section.resume_uuid
                 ORDER BY full_name, uuid
                                """, ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> processedResumes = new LinkedHashMap<>();
            Map<String, ContactType> processedContacts = new HashMap<>();
            Map<String, SectionType> processedSections = new HashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                Resume resume = processedResumes.get(uuid);
                if (!processedResumes.containsKey(uuid)) {
                    resume = new Resume(uuid, fullName);
                    processedResumes.put(uuid, resume);
                }
                addContact(resume, processedContacts, rs);
                addTextSection(resume, processedSections, rs);
                addListSection(resume, processedSections, rs);
            }
            return new ArrayList<>(processedResumes.values());
        });
    }

    @Override
    public int size() {
        return sqlTemplate.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("updating resume: " + resume);
        sqlTemplate.transactionExecute(conn -> {
            updateName(resume, conn);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) " +
                                                              "         VALUES (?, ?, ?)")) {
                saveContacts(resume, ps);
            }
            return null;
        });
    }

    public void updateWithoutDeleting(Resume resume) {
        String uuid = resume.getUuid();
        LOG.info("updating resume: " + resume);
        sqlTemplate.transactionExecute(conn -> {
            updateName(resume, conn);
            Map<ContactType, String> contactsInResume = resume.getContacts();
            Map<ContactType, String> contactsInDB = new HashMap<>();
            Set<ContactType> contactTypesInResume = contactsInResume.keySet();
            Set<ContactType> contactTypesInDB = new HashSet<>();

            try (PreparedStatement ps = conn.prepareStatement("""
                    SELECT *
                      FROM resume
                               INNER JOIN contact c ON resume.uuid = c.resume_uuid
                     WHERE resume_uuid = ?;
                                                            """)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    contactsInDB.put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                    contactTypesInDB.add(ContactType.valueOf(rs.getString("type")));
                }
            }

            //determine contacts that are new in the resume in order to save them
            Set<ContactType> newContacts = new HashSet<>(contactTypesInResume);
            newContacts.removeAll(contactTypesInDB);

            //determine contacts that are not in the resume, but stored in DB in order to delete them
            Set<ContactType> deletedContacts = new HashSet<>(contactTypesInDB);
            deletedContacts.removeAll(contactTypesInResume);

            //determine contact types that are stored in DB and resume simultaneously
            Set<ContactType> sameContactTypes = new HashSet<>(contactTypesInResume);
            sameContactTypes.retainAll(contactTypesInDB);

            //determine which contacts in the resume have been changed in order to update them
            Set<ContactType> editedContacts = new HashSet<>();
            for (ContactType type : sameContactTypes) {
                if (!contactsInResume.get(type).equals(contactsInDB.get(type))) {
                    editedContacts.add(type);
                }
            }

            conductStatement("INSERT INTO contact (type, value, resume_uuid) " +
                             "VALUES (?, ?, ?)", conn, newContacts, (type, ps) -> {
                ps.setString(1, type.name());
                ps.setString(2, contactsInResume.get(type));
                ps.setString(3, uuid);
            });

            conductStatement("DELETE FROM contact WHERE type = ? AND resume_uuid" +
                             " = ?", conn, deletedContacts, (type, ps) -> {
                ps.setString(1, type.name());
                ps.setString(2, uuid);
            });

            conductStatement("UPDATE contact SET value = ? WHERE type = ? AND " +
                             "resume_uuid = ?", conn, editedContacts, (type, ps) -> {
                ps.setString(1, contactsInResume.get(type));
                ps.setString(2, type.name());
                ps.setString(3, uuid);
            });

            return null;
        });
    }

    private void addListSection(Resume resume, Map<String, SectionType> processedSections, ResultSet rs) throws SQLException {
        String type = rs.getString("list_section_type");
        String text = rs.getString("list_section_text");
        SectionType processedSection = processedSections.get(resume.getUuid());
        if (type == null || (processedSection != null && processedSection.equals(SectionType.valueOf(type)))) {
            return;
        }
        List<String> list = parseText(text);
        resume.addSection(SectionType.valueOf(type), new ListSection(list));
    }

    private List<String> parseText(String text) {
        return new ArrayList<>(Arrays.asList(text.split("\n")));
    }

    private void addTextSection(Resume resume, Map<String, SectionType> processedSections, ResultSet rs) throws SQLException {
        String type = rs.getString("text_section_type");
        String text = rs.getString("text_section_text");
        SectionType processedSection = processedSections.get(resume.getUuid());
        if (type == null || (processedSection != null && processedSection.equals(SectionType.valueOf(type)))) {
            return;
        }
        resume.addSection(SectionType.valueOf(type), new TextSection(text));
    }


    private void saveSections(Resume resume, Connection conn) throws SQLException {
        for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
            switch (section.getKey()) {
                case PERSONAL, POSITION -> {
                    saveTextSection(section, resume.getUuid(), conn);
                }
                case ACHIEVEMENTS, QUALIFICATIONS -> {
                    saveListSection(section, resume.getUuid(), conn);
                }
                case EXPERIENCE, EDUCATION -> {

                }
            }
        }
    }

    private void saveListSection(Map.Entry<SectionType, AbstractSection> section, String uuid, Connection conn) throws SQLException {
        String text = concatStrings((ListSection) section.getValue());
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO list_section(text, resume_uuid, type)VALUES( " +
                                                          "?, ?, ?::list_section_type) ")) {
            ps.setString(1, text);
            ps.setString(2, uuid);
            ps.setString(3, section.getKey().name());
            ps.execute();
        }
    }

    private String concatStrings(ListSection section) {
        StringBuilder sb = new StringBuilder();
        for (String s : section.getTexts()) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    //todo concat with saveListSection like saveContacts
    private void saveTextSection(Map.Entry<SectionType, AbstractSection> section, String uuid, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO text_section(text, resume_uuid, type)VALUES( " +
                                                          "?, ?, ?::text_section_type) ")) {
            TextSection tx = (TextSection) section.getValue();
            ps.setString(1, tx.getText());
            ps.setString(2, uuid);
            ps.setString(3, section.getKey().name());
            ps.execute();
        }
    }

    private void saveContacts(Resume resume, PreparedStatement ps) throws SQLException {
        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, e.getKey()
                    .name());
            ps.setString(3, e.getValue());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    private void updateName(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE resume" +
                                                          "       SET full_name = ?" +
                                                          "     WHERE uuid = ?")) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        }
    }

    private void addContact(Resume resume, Map<String, ContactType> processedContacts, ResultSet rs) throws SQLException {
        String type = rs.getString("contact_type");
        String value = rs.getString("contact_value");
        ContactType processedContact = processedContacts.get(resume.getUuid());
        if (type == null || (processedContact != null && processedContact.equals(ContactType.valueOf(type)))) {
            return;
        }
        processedContacts.put(resume.getUuid(), ContactType.valueOf(type));
        resume.addContact(ContactType.valueOf(type), value);
    }

    private void conductStatement(String sql, Connection conn, Set<ContactType> set,
                                  SqlConductor<ContactType> conductor) {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (ContactType type : set) {
                conductor.conduct(type, statement);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
}
