package com.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class DateUtil {
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
