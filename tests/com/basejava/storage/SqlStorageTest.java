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
    public static final String PHONE_NUMBER = "+1 234 567 8901";
    public static final String GITHUB = "git.com/first_resume";


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
    void update_whenContactsUpdated_shouldTrue() {
        storage.clear();
        Resume testResume = new Resume(UUID, FULL_NAME);
        testResume.addContact(ContactType.EMAIL, EMAIL);
        testResume.addContact(ContactType.PHONE_NUMBER, PHONE_NUMBER);
        testResume.addContact(ContactType.GITHUB, GITHUB);
        storage.save(testResume);

        testResume.addContact(ContactType.PHONE_NUMBER, "222222222");
        storage.update(testResume);

        Assertions.assertEquals(testResume, storage.get(testResume.getUuid()));
    }

    @Test
    void update_whenSavedResume_HasMoreContactsThenActual_shouldTrue() {
        storage.clear();
        Resume testResume = new Resume(UUID, FULL_NAME);
        testResume.addContact(ContactType.EMAIL, EMAIL);
        testResume.addContact(ContactType.GITHUB, GITHUB);
    }
}