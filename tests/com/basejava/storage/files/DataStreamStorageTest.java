package com.basejava.storage.files;

import com.basejava.model.*;
import com.basejava.storage.files.serialization.DataStreamSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import java.nio.file.Paths;
import java.util.List;

public class DataStreamStorageTest extends AbstractFilesStorageTest {
    protected DataStreamStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new DataStreamSerializer()));
    }

    @Test
    void getSave_withMoreInformation() {
        List<Company> companies = getCompanies(resume1);
        companies.add(resumeTestData.getExperiences().get(0));
        companies.add(resumeTestData.getExperiences().get(1));

        deleteThenSave(UUID_1, resume1);

        Resume actual = storage.get(UUID_1);
        Assertions.assertEquals(resume1, actual);
    }

    @Test
    void saveGet_when_urlIsNull() {
        Company company = getCompany(resume1, 0);
        Link website = company.getWebsite();
        website.setUrl(null);

        deleteThenSave(UUID_1, resume1);

        Resume actual = storage.get(UUID_1);
        Assertions.assertEquals(actual, resume1);
    }

    @Test
    void saveGet_when_descriptionIsNull() {
        Company company = getCompany(resume2, 1);
        company.getPeriods().get(0).setDescription(null);

        deleteThenSave(UUID_2, resume2);

        Resume actual = storage.get(UUID_2);
        Assertions.assertEquals(actual, resume2);
    }

    private void deleteThenSave(String uuid, Resume resume) {
        storage.delete(uuid);
        storage.save(resume);
    }

    private Company getCompany(Resume resume, int index) {
        List<Company> companies = getCompanies(resume);
        if (companies.size() < index) {
            throw new TestAbortedException("Resume does not have that many companies");
        }
        return companies.get(index);
    }

    private List<Company> getCompanies(Resume resume) {
        return ((CompanySection) resume.getSections().get(SectionType.EXPERIENCE)).getCompanies();
    }
}
