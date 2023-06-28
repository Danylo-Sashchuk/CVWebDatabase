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
                SELECT *
                  FROM resume
                           LEFT JOIN contact ON resume.uuid = contact.resume_uuid
                           LEFT JOIN text_section ON resume.uuid = text_section.resume_uuid
                 WHERE resume.uuid = ?;
                                                     """, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(resume, rs);
            } while (rs.next());


            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("get all sorted");
        return sqlTemplate.execute("""
                                    SELECT *
                                      FROM resume
                                               LEFT JOIN contact ON resume.uuid = contact.resume_uuid
                                     ORDER BY full_name, uuid
                """, ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> processedResumes = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                Resume resume = processedResumes.get(uuid);
                if (!processedResumes.containsKey(uuid)) {
                    resume = new Resume(uuid, fullName);
                    processedResumes.put(uuid, resume);
                }
                addContact(resume, rs);
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

    private void saveSections(Resume resume, Connection conn) throws SQLException {
        for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
            switch (section.getKey()) {
                case PERSONAL, POSITION -> {
                    saveTextSection(section, resume.getUuid(), conn);
                }
                case ACHIEVEMENTS, QUALIFICATIONS -> {

                }
                case EXPERIENCE, EDUCATION -> {
                    //TODO experience and education
                }
            }
        }
    }

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

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (type == null) {
            return;
        }
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
