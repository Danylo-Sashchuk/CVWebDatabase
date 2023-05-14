package com.basejava.storage.files;

import java.io.File;

public class FileStorage extends AbstractFileStorage {
    protected FileStorage(File directory, SerializationStrategy serializationStrategy) {
        super(directory, serializationStrategy);
    }
}
