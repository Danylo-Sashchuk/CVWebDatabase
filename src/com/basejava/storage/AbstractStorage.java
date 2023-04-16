package com.basejava.storage;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected static final int STORAGE_MAX_SIZE = 10;
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(index);
    }

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (!isExist(index)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateElement(index, resume);
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        size--;
        deleteElement(index);
    }

    @Override
    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size == STORAGE_MAX_SIZE) {
            throw new StorageException("ERROR: The storage capacity is exceeded", resume.getUuid());
        } else if (isExist(index)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            proceedSave(size++, resume);
        }
    }

    protected boolean isExist(int index) {
        return index >= 0;
    }

    protected abstract void proceedSave(int index, Resume r);

    protected abstract int getIndex(String uuid);

    protected abstract void updateElement(int index, Resume resume);

    protected abstract Resume getElement(int index);

    protected abstract void deleteElement(int index);
}
