package com.webcv.storage.arrays;

import com.webcv.exceptions.StorageException;
import com.webcv.storage.AbstractStorageTest;
import com.webcv.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected final String[] NAMES = {"Emily Adams", "Michael Brown", "Sophia Clark", "William Davis", "Natalie Evans",
            "Oliver Green", "Isabella Hall", "Jacob Jackson", "Ava Johnson", "Ethan King", "Lily Martinez",};

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    final void saveOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_MAX_SIZE; i++) {
                storage.save(resumeTestData.createResume(NAMES[i]));
            }
        } catch (StorageException storageException) {
            Assertions.fail("StorageException thrown.");
        }
        Assertions.assertThrows(StorageException.class,
                () -> storage.save(resumeTestData.createResume(NAMES[NAMES.length - 1])));
    }
}
