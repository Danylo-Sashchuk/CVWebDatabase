package com.webcv.storage.files;

import com.webcv.storage.files.serialization.ObjectStreamSerializer;

import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractFilesStorageTest {

    protected ObjectStreamPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new ObjectStreamSerializer()));
    }
}