package com.basejava.storage.files;

import com.basejava.storage.AbstractStorageTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    protected ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(Paths.get(STORAGE_DIR)));
    }

    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }
}