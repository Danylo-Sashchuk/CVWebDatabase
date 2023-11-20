package com.webcv.util;

import com.webcv.model.Company;

/**
 * @author Danylo Sashchuk <p>
 * 11/19/23
 */

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Company.Period period) {
        return DateUtil.format(period.getStartDate()) + " - " + DateUtil.format(period.getEndDate());
    }
}
