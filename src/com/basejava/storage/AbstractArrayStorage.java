package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final Resume[] storage = new Resume[STORAGE_MAX_SIZE];
    protected static final int STORAGE_MAX_SIZE = 10;
    protected int size = 0;

    @Override
    protected void doDelete(Object searchKey) {
        size--;
        proceedDelete(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        if (size() == STORAGE_MAX_SIZE) {
            throw new StorageException("ERROR: The storage capacity is exceeded", resume.getUuid());
        }
        storage[size++] = resume;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract void proceedDelete(Object searchKey);
}
