package com.basejava.storage;

import com.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_MAX_SIZE = 10000;
    protected final Resume[] storage = new Resume[STORAGE_MAX_SIZE];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR: A resume with uuid = \"" + uuid + "\" does not exist.");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);
}
