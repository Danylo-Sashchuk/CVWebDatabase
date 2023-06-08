package com.basejava.storage;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import com.basejava.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement("DELETE FROM resume")) {
            statement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint \"resume_pk\"")) {
                throw new ExistStorageException(resume.getUuid());
            }
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            statement.setString(1, uuid);
            int deleted = statement.executeUpdate();
            if (deleted == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid").trim();
                String fullName = resultSet.getString("full_name");
                resumes.add(new Resume(uuid, fullName));
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return resumes;
    }

    @Override
    public int size() {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement("SELECT COUNT(*) FROM resume")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return 0;
    }

    @Override
    public void update(Resume resume) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            int deleted = statement.executeUpdate();
            if (deleted == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
