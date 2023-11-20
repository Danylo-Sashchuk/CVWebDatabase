package com.webcv;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File start = new File("./src/com/webcv");
        go(start, new StringBuilder());
    }

    public static void go(File file, StringBuilder offset) {
        System.out.println(offset + file.getName());
        if (file.isDirectory()) {
            offset.append("    ");
            for (File f : Objects.requireNonNull(file.listFiles())) {
                go(f, offset);
            }
            offset.delete(0, 4);
        }
    }
}
