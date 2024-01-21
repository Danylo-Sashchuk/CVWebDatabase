package com.webcv.storage;

import com.webcv.model.ContactType;
import com.webcv.model.Resume;
import com.webcv.model.SectionType;
import com.webcv.util.Config;
import com.webcv.util.ResumeTestData;
import org.junit.jupiter.api.Test;

/**
 * @author Danylo Sashchuk <p>
 * 01/20/24
 */

public class CreateResumeTest {
    protected static final String STORAGE_DIR = Config.get()
            .getStorageDir();
    protected static final ResumeTestData resumeTestData = ResumeTestData.getInstance();

    @Test
    void createMoreData() {
        ResumeTestData resumeTestData = ResumeTestData.getInstance();
        Storage storage = Config.get()
                .getStorage();
        Resume r = resumeTestData.createResume("Anna Frank");
        r.removeContact(ContactType.SKYPE);
        r.removeContact(ContactType.EMAIL);
        r.removeSection(SectionType.EDUCATION);
        r.removeSection(SectionType.POSITION);
        storage.save(r);
    }
}
