package com.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlConductor<T> {
    void conduct(T t, PreparedStatement ps) throws SQLException;
}
