package com.basejava.sql;

import com.basejava.exceptions.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

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
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionExecute(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = executor.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> void statementExecute(String sql, Connection conn, Collection<T> collection, SqlAbsorber<T> absorber) {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (T item : collection) {
                absorber.absorb(item, statement);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
}
