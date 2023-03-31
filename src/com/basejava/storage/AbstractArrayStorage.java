package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_MAX_SIZE = 10000;
    protected final Resume[] storage = new Resume[STORAGE_MAX_SIZE];
    protected int size = 0;

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR: A resume with uuid = \"" + uuid + "\" does not exist.");
            return null;
        }
        return storage[index];
    }

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (!isExist(index)) {
            System.out.println("ERROR: A resume with uuid = \"" + resume.getUuid() + "\" does not exist.");
            return;
        }
        storage[index] = resume;
    }

    @Override
    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_MAX_SIZE) {
            System.out.println("ERROR: The storage capacity is exceeded.\nNew resume has not been saved.");
        } else if (isExist(index)) {
            System.out.println("ERROR: A resume with uuid = \"" + r.getUuid() + "\" already exists.");
            System.out.println("A resume has not been saved");
        } else {
            proceedSave(size++, r);
        }
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR: A resume with uuid = \"" + uuid + "\" was not found.");
            return;
        }
        size--;
        proceedDelete(index);
    }

    protected boolean isExist(int index) {
        return index >= 0;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void proceedSave(int index, Resume r);

    protected abstract void proceedDelete(int index);
}
