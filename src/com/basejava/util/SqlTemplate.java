package com.basejava.util;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlTemplate {
    private final ConnectionFactory connectionFactory;

    public SqlTemplate(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(SqlFunctional<T> functional, String sql) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement(sql)) {
            return functional.execute(statement);
        } catch (SQLException e) {
            if (e.getSQLState()
                    .equals("23505")) {
                throw new ExistStorageException(e);
            }
        } catch (Exception e) {
            throw new StorageException(e);
        }

        return null;
    }
}
