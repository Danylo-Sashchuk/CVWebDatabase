package com.basejava.storage.files;

import com.basejava.storage.files.serialization.ObjectStreamSerializer;

import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractFilesStorageTest {

    protected ObjectStreamPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new ObjectStreamSerializer()));
    }
}