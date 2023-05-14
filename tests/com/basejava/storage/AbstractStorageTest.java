package com.basejava.storage;

import com.basejava.exceptions.ExistStorageException;
import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = "/Users/Danylo/Desktop/Workspace/basejava/storage";
    protected static final ResumeTestData resumeTestData = ResumeTestData.getInstance();
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String FULL_NAME_1 = "John Smith";
    protected static final String FULL_NAME_2 = "Emily Davis";
    protected static final String FULL_NAME_3 = "Robert Johnson";
    protected static final String FULL_NAME_4 = "Alice Wilson";
    protected static final String UUID_NOT_EXIST = "UUID_NOT_EXIST";
    protected static final Resume resume1 = resumeTestData.createResume(UUID_1, FULL_NAME_1);
    protected static final Resume resume2 = resumeTestData.createResume(UUID_2, FULL_NAME_2);
    protected static final Resume resume3 = resumeTestData.createResume(UUID_3, FULL_NAME_3);
    protected static final Resume resume4 = resumeTestData.createResume(UUID_4, FULL_NAME_4);
    protected static final Resume dummy = resumeTestData.createResume(UUID_NOT_EXIST);
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    final void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    protected void getAllSorted() {
        List<Resume> expected = Arrays.asList(resume2, resume1, resume3);
        Assertions.assertEquals(expected, storage.getAllSorted());
    }

    @Test
    final void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertEquals(new ArrayList<Resume>(), storage.getAllSorted());
    }

    @Test
    final void size() {
        assertSize(3);
    }

    @Test
    final void get() {
        assertGet(resume1);
        assertGet(resume2);
        assertGet(resume3);
    }

    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertSame(resume1, storage.get(UUID_1));
    }

    @Test
    final void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    final void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(resume1));
    }

    @Test
    final void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    final void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(dummy));
    }

    @Test
    final void save() {
        storage.save(resume4);
        assertSize(4);
        assertGet(resume4);
    }

    @Test
    final void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }
}