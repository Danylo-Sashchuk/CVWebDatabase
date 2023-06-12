package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.util.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {
        super(Config.get()
                .getStorage());
    }

    @Override
    @Test
    protected void getNotExist() {
        Assertions.assertThrows(StorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Override
    @Test
    protected void deleteNotExist() {
        Assertions.assertThrows(StorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Override
    @Test
    protected void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(StorageException.class, () -> storage.get(UUID_2));
    }

    @Override
    protected void updateNotExist() {
        Assertions.assertThrows(StorageException.class, () -> storage.update(dummy));
    }

    @Override
    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }
}