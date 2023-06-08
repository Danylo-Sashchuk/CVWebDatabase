package com.basejava.util;

import java.sql.PreparedStatement;

public interface SqlFunctional<T> {
    T execute(PreparedStatement statement) throws Exception;
}
