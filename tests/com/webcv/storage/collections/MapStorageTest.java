package com.webcv.storage.collections;

import com.webcv.model.Resume;
import com.webcv.storage.AbstractStorageTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class MapStorageTest extends AbstractStorageTest {

    protected MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    @Override
    protected void getAllSorted() {
        List<Resume> expected = Arrays.asList(resume1, resume2, resume3);
        List<Resume> actual = storage.getAllSorted();
        Assertions.assertEquals(3, storage.size());
        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}