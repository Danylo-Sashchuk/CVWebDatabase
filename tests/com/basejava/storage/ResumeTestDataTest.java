package com.basejava.storage;

public class ResumeTestDataTest {
        public static void main(String[] args) {
            ResumeTestData resumeTestData = new ResumeTestData();
            final String UUID_1 = "uuid1";
            final String UUID_2 = "uuid2";
            final String UUID_3 = "uuid3";
            final String UUID_4 = "uuid4";
            final String FULL_NAME_1 = "John Smith";
            final String FULL_NAME_2 = "Emily Davis";
            final String FULL_NAME_3 = "Robert Johnson";
            final String FULL_NAME_4 = "Alice Wilson";
            System.out.println(resumeTestData.createResume(UUID_1, FULL_NAME_1)); //1
            System.out.println(resumeTestData.createResume(UUID_2, FULL_NAME_2));
            System.out.println(resumeTestData.createResume(UUID_3, FULL_NAME_3));//3
            System.out.println(resumeTestData.createResume(UUID_4, FULL_NAME_4));
            System.out.println(resumeTestData.createResume(UUID_4, FULL_NAME_4));//5
            System.out.println(resumeTestData.createResume(UUID_4, FULL_NAME_4));
            System.out.println(resumeTestData.createResume(UUID_4, FULL_NAME_4));//7
            System.out.println(resumeTestData.createResume(UUID_4, FULL_NAME_4));
            System.out.println(resumeTestData.createResume(UUID_4, FULL_NAME_4));//9
            System.out.println(resumeTestData.createResume(UUID_4, FULL_NAME_4));
    }
}
