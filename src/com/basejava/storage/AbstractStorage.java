package com.basejava.storage;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public final void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public final void save(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);
}
