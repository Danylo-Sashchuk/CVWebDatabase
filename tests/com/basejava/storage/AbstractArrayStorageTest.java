package com.basejava.storage;

import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AbstractArrayStorageTest {
    static final Storage storage = new SortedArrayStorage();
    static final Resume r1 = new Resume("uuid1");
    static final Resume r2 = new Resume("uuid2");
    static final Resume r3 = new Resume("uuid3");

    @BeforeAll
    static void beforeAll() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    void getAll() {
        Assertions.assertArrayEquals(storage.getAll(), new Resume[]{r1, r2, r3});
    }

    @Test
    void clear() {
    }

    @Test
    void size() {
    }

    @Test
    void get() {
    }

    @Test
    void update() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }
}