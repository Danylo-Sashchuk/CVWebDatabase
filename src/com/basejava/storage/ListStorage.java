package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    protected final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void saveElement(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteElement(int index) {
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
