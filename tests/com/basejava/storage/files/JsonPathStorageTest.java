package com.basejava.storage.files;

import com.basejava.storage.AbstractStorageTest;
import com.basejava.storage.files.serialization.JsonStreamSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class JsonPathStorageTest extends AbstractStorageTest {
    protected JsonPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new JsonStreamSerializer()));
    }

    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }
}
