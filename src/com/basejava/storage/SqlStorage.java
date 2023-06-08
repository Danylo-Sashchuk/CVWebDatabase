package com.basejava.storage;

import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.Resume;
import com.basejava.sql.ConnectionFactory;
import com.basejava.util.SqlTemplate;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlTemplate sqlTemplate = new SqlTemplate(connectionFactory);
        sqlTemplate.execute(PreparedStatement::execute, "DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        SqlTemplate sqlTemplate = new SqlTemplate(connectionFactory);
        sqlTemplate.execute(statement -> {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();

            return null;
        }, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)");
    }

    //    @Override
    //    public void save(Resume resume) {
    //        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
    //                connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
    //            statement.setString(1, resume.getUuid());
    //            statement.setString(2, resume.getFullName());
    //            statement.execute();
    //        } catch (SQLException e) {
    //            if (e.getMessage().contains("duplicate key value violates unique constraint \"resume_pk\"")) {
    //                throw new ExistStorageException(resume.getUuid());
    //            }
    //            throw new StorageException(e);
    //        }
    //    }

    @Override
    public Resume get(String uuid) {
        SqlTemplate sqlTemplate = new SqlTemplate(connectionFactory);
        return sqlTemplate.execute(statement -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        }, "SELECT * FROM resume r WHERE r.uuid = ?");
    }

    //    @Override
    //    public Resume get(String uuid) {
    //        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
    //                connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
    //            statement.setString(1, uuid);
    //            ResultSet resultSet = statement.executeQuery();
    //            if (!resultSet.next()) {
    //                throw new NotExistStorageException(uuid);
    //            }
    //            return new Resume(uuid, resultSet.getString("full_name"));
    //        } catch (SQLException e) {
    //            throw new StorageException(e);
    //        }
    //    }

    @Override
    public void delete(String uuid) {
        SqlTemplate sqlTemplate = new SqlTemplate(connectionFactory);
        sqlTemplate.execute(statement -> {
            statement.setString(1, uuid);
            int deleted = statement.executeUpdate();
            if (deleted == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, "DELETE FROM resume WHERE uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        SqlTemplate sqlTemplate = new SqlTemplate(connectionFactory);
        sqlTemplate.execute(statement -> {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid").trim();
                String fullName = resultSet.getString("full_name");
                resumes.add(new Resume(uuid, fullName));
            }
            return resumes;
        }, "SELECT * FROM resume r ORDER BY full_name, uuid");
        return resumes;
    }

    @Override
    public int size() {
        SqlTemplate sqlTemplate = new SqlTemplate(connectionFactory);
        return sqlTemplate.execute(statement -> {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }, "SELECT COUNT(*) FROM resume");
    }

    @Override
    public void update(Resume resume) {
        SqlTemplate sqlTemplate = new SqlTemplate(connectionFactory);
        sqlTemplate.execute(statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            int updated = statement.executeUpdate();
            if (updated == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        }, "UPDATE resume SET full_name = ? WHERE uuid = ?");
    }
}
