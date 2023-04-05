package com.basejava.storage;

import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArrayStorageTest extends AbstractArrayStorageTest {

    ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Override
    @Test
    void save() {
        storage.save(r4);
        Assertions.assertArrayEquals(new Resume[]{r1, r2, r3, r4}, storage.getAll());
    }

    @Override
    @Test
    void delete() {
        storage.delete("uuid2");
        Assertions.assertArrayEquals(new Resume[]{r1, r3}, storage.getAll());
        storage.delete("uuid1");
        storage.delete("uuid3");
        Assertions.assertArrayEquals(new Resume[]{}, storage.getAll());
    }
}