package com.basejava.storage.files;

import com.basejava.model.*;
import com.basejava.storage.files.serialization.DataStreamSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class DataStreamStorageTest extends AbstractFilesStorageTest {
    protected DataStreamStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new DataStreamSerializer()));
    }

    @Test
    void getSave_withMoreInformation() {
        CompanySection experience = (CompanySection) resume1.getSections().get(SectionType.EXPERIENCE);
        experience.getCompanies().add(resumeTestData.getExperiences().get(0));
        experience.getCompanies().add(resumeTestData.getExperiences().get(1));
        storage.delete(UUID_1);
        storage.save(resume1);
        Resume actual = storage.get(UUID_1);
        Assertions.assertEquals(resume1, actual);
    }

    @Test
    void saveGet_when_urlAndDescriptionAreEqualToNull() {
        Company company = ((CompanySection) resume1.getSections().get(SectionType.EXPERIENCE)).getCompanies().get(0);

        Link website = company.getWebsite();
        website.setUrl(null);
        company.getPeriods().get(0).setDescription(null);
        storage.delete(UUID_1);
        storage.save(resume1);
        Resume got = storage.get(UUID_1);
        Assertions.assertEquals(got, resume1);
    }
}
