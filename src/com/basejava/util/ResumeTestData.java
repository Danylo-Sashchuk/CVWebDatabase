package com.basejava.util;

import com.basejava.model.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ResumeTestData {
    private static final ResumeTestData instance = new ResumeTestData();
    private final MockingData mockingData;
    private Iterator<String> phonesIterator;
    private Iterator<String> emailsIterator;
    private Iterator<String> positionsIterator;
    private Iterator<String> personalsIterator;
    private Iterator<String> achievementsIterator;
    private Iterator<String> qualificationsIterator;
    private Iterator<String> workTitlesIterator;
    private Iterator<String> workDescriptionsIterator;
    private Iterator<String> datesIterator;
    private Iterator<String> companyNamesIterator;
    private Iterator<String> companyWebsitesIterator;
    private Iterator<String> educationNamesIterator;
    private Iterator<String> educationWebsitesIterator;
    private Iterator<String> educationTitlesIterator;
    private Iterator<String> educationDescriptionsIterator;

    private ResumeTestData() {
        mockingData = MockingData.getInstance();
        phonesIterator = mockingData.getIterator(mockingData.PHONE_NUMBERS);
        emailsIterator = mockingData.getIterator(mockingData.EMAILS);
        positionsIterator = mockingData.getIterator(mockingData.POSITIONS);
        personalsIterator = mockingData.getIterator(mockingData.PERSONALS);
        achievementsIterator = mockingData.getIterator(mockingData.ACHIEVEMENTS);
        qualificationsIterator = mockingData.getIterator(mockingData.QUALIFICATIONS);
        workTitlesIterator = mockingData.getIterator(mockingData.WORK_TITLES);
        workDescriptionsIterator = mockingData.getIterator(mockingData.WORK_DESCRIPTIONS);
        datesIterator = mockingData.getIterator(mockingData.DATES);
        companyNamesIterator = mockingData.getIterator(mockingData.COMPANY_NAMES);
        companyWebsitesIterator = mockingData.getIterator(mockingData.COMPANY_WEBSITES);
        educationNamesIterator = mockingData.getIterator(mockingData.EDUCATION_NAMES);
        educationWebsitesIterator = mockingData.getIterator(mockingData.EDUCATION_WEBSITES);
        educationTitlesIterator = mockingData.getIterator(mockingData.EDUCATION_TITLE);
        educationDescriptionsIterator = mockingData.getIterator(mockingData.EDUCATION_DESCRIPTION);
    }

    public static ResumeTestData getInstance() {
        return instance;
    }

    public Resume createResume(String uuid, String fullName) {
        checkIterators();
        Resume resume = new Resume(uuid, fullName);
        addContacts(fullName, resume);

        updatePersonal(resume);
        updatePosition(resume);

        updateAchievements(resume);
        addQualifications(resume);

        //        addExperiences(resume);
        //        addEducations(resume);
        return resume;
    }

    public Resume createResume(String fullName) {
        return createResume(UUID.randomUUID().toString(), fullName);
    }

    public void updatePosition(Resume resume) {
        resume.addSection(SectionType.POSITION, new TextSection(positionsIterator.next()));
    }

    public void updatePersonal(Resume resume) {
        resume.addSection(SectionType.PERSONAL, new TextSection(personalsIterator.next()));
    }

    public void updateAchievements(Resume resume) {
        ListSection achievementsSection = createListSection(achievementsIterator);
        resume.addSection(SectionType.ACHIEVEMENTS, achievementsSection);
    }

    public void addExperiences(Resume resume) {
        CompanySection experienceSection = new CompanySection(createCompaniesList(companyNamesIterator,
                companyWebsitesIterator, workTitlesIterator, workDescriptionsIterator));
        resume.addSection(SectionType.EXPERIENCE, experienceSection);
    }

    public List<Company> getExperiences() {
        return createCompaniesList(companyNamesIterator, companyWebsitesIterator, workTitlesIterator,
                workDescriptionsIterator);
    }

    private void checkIterators() {
        if (!phonesIterator.hasNext()) {
            phonesIterator = mockingData.getIterator(mockingData.PHONE_NUMBERS);
        }
        if (!emailsIterator.hasNext()) {
            emailsIterator = mockingData.getIterator(mockingData.EMAILS);
        }
        if (!positionsIterator.hasNext()) {
            positionsIterator = mockingData.getIterator(mockingData.POSITIONS);
        }
        if (!personalsIterator.hasNext()) {
            personalsIterator = mockingData.getIterator(mockingData.PERSONALS);
        }
        if (!achievementsIterator.hasNext()) {
            achievementsIterator = mockingData.getIterator(mockingData.ACHIEVEMENTS);
        }
        if (!qualificationsIterator.hasNext()) {
            qualificationsIterator = mockingData.getIterator(mockingData.QUALIFICATIONS);
        }
        if (!workTitlesIterator.hasNext()) {
            workTitlesIterator = mockingData.getIterator(mockingData.WORK_TITLES);
        }
        if (!workDescriptionsIterator.hasNext()) {
            workDescriptionsIterator = mockingData.getIterator(mockingData.WORK_DESCRIPTIONS);
        }
        if (!datesIterator.hasNext()) {
            datesIterator = mockingData.getIterator(mockingData.DATES);
        }
        if (!companyNamesIterator.hasNext()) {
            companyNamesIterator = mockingData.getIterator(mockingData.COMPANY_NAMES);
        }
        if (!companyWebsitesIterator.hasNext()) {
            companyWebsitesIterator = mockingData.getIterator(mockingData.COMPANY_WEBSITES);
        }
        if (!educationNamesIterator.hasNext()) {
            educationNamesIterator = mockingData.getIterator(mockingData.EDUCATION_NAMES);
        }
        if (!educationWebsitesIterator.hasNext()) {
            educationWebsitesIterator = mockingData.getIterator(mockingData.EDUCATION_WEBSITES);
        }
        if (!educationTitlesIterator.hasNext()) {
            educationTitlesIterator = mockingData.getIterator(mockingData.EDUCATION_TITLE);
        }
        if (!educationDescriptionsIterator.hasNext()) {
            educationDescriptionsIterator = mockingData.getIterator(mockingData.EDUCATION_DESCRIPTION);
        }
    }

    private void addContacts(String fullName, Resume resume) {
        resume.addContact(ContactType.PHONE_NUMBER, phonesIterator.next());
        resume.addContact(ContactType.EMAIL, generateEmail(fullName));
        resume.addContact(ContactType.GITHUB, generateLink(fullName, "https://github.com/"));
        resume.addContact(ContactType.LINKEDIN, generateLink(fullName, "https://www.linkedin.com/in/"));
        resume.addContact(ContactType.SKYPE, generateSkype(fullName));
    }

    private String generateEmail(String fullName) {
        fullName = fullName.replaceAll(" ", "").toLowerCase();
        return fullName + "@" + emailsIterator.next() + ".com";
    }

    private String generateLink(String fullName, String websiteUrl) {
        fullName = fullName.replaceAll(" ", "").toLowerCase();
        return websiteUrl + fullName;
    }

    private String generateSkype(String fullName) {
        fullName = fullName.replaceAll(" ", ".").toLowerCase();
        return fullName;
    }

    private ListSection createListSection(Iterator<String> infoIterator) {
        List<String> list = createComposedList(infoIterator);
        return new ListSection(list);
    }

    private List<String> createComposedList(Iterator<String> infoIterator) {
        List<String> list = new ArrayList<>();
        list.add(infoIterator.next());
        list.add(infoIterator.next());
        list.add(infoIterator.next());
        return list;
    }

    private void addQualifications(Resume resume) {
        ListSection qualificationsSection = createListSection(qualificationsIterator);
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);
    }

    private void addEducations(Resume resume) {
        CompanySection educationSection = new CompanySection(createCompaniesList(educationNamesIterator,
                educationWebsitesIterator, educationTitlesIterator, educationDescriptionsIterator));
        resume.addSection(SectionType.EDUCATION, educationSection);
    }

    private List<Company> createCompaniesList(Iterator<String> namesIterator, Iterator<String> websitesIterator,
                                              Iterator<String> titlesIterator, Iterator<String> descriptionsIterator) {
        List<Company> companies = new ArrayList<>();
        companies.add(createCompany(namesIterator, websitesIterator, titlesIterator, descriptionsIterator));
        companies.add(createCompany(namesIterator, websitesIterator, titlesIterator, descriptionsIterator));
        return companies;
    }

    private Company createCompany(Iterator<String> namesIterator, Iterator<String> websitesIterator,
                                  Iterator<String> titlesIterator, Iterator<String> descriptionsIterator) {
        List<Company.Period> periods = new ArrayList<>();
        periods.add(createPeriod(titlesIterator, descriptionsIterator));
        periods.add(createPeriod(titlesIterator, descriptionsIterator));
        return new Company(namesIterator.next(), websitesIterator.next(), periods);
    }

    private Company.Period createPeriod(Iterator<String> titleIterator, Iterator<String> descriptionIterator) {
        YearMonth start = YearMonth.parse(datesIterator.next());
        YearMonth end = YearMonth.parse(datesIterator.next());
        LocalDate startDate = DateUtil.of(start.getYear(), start.getMonth());
        LocalDate endDate = DateUtil.of(end.getYear(), end.getMonth());
        String workTitle = titleIterator.next();
        String workDescription = descriptionIterator.next();
        return new Company.Period(workTitle, workDescription, startDate, endDate);
    }
}