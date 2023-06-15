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
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private final SqlTemplate sqlTemplate;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlTemplate = new SqlTemplate(connectionFactory);
    }

    @Override
    public void clear() {
        sqlTemplate.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) " +
                    "VALUES (?, ?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO contact (type, value, " +
                    "resume_uuid) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> contact : resume.getContacts()
                        .entrySet()) {
                    statement.setString(1, contact.getKey()
                            .toString());
                    statement.setString(2, contact.getValue());
                    statement.setString(3, resume.getUuid());
                    statement.addBatch();
                }
                statement.executeBatch();
            }

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlTemplate.execute("SELECT * FROM resume r " + "LEFT JOIN contact c " + "   ON r.uuid = c" +
                ".resume_uuid " + "WHERE r.uuid = ?", statement -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                String value = resultSet.getString("value");
                ContactType type = ContactType.valueOf(resultSet.getString("type"));
                resume.addContact(type, value);
            } while (resultSet.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlTemplate.execute("DELETE FROM resume WHERE uuid = ?", statement -> {
            statement.setString(1, uuid);
            int deleted = statement.executeUpdate();
            if (deleted == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        sqlTemplate.execute("SELECT * FROM resume r ORDER BY full_name, uuid", statement -> {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String fullName = resultSet.getString("full_name");
                resumes.add(new Resume(uuid, fullName));
            }
            return resumes;
        });
        return resumes;
    }

    @Override
    public int size() {
        return sqlTemplate.execute("SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlTemplate.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            int updated = statement.executeUpdate();
            if (updated == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }
}
