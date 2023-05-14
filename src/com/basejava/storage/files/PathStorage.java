package com.basejava.storage.files;

import java.nio.file.Path;

public class PathStorage extends AbstractPathStorage {
    protected PathStorage(Path dir, SerializationStrategy serializationStrategy) {
        super(dir, serializationStrategy);
    }
}
