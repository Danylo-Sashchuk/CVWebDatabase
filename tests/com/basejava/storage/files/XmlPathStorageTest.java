package com.basejava.storage.files;

import com.basejava.storage.files.serialization.XmlStreamSerializer;

import java.nio.file.Paths;

public class XmlPathStorageTest extends AbstractFilesStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new XmlStreamSerializer()));
    }
}