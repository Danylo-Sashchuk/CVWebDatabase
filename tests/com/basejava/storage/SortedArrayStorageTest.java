package com.basejava.storage;

import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Override
    @Test
    void save() {
        storage.save(R_4);
        Assertions.assertArrayEquals(new Resume[]{R_1, R_2, R_3, R_4}, storage.getAll());
        storage.save(new Resume("uuid5"));
        storage.save(new Resume("uuid6"));
        storage.save(new Resume("uuid7"));
        storage.save(new Resume("uuid8"));
        storage.save(new Resume("uuid9"));
        storage.save(new Resume("uuid10"));
        Assertions.assertArrayEquals(new Resume[]{R_1, R_2, R_3, R_4, new Resume("uuid5"),
                new Resume("uuid6"), new Resume("uuid7"), new Resume("uuid8"),
                new Resume("uuid9"), new Resume("uuid10")}, storage.getAll());
    }

    @Test
    @Override
    void delete() {
        storage.save(R_4);
        storage.delete(R_2.getUuid());
        Assertions.assertArrayEquals(new Resume[] {R_1, R_3, R_4}, storage.getAll());
        storage.save(new Resume("uuid5"));
        storage.save(new Resume("uuid7"));
        storage.delete("uuid5");
        storage.delete("uuid1");
        Assertions.assertArrayEquals(new Resume[] {R_3, R_4, new Resume("uuid7")},
                storage.getAll());
    }
}
