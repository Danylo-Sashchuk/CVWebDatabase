package com.webcv.storage;

import com.webcv.exceptions.ExistStorageException;
import com.webcv.exceptions.NotExistStorageException;
import com.webcv.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumes = doGetAll();
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("get " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public final void update(Resume resume) {
        LOG.info("update " + resume);
        SK searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public final void save(Resume resume) {
        LOG.info("save " + resume);
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    private SK getExistingSearchKey(String uuid) {
        LOG.info("getExistingSearchKey " + uuid);
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            NotExistStorageException exception = new NotExistStorageException(uuid);
            LOG.log(Level.WARNING, exception.getMessage(), exception);
            throw exception;
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        LOG.info("getNotExistingSearchKey " + uuid);
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            ExistStorageException exception = new ExistStorageException(uuid);
            LOG.log(Level.WARNING, exception.getMessage(), exception);
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> doGetAll();

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);
}
