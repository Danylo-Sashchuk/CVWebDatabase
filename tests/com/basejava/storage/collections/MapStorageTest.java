package com.basejava.storage.collections;

import com.basejava.model.Resume;
import com.basejava.storage.AbstractStorageTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class MapStorageTest extends AbstractStorageTest {

    protected MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    @Override
    protected void getAll() {
        List<Resume> expected = Arrays.asList(resume1, resume2, resume3);
        List<Resume> actual = Arrays.asList(storage.getAll());
        Assertions.assertEquals(3, storage.size());
        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}