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
    protected static final Resume R_1 = new Resume("uuid1");
    protected static final Resume R_2 = new Resume("uuid2");
    protected static final Resume R_3 = new Resume("uuid3");
    protected static final Resume R_4 = new Resume("uuid4");
    protected static final Resume DUMMY = new Resume("UUID_NOT_EXIST");

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(R_1);
        storage.save(R_2);
        storage.save(R_3);
    }

    @Test
    final void getAll() {
        Assertions.assertArrayEquals(new Resume[]{R_1, R_2, R_3}, storage.getAll());
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
        assertGet(R_1);
        assertGet(R_2);
        assertGet(R_3);
    }

    @Test
    final void update() {
        storage.update(R_1);
        Assertions.assertSame(R_1, storage.get(R_1.getUuid()));
    }

    @Test
    final void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(DUMMY.getUuid()));
    }

    @Test
    final void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(R_1));
    }

    @Test
    final void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(DUMMY.getUuid()));
    }

    @Test
    final void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(DUMMY));
    }

    @Test
    final void saveOverflow() {
        try {
            while (true) {
                storage.save(new Resume());
            }
        } catch (StorageException storageException) {
            Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume()));
        }
    }

    @Test
    final void save() {
        storage.save(R_4);
        assertSize(4);
        assertGet(R_4);
    }

    @Test
    final void delete() {
        storage.delete(R_2.getUuid());
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(R_4.getUuid()));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }
}