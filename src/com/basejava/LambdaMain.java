package com.basejava;

import com.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class LambdaMain {
    public static void main(String[] args) {
        Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
        Resume[] storage = new Resume[10];
        storage[0] = new Resume("John Smith");
        storage[1] = new Resume("Emily Davis");
        storage[2] = new Resume("Robert Johnson");
        storage[3] = new Resume("Alice Wilson");
        long start1 = System.nanoTime();
        List<Resume> list = Arrays.stream(storage).filter(Objects::nonNull).sorted(RESUME_COMPARATOR).toList();
        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));


        start1 = System.nanoTime();
        Resume[] resumes = new Resume[4];
        System.arraycopy(storage, 0, resumes, 0, 4);
        Arrays.sort(resumes, RESUME_COMPARATOR);
        list = Arrays.asList(resumes);
        end1 = System.nanoTime();
        System.out.println("ELAPSED TIME IN NANO SECONDS: "+ (end1-start1));

    }
}
