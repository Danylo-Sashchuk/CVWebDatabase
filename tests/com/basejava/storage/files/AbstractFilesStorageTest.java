package com.basejava.storage.files;

import com.basejava.storage.AbstractStorageTest;
import com.basejava.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractFilesStorageTest extends AbstractStorageTest {

    protected AbstractFilesStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }
}
