package com.basejava.storage;

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
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }
}