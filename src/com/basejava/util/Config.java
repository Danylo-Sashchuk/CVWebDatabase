package com.basejava.util;

import com.basejava.storage.SqlStorage;
import com.basejava.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES_FILE = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final Storage storage;
    private final Properties properties = new Properties();
    private final String storageDir;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(is);
            storageDir = properties.getProperty("storage.dir");
            dbUrl = properties.getProperty("db.url");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
            storage = new SqlStorage(dbUrl, dbUser, dbPassword);
        } catch (IOException ioException) {
            throw new IllegalStateException("Invalid config file: " + PROPERTIES_FILE.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
