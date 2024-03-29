package com.webcv.storage.arrays;

import com.webcv.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void proceedSave(Integer searchKey, Resume resume) {
        int index = -searchKey - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void proceedDelete(Integer searchKey) {
        int index = searchKey;
        System.arraycopy(storage, index + 1, storage, index, size - index);
        storage[size] = null;
    }
}
