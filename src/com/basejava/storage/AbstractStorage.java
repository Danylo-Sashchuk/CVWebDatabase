package com.basejava.storage;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final Resume get(String uuid) {
        Object searchKey = getIndex(uuid);
        return getElement(getNonExistingSearchKey(searchKey, uuid));
    }

    @Override
    public final void update(Resume resume) {
        Object searchKey = getIndex(resume.getUuid());
        updateElement(getNonExistingSearchKey(searchKey, resume.getUuid()), resume);
    }

    @Override
    public final void delete(String uuid) {
        Object searchKey = getIndex(uuid);
        deleteElement(getNonExistingSearchKey(searchKey, uuid));
    }

    @Override
    public final void save(Resume resume) {
        Object searchKey = getIndex(resume.getUuid());
        if (size() == AbstractArrayStorage.STORAGE_MAX_SIZE) {
            throw new StorageException("ERROR: The storage capacity is exceeded", resume.getUuid());
        }
        getExistingSearchKey(searchKey, resume.getUuid());
        saveElement(resume);
    }

    private Object getNonExistingSearchKey(Object searchKey, String uuid) {
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
    private void getExistingSearchKey(Object searchKey, String uuid) {
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract void saveElement(Resume resume);

    protected abstract int getIndex(String uuid);

    protected abstract void updateElement(Object searchKey, Resume resume);

    protected abstract Resume getElement(Object searchKey);

    protected abstract void deleteElement(Object searchKey);
}
