package com.basejava.storage.files;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.AbstractStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        List<Resume> resumes = new ArrayList<>();
        for (File file : files) {
            resumes.add(doRead(file));
        }
        return resumes;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File searchKey, Resume resume) {
        doSave(searchKey, resume);
    }

    @Override
    protected Resume doGet(File searchKey) {
        return doRead(searchKey);
    }

    @Override
    protected void doDelete(File searchKey) {
        searchKey.delete();
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        return files == null ? 0 : files.length;
    }

    protected abstract Resume doRead(File file);

    protected abstract void doWrite(File file, Resume resume);
}
