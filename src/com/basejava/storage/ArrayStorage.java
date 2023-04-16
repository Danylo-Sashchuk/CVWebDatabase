package com.basejava.storage;

import com.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void proceedSave(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected void deleteElement(int index) {
        storage[index] = storage[size];
        storage[size] = null;
    }
}
