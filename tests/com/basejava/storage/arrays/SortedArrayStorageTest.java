package com.basejava.storage.arrays;

import com.basejava.model.Resume;
import org.junit.jupiter.api.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void saveBug() {
        storage.clear();
        storage.save(new Resume("u1", NAMES[0]));
        storage.save(new Resume("u9", NAMES[1]));
        storage.save(new Resume("u8", NAMES[2]));
        storage.save(new Resume("u5", NAMES[3]));
        storage.save(new Resume("u4", NAMES[4]));
        System.out.println(storage.get("u9"));
    }
}
