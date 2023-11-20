package com.webcv.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlFunctional<T> {
    T execute(PreparedStatement statement) throws SQLException;
}
