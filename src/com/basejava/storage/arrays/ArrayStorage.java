package com.basejava.storage.arrays;

import com.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void proceedSave(Object searchKey, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void proceedDelete(Object searchKey) {
        storage[(int) searchKey] = storage[size];
        storage[size] = null;
    }
}
