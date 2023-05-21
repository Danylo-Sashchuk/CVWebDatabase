package com.basejava.storage.files;

import com.basejava.model.CompanySection;
import com.basejava.model.Resume;
import com.basejava.model.SectionType;
import com.basejava.storage.ResumeTestData;
import com.basejava.storage.files.serialization.DataStreamSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class DataStreamStorageTest extends AbstractFilesStorageTest {
    protected DataStreamStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new DataStreamSerializer()));
    }

    @Test
    void getSave_addMoreInformationAndCompare_true() {
        CompanySection experience = (CompanySection) resume1.getSections().get(SectionType.EXPERIENCE);
        experience.getCompanies().add(resumeTestData.getExperiences().get(0));
        experience.getCompanies().add(resumeTestData.getExperiences().get(1));
        storage.delete(UUID_1);
        storage.save(resume1);
        Resume actual = storage.get(UUID_1);
        Assertions.assertEquals(resume1, actual);
    }
}
