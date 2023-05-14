package com.basejava.storage.files;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.AbstractStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(Path dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = dir;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> stream = Files.list(directory)) {
            stream.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        File[] files = directory.toFile().listFiles();
        if (files == null) {
            throw new StorageException("I/O error", directory.toString());
        }
        return files.length;
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>();
        try (Stream<Path> stream = Files.list(directory)) {
            stream.map(this::doGet).forEach(resumes::add);
        } catch (IOException e) {
            throw new StorageException("Path read error", null, e);
        }
        return resumes;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
            doWrite(resume, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString() + "/" + uuid);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            doWrite(resume, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Path delete error", searchKey.toString(), e);
        }
    }

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;
}
