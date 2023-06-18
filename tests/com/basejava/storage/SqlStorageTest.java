package com.basejava.storage;

import com.basejava.model.ContactType;
import com.basejava.model.Resume;
import com.basejava.util.Config;
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
        RESUME.addContact(ContactType.EMAIL, EMAIL);
        RESUME.addContact(ContactType.PHONE_NUMBER, PHONE_NUMBER);
        RESUME.addContact(ContactType.GITHUB, GITHUB);
    }


    protected SqlStorageTest() {
        super(Config.get()
                .getStorage());
    }

    @Override
    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test
    void update_whenContactsUpdated_shouldTrue() { //TODO DELETE CLEAR
        storage.clear();
        storage.save(RESUME);

        RESUME.addContact(ContactType.PHONE_NUMBER, "222222222");
        storage.update(RESUME);

        Assertions.assertEquals(RESUME, storage.get(UUID));
    }

    @Test
    void update_whenSavedResume_HasMoreContactsThenActual_shouldTrue() {
        storage.clear();
        storage.save(RESUME);

        RESUME.removeContact(ContactType.GITHUB);
        storage.update(RESUME);

        Assertions.assertEquals(RESUME, storage.get(UUID));
    }

    @Test
    void update_whenSavedResume_HasLessContactsThenActual_shouldTrue() {
        storage.clear();
        storage.save(RESUME);

        RESUME.addContact(ContactType.LINKEDIN, "linkedin.com/john_doe_unique");
        storage.update(RESUME);

        Assertions.assertEquals(RESUME, storage.get(UUID));
    }
}