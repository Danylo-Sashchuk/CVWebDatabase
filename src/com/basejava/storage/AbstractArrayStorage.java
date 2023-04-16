package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final Resume[] storage = new Resume[STORAGE_MAX_SIZE];

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume getElement(int index) {
        return storage[index];
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage[index] = resume;
    }
}
