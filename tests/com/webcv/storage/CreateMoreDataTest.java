package com.webcv.storage;

import com.webcv.util.Config;
import com.webcv.util.ResumeTestData;
import org.junit.jupiter.api.Test;

/**
 * @author Danylo Sashchuk <p>
 * 11/22/23
 */

public class CreateMoreDataTest {
    protected static final String STORAGE_DIR = Config.get()
            .getStorageDir();
    protected static final ResumeTestData resumeTestData = ResumeTestData.getInstance();

    @Test
    void createMoreData() {
        ResumeTestData resumeTestData = ResumeTestData.getInstance();
        Storage storage = Config.get()
                .getStorage();
        storage.save(resumeTestData.createResume("Michael Miller"));
        storage.save(resumeTestData.createResume("David Brown"));
        storage.save(resumeTestData.createResume("Richard Jones"));
        storage.save(resumeTestData.createResume("Joseph Taylor"));
        storage.save(resumeTestData.createResume("Thomas Anderson"));
        storage.save(resumeTestData.createResume("Charles Thomas"));
        storage.save(resumeTestData.createResume("Christopher Jackson"));
        storage.save(resumeTestData.createResume("Daniel White"));
    }
}
