package com.basejava.storage;

import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.ContactType;
import com.basejava.model.Resume;
import com.basejava.sql.SqlTemplate;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        sqlTemplate.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
                ps.execute();
            }
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
                for (Map.Entry<ContactType, String> e : resume.getContacts()
                        .entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey()
                            .name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get resume: " + uuid);
        return sqlTemplate.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("""
                    SELECT *
                      FROM resume
                               LEFT JOIN contact ON resume.uuid = contact.resume_uuid
                     WHERE resume.uuid = ?;
                                                         """)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume resume = new Resume(uuid, rs.getString("full_name"));

                do {
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    String value = rs.getString("value");
                    resume.addContact(type, value);
                } while (rs.next());
                return resume;
            }
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete resume: " + uuid);
        sqlTemplate.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume " +
                                                              "          WHERE uuid = ?")) {
                ps.setString(1, uuid);
                int deleted = ps.executeUpdate();
                if (deleted == 0) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("get all sorted");
        return sqlTemplate.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("""
                    SELECT *
                      FROM resume
                               LEFT JOIN contact ON resume.uuid = contact.resume_uuid
                     ORDER BY full_name, uuid
                    """)) {
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
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    String value = rs.getString("value");
                    resume.addContact(type, value);
                }
                return new ArrayList<>(processedResumes.values());
            }
        });
    }

    @Override
    public int size() {
        return sqlTemplate.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*)" +
                                                              "      FROM resume")) {
                ResultSet rs = ps.executeQuery();
                return rs.next() ? rs.getInt(1) : 0;
            }
        });
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        LOG.info("updating resume: " + uuid);
        sqlTemplate.transactionExecute(conn -> {
            Map<ContactType, String> contactsInResume = resume.getContacts();
            Set<ContactType> contactTypesInResume = contactsInResume.keySet();
            Map<ContactType, String> contactsInDB = new HashMap<>();
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

            //determine contacts that are new in the resume
            Set<ContactType> newContacts = new HashSet<>(contactTypesInResume);
            newContacts.removeAll(contactTypesInDB);

            //determine contact that are not in resume, but stored in DB
            Set<ContactType> deletedContacts = new HashSet<>(contactTypesInDB);
            deletedContacts.removeAll(contactTypesInResume);

            //determine contacts that has been edited
            Set<ContactType> editedContacts = new HashSet<>(contactTypesInResume);
            editedContacts.retainAll(contactTypesInDB);

            try (PreparedStatement insert = conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) " +
                                                                  "VALUES (?, ?, ?)")) {
                for (ContactType type : newContacts) {
                    insert.setString(1, type.toString());
                    insert.setString(2, contactsInResume.get(type));
                    insert.setString(3, uuid);
                    insert.addBatch();
                }
                insert.executeBatch();
            }

            try (PreparedStatement delete = conn.prepareStatement("DELETE FROM contact WHERE type = ? AND resume_uuid" +
                                                                  " = ?")) {
                for (ContactType type : deletedContacts) {
                    delete.setString(1, type.toString());
                    delete.setString(2, uuid);
                    delete.addBatch();
                }
                delete.executeBatch();
            }

            try (PreparedStatement update = conn.prepareStatement("UPDATE contact SET value = ? WHERE type = ? AND resume_uuid = ?")) {
                for (ContactType type : editedContacts) {
                    update.setString(1, contactsInResume.get(type));
                    update.setString(2, type.toString());
                    update.addBatch();
                }
                update.executeBatch();
            }

            return null;
        });
    }
}
