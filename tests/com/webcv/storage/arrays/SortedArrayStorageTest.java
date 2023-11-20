package com.webcv.storage.arrays;

import org.junit.jupiter.api.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void saveBug() {
        storage.clear();
        storage.save(resumeTestData.createResume("u1", NAMES[0]));
        storage.save(resumeTestData.createResume("u9", NAMES[1]));
        storage.save(resumeTestData.createResume("u8", NAMES[2]));
        storage.save(resumeTestData.createResume("u5", NAMES[3]));
        storage.save(resumeTestData.createResume("u4", NAMES[4]));
        System.out.println(storage.get("u9"));
    }
}
