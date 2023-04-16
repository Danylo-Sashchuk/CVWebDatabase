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
    protected void deleteElement(Object searchKey) {
        System.arraycopy(storage, (int) searchKey + 1, storage, (int) searchKey, size - (int) searchKey);
        storage[size] = null;
    }
}
