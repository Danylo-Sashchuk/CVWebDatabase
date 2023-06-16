package com.basejava.storage;

import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.ContactType;
import com.basejava.model.Resume;
import com.basejava.sql.ConnectionFactory;
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
    public final ConnectionFactory connectionFactory;
    private final SqlTemplate sqlTemplate;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlTemplate = new SqlTemplate(connectionFactory);
    }

    @Override
    public void clear() {
        sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM resume
                    """)) {
                statement.execute();
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save resume " + resume);
        sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO resume (uuid, full_name)
                         VALUES (?, ?)
                    """)) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO contact (resume_uuid, type, value)
                         VALUES (?, ?, ?)
                    """)) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts()
                        .entrySet()) {
                    statement.setString(1, resume.getUuid());
                    statement.setString(2, entry.getKey()
                            .name());
                    statement.setString(3, entry.getValue());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get resume: " + uuid);
        return sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT r.full_name AS full_name,
                           c.id        AS contact_id,
                           c.type      AS contact_type,
                           c.value     AS contact_value
                      FROM public.resume r
                               JOIN contact c ON r.uuid = c.resume_uuid
                     WHERE r.uuid = ?;
                                     """)) {
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume resume = new Resume(uuid, resultSet.getString("full_name"));

                do {
                    ContactType type = ContactType.valueOf(resultSet.getString("contact_type"));
                    String value = resultSet.getString("contact_value");
                    resume.addContact(type, value);
                } while (resultSet.next());
                return resume;
            }
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete resume: " + uuid);
        sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
                statement.setString(1, uuid);
                int deleted = statement.executeUpdate();
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
        return sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT uuid, full_name, type, value
                      FROM resume r
                               LEFT JOIN contact c ON r.uuid = c.resume_uuid
                     ORDER BY full_name, uuid
                    """)) {
                ResultSet resultSet = statement.executeQuery();
                Map<String, Resume> processedResumes = new LinkedHashMap<>();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String fullName = resultSet.getString("full_name");
                    Resume resume = processedResumes.get(uuid);
                    if (!processedResumes.containsKey(uuid)) {
                        resume = new Resume(uuid, fullName);
                        processedResumes.put(uuid, resume);
                    }
                    ContactType type = ContactType.valueOf(resultSet.getString("type"));
                    String value = resultSet.getString("value");
                    resume.addContact(type, value);
                }
                return new ArrayList<>(processedResumes.values());
            }
        });
    }

    @Override
    public int size() {
        return sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT COUNT(*)
                      FROM resume
                    """)) {
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("update resume");
        sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    UPDATE resume
                       SET full_name = ?
                     WHERE uuid = ?
                    """)) {
                statement.setString(1, resume.getFullName());
                statement.setString(2, resume.getUuid());
                int updated = statement.executeUpdate();
                if (updated == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                return null;
            }
        });
    }
}
