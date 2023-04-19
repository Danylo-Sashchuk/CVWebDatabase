package com.basejava.storage;

import com.basejava.model.Resume;
import org.junit.jupiter.api.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void saveBug() {
        storage.clear();
        storage.save(new Resume("u1"));
        storage.save(new Resume("u9"));
        storage.save(new Resume("u8"));
        storage.save(new Resume("u5"));
        storage.save(new Resume("u4"));
        System.out.println(storage.get("u9"));
    }
}
