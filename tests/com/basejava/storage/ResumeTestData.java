package com.basejava.storage;

import com.basejava.model.ContactType;
import com.basejava.model.Resume;

public class ResumeTestData {
    private static final ResumeTestData instance = new ResumeTestData();
    private static final String[] PHONE_NUMBERS = {"(555) 123-4567", "(555) 987-6543", "(555) 555-1212", "(555) " +
            "456-7890", "(555) 777-8888", "(555) 999-0000", "(555) 222-3333", "(555) 444-5555", "(555) 888-9999",
            "(555) " + "111" + "-2222", "(555) 222-1111", "(555) 333-4444", "(555) 444-3333", "(555) 666-7777",
            "(555) 777-6666", "(555)" + " 123-7890", "(555) 789-1234", "(555) 345-6789", "(555) 678-9012",
            "(555) " + "901-2345"};

    private static final String[] EMAILS = new String[]{"gmail", "yahoo", "hotmail", "outlook", "icloud", "protonmail"
            , "aol"};

    private ResumeTestData() {
    }

    public static ResumeTestData getInstance() {
        return instance;
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE_NUMBER, getRandomPhoneNumber());
        resume.addContact(ContactType.EMAIL, generateEmail(fullName));
        resume.addContact(ContactType.GITHUB, generateLink(fullName, "https://github.com/"));
        resume.addContact(ContactType.LINKEDIN, generateLink(fullName, "https://www.linkedin.com/in/"));
        resume.addContact(ContactType.SKYPE, generateSkype(fullName));
        return resume;
    }

    public static String getRandomPhoneNumber() {
        int random = (int) (Math.random() * PHONE_NUMBERS.length);
        return PHONE_NUMBERS[random];
    }

    public static String generateEmail(String fullName) {
        fullName = fullName.replaceAll(" ", "").toLowerCase();
        int random = (int) (Math.random() * EMAILS.length);
        return fullName + "@" + EMAILS[random] + ".com";
    }

    public static String generateLink(String fullName, String websiteName) {
        fullName = fullName.replaceAll(" ", "").toLowerCase();
        return websiteName + fullName;
    }

    public static String generateSkype(String fullName) {
        fullName = fullName.replaceAll(" ", ".").toLowerCase() + (int) (Math.random() * 100);
        return fullName;
    }

    public static void main(String[] args) {
        final String UUID_1 = "uuid1";
        final String UUID_2 = "uuid2";
        final String UUID_3 = "uuid3";
        final String UUID_4 = "uuid4";
        final String FULL_NAME_1 = "John Smith";
        final String FULL_NAME_2 = "Emily Davis";
        final String FULL_NAME_3 = "Robert Johnson";
        final String FULL_NAME_4 = "Alice Wilson";
        System.out.println(createResume(UUID_1, FULL_NAME_1));

        System.out.println(createResume(UUID_2, FULL_NAME_2));

        System.out.println(createResume(UUID_3, FULL_NAME_3));

        System.out.println(createResume(UUID_4, FULL_NAME_4));
    }
}
