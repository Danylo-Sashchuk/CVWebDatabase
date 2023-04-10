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
        storage.save(R_4);
        Assertions.assertArrayEquals(new Resume[]{R_1, R_2, R_3, R_4}, storage.getAll());
    }

    @Override
    @Test
    void delete() {
        storage.delete("uuid2");
        Assertions.assertArrayEquals(new Resume[]{R_1, R_3}, storage.getAll());
        storage.delete("uuid1");
        storage.delete("uuid3");
        Assertions.assertArrayEquals(new Resume[]{}, storage.getAll());
    }
}