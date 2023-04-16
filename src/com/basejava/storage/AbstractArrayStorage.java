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
    protected Resume getElement(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected void saveElement(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
