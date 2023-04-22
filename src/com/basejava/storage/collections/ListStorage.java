package com.basejava.storage.collections;

import com.basejava.model.Resume;
import com.basejava.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(storage);
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
