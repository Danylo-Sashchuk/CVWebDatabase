package com.webcv.storage.files;

import com.webcv.storage.files.serialization.ObjectStreamSerializer;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractFilesStorageTest {
    protected ObjectStreamStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectStreamSerializer()));
    }
}