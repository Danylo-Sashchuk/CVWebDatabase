package com.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlAbsorber<T> {
    void absorb(T item, PreparedStatement preparedStatement) throws SQLException;
}
