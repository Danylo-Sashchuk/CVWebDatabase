package com.basejava.storage;

import com.basejava.model.*;
import com.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResumeTestData {

    private static Iterator<String> phonesIterator = MockingData.getIterator(MockingData.PHONE_NUMBERS);
    private static Iterator<String> emailsIterator = MockingData.getIterator(MockingData.EMAILS);
    private static Iterator<String> positionsIterator = MockingData.getIterator(MockingData.POSITIONS);
    private static Iterator<String> personalsIterator = MockingData.getIterator(MockingData.PERSONALS);
    private static Iterator<String> achievementsIterator = MockingData.getIterator(MockingData.ACHIEVEMENTS);
    private static Iterator<String> qualificationsIterator = MockingData.getIterator(MockingData.QUALIFICATIONS);
    private static Iterator<String> workTitlesIterator = MockingData.getIterator(MockingData.WORK_TITLES);
    private static Iterator<String> workDescriptionsIterator = MockingData.getIterator(MockingData.WORK_DESCRIPTIONS);
    private static Iterator<String> datesIterator = MockingData.getIterator(MockingData.DATES);
    private static Iterator<String> companyNamesIterator = MockingData.getIterator(MockingData.COMPANY_NAMES);
    private static Iterator<String> companyWebsitesIterator = MockingData.getIterator(MockingData.COMPANY_WEBSITES);
    private static Iterator<String> educationNamesIterator = MockingData.getIterator(MockingData.EDUCATION_NAMES);
    private static Iterator<String> educationWebsitesIterator = MockingData.getIterator(MockingData.EDUCATION_WEBSITES);
    private static Iterator<String> educationTitlesIterator = MockingData.getIterator(MockingData.EDUCATION_TITLE);
    private static Iterator<String> educationDescriptionsIterator =
            MockingData.getIterator(MockingData.EDUCATION_DESCRIPTION);

    public static Resume createResume(String uuid, String fullName) {
        if (!phonesIterator.hasNext()) {
            reloadIterators();
        }
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE_NUMBER, phonesIterator.next());
        resume.addContact(ContactType.EMAIL, generateEmail(fullName));
        resume.addContact(ContactType.GITHUB, generateLink(fullName, "https://github.com/"));
        resume.addContact(ContactType.LINKEDIN, generateLink(fullName, "https://www.linkedin.com/in/"));
        resume.addContact(ContactType.SKYPE, generateSkype(fullName));

        resume.addSection(SectionType.POSITION, new TextSection(positionsIterator.next()));
        resume.addSection(SectionType.PERSONAL, new TextSection(personalsIterator.next()));

        ListSection achievementsSection = createListSection(achievementsIterator);
        resume.addSection(SectionType.ACHIEVEMENTS, achievementsSection);
        ListSection qualificationsSection = createListSection(qualificationsIterator);
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);

        resume.addSection(SectionType.EXPERIENCE, new CompanySection(createCompaniesList(companyNamesIterator, companyWebsitesIterator, workTitlesIterator, workDescriptionsIterator)));
        resume.addSection(SectionType.EDUCATION, new CompanySection(createCompaniesList(educationNamesIterator, educationWebsitesIterator, educationTitlesIterator, educationDescriptionsIterator)));
        return resume;
    }

    private static ListSection createListSection(Iterator<String> iterator) {
        List<String> list = createComposedList(iterator);
        return new ListSection(list);
    }

    public static String generateEmail(String fullName) {
        fullName = fullName.replaceAll(" ", "").toLowerCase();
        return fullName + "@" + emailsIterator.next() + ".com";
    }

    public static String generateLink(String fullName, String websiteUrl) {
        fullName = fullName.replaceAll(" ", "").toLowerCase();
        return websiteUrl + fullName;
    }

    public static String generateSkype(String fullName) {
        fullName = fullName.replaceAll(" ", ".").toLowerCase();
        return fullName;
    }

    private static List<String> createComposedList(Iterator<String> iterator) {
        List<String> list = new ArrayList<>();
        list.add(iterator.next());
        list.add(iterator.next());
        list.add(iterator.next());
        return list;
    }

    public static List<Company> createCompaniesList(Iterator<String> namesIterator, Iterator<String> websitesIterator, Iterator<String> titlesIterator, Iterator<String> descriptionsIterator) {
        List<Company> companies = new ArrayList<>();
        List<Period> periods = getTwoPeriods(titlesIterator, descriptionsIterator);
        companies.add(createCompany(namesIterator, websitesIterator, periods));
        periods = getTwoPeriods(titlesIterator, descriptionsIterator);
        companies.add(createCompany(namesIterator, websitesIterator, periods));
        return companies;
    }

    private static Company createCompany(Iterator<String> namesIterator, Iterator<String> websitesIterator,List<Period> firstPeriodsPair) {
        return new Company(namesIterator.next(), websitesIterator.next(), firstPeriodsPair);
    }

    private static List<Period> getTwoPeriods(Iterator<String> titleIterator, Iterator<String> descriptionIterator) {
        List<Period> periods = new ArrayList<>();
        periods.add(createPeriod(titleIterator, descriptionIterator));
        periods.add(createPeriod(titleIterator, descriptionIterator));
        return periods;
    }

    private static Period createPeriod(Iterator<String> titleIterator, Iterator<String> descriptionIterator) {
        YearMonth start = YearMonth.parse(datesIterator.next());
        YearMonth end = YearMonth.parse(datesIterator.next());
        LocalDate startDate = DateUtil.of(start.getYear(), start.getMonth());
        LocalDate endDate = DateUtil.of(end.getYear(), end.getMonth());
        String workTitle = titleIterator.next();
        String workDescription = descriptionIterator.next();
        return new Period(workTitle, workDescription, startDate, endDate);
    }

    private static void reloadIterators() {
        phonesIterator = MockingData.getIterator(MockingData.PHONE_NUMBERS);
        emailsIterator = MockingData.getIterator(MockingData.EMAILS);
        positionsIterator = MockingData.getIterator(MockingData.POSITIONS);
        personalsIterator = MockingData.getIterator(MockingData.PERSONALS);
        achievementsIterator = MockingData.getIterator(MockingData.ACHIEVEMENTS);
        qualificationsIterator = MockingData.getIterator(MockingData.QUALIFICATIONS);
        workTitlesIterator = MockingData.getIterator(MockingData.WORK_TITLES);
        workDescriptionsIterator = MockingData.getIterator(MockingData.WORK_DESCRIPTIONS);
        datesIterator = MockingData.getIterator(MockingData.DATES);
        companyNamesIterator = MockingData.getIterator(MockingData.COMPANY_NAMES);
        companyWebsitesIterator = MockingData.getIterator(MockingData.COMPANY_WEBSITES);
        educationNamesIterator = MockingData.getIterator(MockingData.EDUCATION_NAMES);
        educationWebsitesIterator = MockingData.getIterator(MockingData.EDUCATION_WEBSITES);
        educationTitlesIterator = MockingData.getIterator(MockingData.EDUCATION_TITLE);
        educationDescriptionsIterator = MockingData.getIterator(MockingData.EDUCATION_DESCRIPTION);
    }

    public static void main(String[] args) {
        final String UUID_1 = "uuid1";
        final String UUID_2 = "uuid2";
        final String UUID_3 = "uuid3";
        final String UUID_4 = "uuid4";
        final String FULL_NAME_1 = "John Smith";
        final String FULL_NAME_2 = "Emily Davis";
        final String FULL_NAME_3 = "Robert Johnson";
        final String FULL_NAME_4 = "Alice Wilson";
        System.out.println(createResume(UUID_1, FULL_NAME_1)); //1
        System.out.println(createResume(UUID_2, FULL_NAME_2));
        System.out.println(createResume(UUID_3, FULL_NAME_3));//3
        System.out.println(createResume(UUID_4, FULL_NAME_4));
        System.out.println(createResume(UUID_4, FULL_NAME_4));//5
        System.out.println(createResume(UUID_4, FULL_NAME_4));
        System.out.println(createResume(UUID_4, FULL_NAME_4));//7
        System.out.println(createResume(UUID_4, FULL_NAME_4));
        System.out.println(createResume(UUID_4, FULL_NAME_4));//9
        System.out.println(createResume(UUID_4, FULL_NAME_4));
    }
}
