package com.basejava.storage;

import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.ContactType;
import com.basejava.model.Resume;
import com.basejava.sql.SqlTemplate;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
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
        LOG.info("update resume");
        sqlTemplate.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume" +
                                                              "       SET full_name = ?" +
                                                              "     WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                int updated = ps.executeUpdate();
                if (updated == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                return null;
            }
        });
    }
}
