package com.basejava.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES_FILE = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final Properties properties = new Properties();
    private final String storageDir;

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(is);
            storageDir = properties.getProperty("storage.dir");
        } catch (IOException ioException) {
            throw new IllegalStateException("Invalid config file: " + PROPERTIES_FILE.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public String getStorageDir() {
        return storageDir;
    }
}
