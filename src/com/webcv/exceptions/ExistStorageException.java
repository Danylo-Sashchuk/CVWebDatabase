package com.webcv.exceptions;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("A resume with uuid = \"" + uuid + "\" already exists.", uuid);
    }

    public ExistStorageException(Exception e) {
        super(e);
    }
}
