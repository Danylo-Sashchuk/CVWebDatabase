package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_MAX_SIZE = 10000;
    private final Resume[] storage = new Resume[STORAGE_MAX_SIZE];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if(isExist(r)) {
            System.out.println("ERROR: A resume with uuid = \"" + r.getUuid() + "\" already exists.");
            System.out.println("A resume has not been saved");
            return;
        }
        if (size == STORAGE_MAX_SIZE) {
            System.out.println("ERROR: The storage capacity is exceeded.\nNew resume has not been saved.");
            return;
        }
        storage[size++] = r;
    }

    private boolean isExist(Resume r) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                return true;
            }
        }
        return false;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("ERROR: A resume with uuid = \"" + uuid + "\" does not exist.");
        return null;
    }

    public void delete(String uuid) {
        int indexForDelete = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                indexForDelete = i;
                break;
            }
        }
        if (indexForDelete == -1) {
            System.out.println("ERROR: A resume with uuid = \"" + uuid + "\" was not found.");
            return;
        }
        storage[indexForDelete] = storage[size - 1];
        storage[size--] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        if (!isExist(resume)) {
            System.out.println("ERROR: A resume with uuid = \"" + resume.getUuid() + "\" does not exist.");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (resume.getUuid().equals(storage[i].getUuid())) {
                storage[i] = resume;
                return;
            }
        }
    }
}
