package com.basejava.storage.files;

import com.basejava.storage.AbstractStorageTest;
import com.basejava.storage.files.serialization.ObjectStreamSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    protected ObjectStreamPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new ObjectStreamSerializer()));
    }

    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }
}