package com.basejava.storage.files;

import com.basejava.storage.files.serialization.JsonStreamSerializer;

import java.nio.file.Paths;

public class JsonPathStorageTest extends AbstractFilesStorageTest {
    protected JsonPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new JsonStreamSerializer()));
    }
}
