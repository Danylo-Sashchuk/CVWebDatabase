package com.basejava.storage;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.exceptions.StorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractArrayStorageTest {

    protected final Storage storage;
    protected final int STORAGE_MAX_SIZE = 10;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String UUID_NOT_EXIST = "UUID_NOT_EXIST";
    protected static final Resume resume1 = new Resume(UUID_1);
    protected static final Resume resume2 = new Resume(UUID_2);
    protected static final Resume resume3 = new Resume(UUID_3);
    protected static final Resume resume4 = new Resume(UUID_4);
    protected static final Resume dummy = new Resume(UUID_NOT_EXIST);

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    final void getAll() {
        Assertions.assertArrayEquals(new Resume[]{resume1, resume2, resume3}, storage.getAll());
    }

    @Test
    final void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(new Resume[]{}, storage.getAll());
    }

    @Test
    final void size() {
        assertSize(3);
    }

    @Test
    final void get() {
        assertGet(resume1);
        assertGet(resume2);
        assertGet(resume3);
    }

    @Test
    final void update() {
        storage.update(resume1);
        Assertions.assertSame(resume1, storage.get(UUID_1));
    }

    @Test
    final void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    final void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(resume1));
    }

    @Test
    final void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    final void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(dummy));
    }

    @Test
    final void saveOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < STORAGE_MAX_SIZE; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException storageException) {
            Assertions.fail("StorageException thrown.");
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    final void save() {
        storage.save(resume4);
        assertSize(4);
        assertGet(resume4);
    }

    @Test
    final void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }
}