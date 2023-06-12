package com.basejava.util;

import com.basejava.exceptions.ExistStorageException;
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

    public <T> T execute(String sql, SqlFunctional<T> functional) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement =
                connection.prepareStatement(sql)) {
            return functional.execute(statement);
        } catch (SQLException e) {
            throw e.getSQLState()
                    .equals("23505") ? new ExistStorageException(e) : new StorageException(e);
        }
    }
}
