package com.basejava.storage.arrays;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.AbstractStorageTest;
import com.basejava.storage.Storage;
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
                storage.save(new Resume(NAMES[i]));
            }
        } catch (StorageException storageException) {
            Assertions.fail("StorageException thrown.");
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume(NAMES[NAMES.length - 1])));
    }
}
