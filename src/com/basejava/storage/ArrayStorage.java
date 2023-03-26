package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_MAX_SIZE) {
            System.out.println("ERROR: The storage capacity is exceeded.\nNew resume has not been saved.");
        } else if (isExist(index)) {
            System.out.println("ERROR: A resume with uuid = \"" + r.getUuid() + "\" already exists.");
            System.out.println("A resume has not been saved");
        } else {
            storage[size++] = r;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR: A resume with uuid = \"" + uuid + "\" was not found.");
            return;
        }
        storage[index] = storage[size - 1];
        storage[size--] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (!isExist(index)) {
            System.out.println("ERROR: A resume with uuid = \"" + resume.getUuid() + "\" does not exist.");
            return;
        }
        storage[index] = resume;
    }

    private boolean isExist(int index) {
        return index >= 0;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
