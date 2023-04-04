package com.basejava.storage;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_MAX_SIZE = 10000;
    protected final Resume[] storage = new Resume[STORAGE_MAX_SIZE];
    protected int size = 0;

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
    public int size() {
        return size;
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (!isExist(index)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage[index] = resume;
    }

    @Override
    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_MAX_SIZE) {
            throw new StorageException("ERROR: The storage capacity is exceeded", r.getUuid());
        } else if (isExist(index)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            proceedSave(size++, r);
        }
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        size--;
        proceedDelete(index);
    }

    protected boolean isExist(int index) {
        return index >= 0;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void proceedSave(int index, Resume r);

    protected abstract void proceedDelete(int index);
}
