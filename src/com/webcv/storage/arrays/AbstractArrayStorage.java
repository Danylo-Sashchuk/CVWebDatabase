package com.webcv.storage.arrays;

import com.webcv.exceptions.StorageException;
import com.webcv.model.Resume;
import com.webcv.storage.AbstractStorage;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected final Resume[] storage = new Resume[STORAGE_MAX_SIZE];
    protected static final int STORAGE_MAX_SIZE = 10;
    protected int size = 0;

    @Override
    protected void doDelete(Integer searchKey) {
        size--;
        proceedDelete(searchKey);
    }

    @Override
    protected List<Resume> doGetAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return Arrays.asList(resumes);
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
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        if (size() == STORAGE_MAX_SIZE) {
            throw new StorageException("ERROR: The storage capacity is exceeded", resume.getUuid());
        }
        proceedSave(searchKey, resume);
        size++;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void proceedSave(Integer searchKey, Resume resume);

    protected abstract void proceedDelete(Integer searchKey);
}
