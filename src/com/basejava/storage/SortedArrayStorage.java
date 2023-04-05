package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void proceedSave(int index, Resume r) {
        if (index != STORAGE_MAX_SIZE - 1) {
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }
        storage[index] = r;
    }

    @Override
    protected void proceedDelete(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
        storage[size] = null;
    }
}
