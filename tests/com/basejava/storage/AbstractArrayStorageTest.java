package com.basejava.storage;

import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

abstract class AbstractArrayStorageTest {

    Storage storage;
    final Resume r1 = new Resume("uuid1");
    final Resume r2 = new Resume("uuid2");
    final Resume r3 = new Resume("uuid3");
    final Resume r4 = new Resume("uuid4");

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    final void getAll() {
        Assertions.assertArrayEquals(new Resume[]{r1, r2, r3}, storage.getAll());
    }

    @Test
    final void clear() {
        storage.clear();
        Assertions.assertArrayEquals(new Resume[]{}, storage.getAll());
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    final void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    final void update() {
        storage.update(r1);
        Assertions.assertArrayEquals(new Resume[]{r1, r2, r3}, storage.getAll());
    }

    @Test
    final void notExistStorageException() {
        try {
            storage.get("non-existent uuid");
            fail("NotExistException has not been thrown.");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    final void existStorageException() {
        try {
            storage.save(new Resume("uuid1"));
            fail("NotExistException has not been thrown.");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    final void storageOverflowException() {
        storage.save(r4);
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
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
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