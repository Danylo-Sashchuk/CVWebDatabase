package com.basejava.storage;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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
        Assertions.assertArrayEquals(new Resume[]{}, storage.getAll());
    }

    @Test
    final void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    final void update() {
        storage.update(R_1);
        Assertions.assertArrayEquals(new Resume[]{R_1, R_2, R_3}, storage.getAll());
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
    final void storageOverflowException() {
        storage.save(R_4);
        storage.save(new Resume("uuid5"));
        storage.save(new Resume("uuid6"));
        storage.save(new Resume("uuid7"));
        storage.save(new Resume("uuid8"));
        storage.save(new Resume("uuid9"));
        storage.save(new Resume("uuid10"));
        try {
            storage.save(new Resume("Extra resume"));
            fail("Storage Exception has not been thrown.");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    final void get() {
        Assertions.assertEquals(new Resume("uuid1"), storage.get("uuid1"));
    }

    @Test
    abstract void save();

    @Test
    abstract void delete();
}