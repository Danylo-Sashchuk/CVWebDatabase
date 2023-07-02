package com.basejava.util;

import com.basejava.model.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ResumeTestData {
    private static final ResumeTestData instance = new ResumeTestData();
    private final Map<DataType, Iterator<String>> iterators;
    private final MockingData mockingData;

    private ResumeTestData() {
        mockingData = MockingData.getInstance();
        iterators = mapIterators();
    }

    public static ResumeTestData getInstance() {
        return instance;
    }

    public Resume createResume(String uuid, String fullName) {
        reloadIterators();
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
        resume.addSection(SectionType.POSITION, new TextSection(iterators.get(DataType.POSITION).next()));
    }

    public void updatePersonal(Resume resume) {
        resume.addSection(SectionType.PERSONAL, new TextSection(iterators.get(DataType.PERSONAL).next()));
    }

    public void updateAchievements(Resume resume) {
        ListSection achievementsSection = createListSection(iterators.get(DataType.ACHIEVEMENT));
        resume.addSection(SectionType.ACHIEVEMENTS, achievementsSection);
    }

    public void addExperiences(Resume resume) {
        CompanySection experienceSection =
                new CompanySection(createCompaniesList(iterators.get(DataType.COMPANY_NAME),
                        iterators.get(DataType.COMPANY_WEBSITE), iterators.get(DataType.WORK_TITLE),
                        iterators.get(DataType.WORK_DESCRIPTION)));
        resume.addSection(SectionType.EXPERIENCE, experienceSection);
    }

    public List<Company> getExperiences() {
        return createCompaniesList(iterators.get(DataType.COMPANY_NAME), iterators.get(DataType.COMPANY_WEBSITE),
                iterators.get(DataType.WORK_TITLE), iterators.get(DataType.WORK_DESCRIPTION));
    }

    private Map<DataType, Iterator<String>> mapIterators() {
        Map<DataType, Iterator<String>> iterators = new HashMap<>();
        iterators.put(DataType.PHONE_NUMBER, mockingData.getIterator(mockingData.PHONE_NUMBERS));
        iterators.put(DataType.EMAIL, mockingData.getIterator(mockingData.EMAILS));
        iterators.put(DataType.POSITION, mockingData.getIterator(mockingData.POSITIONS));
        iterators.put(DataType.PERSONAL, mockingData.getIterator(mockingData.PERSONALS));
        iterators.put(DataType.ACHIEVEMENT, mockingData.getIterator(mockingData.ACHIEVEMENTS));
        iterators.put(DataType.QUALIFICATION, mockingData.getIterator(mockingData.QUALIFICATIONS));
        iterators.put(DataType.WORK_TITLE, mockingData.getIterator(mockingData.WORK_TITLES));
        iterators.put(DataType.WORK_DESCRIPTION, mockingData.getIterator(mockingData.WORK_DESCRIPTIONS));
        iterators.put(DataType.DATE, mockingData.getIterator(mockingData.DATES));
        iterators.put(DataType.COMPANY_NAME, mockingData.getIterator(mockingData.COMPANY_NAMES));
        iterators.put(DataType.COMPANY_WEBSITE, mockingData.getIterator(mockingData.COMPANY_WEBSITES));
        iterators.put(DataType.EDUCATION_NAME, mockingData.getIterator(mockingData.EDUCATION_NAMES));
        iterators.put(DataType.EDUCATION_WEBSITE, mockingData.getIterator(mockingData.EDUCATION_WEBSITES));
        iterators.put(DataType.EDUCATION_TITLE, mockingData.getIterator(mockingData.EDUCATION_TITLE));
        iterators.put(DataType.EDUCATION_DESCRIPTION, mockingData.getIterator(mockingData.EDUCATION_DESCRIPTION));
        return iterators;
    }

    private void reloadIterators() {
        for (Map.Entry<DataType, Iterator<String>> entry : iterators.entrySet()) {
            if (!entry.getValue().hasNext()) {
                entry.getValue().
                entry.setValue(mockingData.getIterator());
            }
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
