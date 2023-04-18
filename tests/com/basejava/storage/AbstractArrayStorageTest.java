package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    final void saveOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_MAX_SIZE; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException storageException) {
            Assertions.fail("StorageException thrown.");
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }
}
