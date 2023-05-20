package com.basejava.storage.files;

import com.basejava.storage.AbstractStorageTest;
import com.basejava.storage.files.serialization.DataStreamSerializer;

import java.nio.file.Paths;

public class DataStreamStorageTest extends AbstractStorageTest {
    protected DataStreamStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new DataStreamSerializer()));
    }
}
