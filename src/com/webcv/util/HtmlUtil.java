package com.webcv.util;

import com.webcv.model.Company;
import com.webcv.model.ContactType;

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

    public static String formatContact(ContactType type, String value) {
        if (type == ContactType.PHONE_NUMBER)
            return value;
        else if (type == ContactType.EMAIL)
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        else if (type == ContactType.SKYPE)
            return "<a href='skype:" + value + "'>" + value + "</a>";
        else if (type == ContactType.LINKEDIN)
            return "<a href=" + value + ">" + value + "</a>";
        else if (type == ContactType.GITHUB)
            return "<a href=" + value + ">" + value + "</a>";
        return null;
    }

    public static String removeNewLinesAndCarriage(String line) {
        line = line.replaceAll("\n", "").replaceAll("\r", "");
        return line;
    }
}
