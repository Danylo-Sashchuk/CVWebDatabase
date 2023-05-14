package com.basejava.storage.files;

import com.basejava.storage.AbstractStorageTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    protected ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(new File(STORAGE_DIR)));
    }

    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }
}