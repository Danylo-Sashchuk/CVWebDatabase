package com.basejava.util;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
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
        } catch (NotExistStorageException | ExistStorageException e) {
            throw e;
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint \"resume_pk\"")) {
                throw new ExistStorageException("i");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
