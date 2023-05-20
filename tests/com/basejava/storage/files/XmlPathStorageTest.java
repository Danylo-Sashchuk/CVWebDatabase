package com.basejava.storage.files;

import com.basejava.storage.AbstractStorageTest;
import com.basejava.storage.files.serialization.XmlStreamSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new XmlStreamSerializer()));
    }

    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }
}