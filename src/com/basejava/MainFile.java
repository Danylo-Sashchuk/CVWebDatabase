package com.basejava;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File start = new File(".");
        go(start);
    }

    public static void go(File file) {
        System.out.println(file);
        if (file.isDirectory()) {
            for (File f: Objects.requireNonNull(file.listFiles())) {
                go(f);
            }
        }
    }
}
