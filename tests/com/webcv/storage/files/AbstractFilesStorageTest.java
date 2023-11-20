package com.webcv.storage.files;

import com.webcv.storage.AbstractStorageTest;
import com.webcv.storage.Storage;
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
