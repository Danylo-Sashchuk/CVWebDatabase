package com.basejava.exceptions;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("ERROR: A resume with uuid = \"" + uuid + "\" does not exist.", uuid);
    }
}
