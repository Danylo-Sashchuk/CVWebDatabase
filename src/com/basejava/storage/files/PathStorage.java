package com.basejava.storage.files;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.AbstractStorage;
import com.basejava.storage.files.serialization.SerializationStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializationStrategy serializationStrategy;

    protected PathStorage(Path dir, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(dir, "directory must not be null");
        Objects.requireNonNull(serializationStrategy, "serializationStrategy must not be null");
        directory = dir;
        this.serializationStrategy = serializationStrategy;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    public void setSerializationStrategy(SerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    public void clear() {
        getFilesStream().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesStream().count();
    }

    @Override
    protected List<Resume> doGetAll() {
        return getFilesStream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.toString(), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serializationStrategy.doWrite(resume, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializationStrategy.doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Path> getFilesStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
    }
}
