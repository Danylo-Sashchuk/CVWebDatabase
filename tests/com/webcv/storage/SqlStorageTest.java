package com.webcv.storage;

import com.webcv.model.ContactType;
import com.webcv.model.Resume;
import com.webcv.util.Config;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SqlStorageTest extends AbstractStorageTest {
    private static final String EMAIL = "email_for_first_resume_test@test.com";
    private static final String UUID = "TestUUID";
    private static final String FULL_NAME = "John Doe";
    private static final String PHONE_NUMBER = "+1 234 567 8901";
    private static final String GITHUB = "git.com/first_resume";
    private static final Resume RESUME;

    static {
        RESUME = new Resume(UUID, FULL_NAME);
        RESUME.setContact(ContactType.EMAIL, EMAIL);
        RESUME.setContact(ContactType.PHONE_NUMBER, PHONE_NUMBER);
        RESUME.setContact(ContactType.GITHUB, GITHUB);
    }


    protected SqlStorageTest() {
        super(Config.get()
                .getStorage());
    }

    @Override
    @Test
    void update() {
        resumeTestData.updatePosition(resume1);
        resumeTestData.updatePersonal(resume1);
        resumeTestData.updateAchievements(resume1);
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test
    void update_whenContactsUpdated_shouldTrue() {
        storage.save(RESUME);

        RESUME.setContact(ContactType.PHONE_NUMBER, "222222222");
        storage.update(RESUME);

        Assertions.assertEquals(RESUME, storage.get(UUID));
    }

    @Test
    void update_whenSavedResume_hasMoreContactsThenActual_shouldTrue() {
        storage.save(RESUME);

        RESUME.removeContact(ContactType.GITHUB);
        storage.update(RESUME);

        assertGet(RESUME);
    }

    @Test
    void update_whenSavedResume_hasLessContactsThenActual_shouldTrue() {
        storage.save(RESUME);

        RESUME.setContact(ContactType.LINKEDIN, "linkedin.com/john_doe_unique");
        storage.update(RESUME);

        assertGet(RESUME);
    }

    @Test
    void update_ifResumeDoesNotHaveContacts() {
        storage.save(RESUME);

        cleanContacts();
        storage.update(RESUME);

        RESUME.setContact(ContactType.SKYPE, "noskype");
        storage.update(RESUME);

        assertGet(RESUME);
    }

    @Test
    void getAllSorted_whenResumeWithoutContacts() {
        storage.save(RESUME);

        cleanContacts();
        storage.update(RESUME);

        storage.getAllSorted();
    }

    @Test
    void get_whenResumeDoesNotHaveContacts() {
        storage.save(RESUME);

        cleanContacts();
        storage.update(RESUME);

        assertGet(RESUME);
    }

    private void cleanContacts() {
        SqlStorageTest.RESUME.removeContact(ContactType.EMAIL);
        SqlStorageTest.RESUME.removeContact(ContactType.PHONE_NUMBER);
        SqlStorageTest.RESUME.removeContact(ContactType.SKYPE);
        SqlStorageTest.RESUME.removeContact(ContactType.GITHUB);
        SqlStorageTest.RESUME.removeContact(ContactType.LINKEDIN);
    }

    @AfterAll
    static void afterAll() {
        Config.get()
                .getStorage()
                .delete(UUID);
    }
}