package com.webcv.storage.files.serialization;

import com.webcv.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataStreamSerializerSecondVersion implements SerializationStrategy {

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readContacts(resume, dataInputStream);

            readTextSection(SectionType.POSITION, resume, dataInputStream);
            readTextSection(SectionType.PERSONAL, resume, dataInputStream);

            readListSection(SectionType.ACHIEVEMENTS, resume, dataInputStream);
            readListSection(SectionType.QUALIFICATIONS, resume, dataInputStream);

            readCompanySection(SectionType.EXPERIENCE, resume, dataInputStream);
            readCompanySection(SectionType.EDUCATION, resume, dataInputStream);
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            writeContacts(resume, dataOutputStream);

            Map<SectionType, AbstractSection> allSections = resume.getSections();
            writeTextSection((TextSection) allSections.get(SectionType.POSITION), dataOutputStream);
            writeTextSection((TextSection) allSections.get(SectionType.PERSONAL), dataOutputStream);

            writeListSection((ListSection) allSections.get(SectionType.ACHIEVEMENTS), dataOutputStream);
            writeListSection((ListSection) allSections.get(SectionType.QUALIFICATIONS), dataOutputStream);

            writeCompanySection((CompanySection) allSections.get(SectionType.EXPERIENCE), dataOutputStream);
            writeCompanySection((CompanySection) allSections.get(SectionType.EDUCATION), dataOutputStream);
        }
    }

    private void writeCompanySection(CompanySection companySection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(companySection.getCompanies().size());
        for (Company company : companySection.getCompanies()) {
            writeCompany(company, dataOutputStream);
        }
    }

    private void writeListSection(ListSection listSection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(listSection.getTexts().size());
        for (String text : listSection.getTexts()) {
            dataOutputStream.writeUTF(text);
        }
    }

    private void writeTextSection(TextSection textSection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(textSection.toString());
    }

    private void writeContacts(Resume resume, DataOutputStream dataOutputStream) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        dataOutputStream.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dataOutputStream.writeUTF(entry.getKey().name());
            dataOutputStream.writeUTF(entry.getValue());
        }
    }

    private void readContacts(Resume resume, DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            resume.setContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
        }
    }

    private void readCompanySection(SectionType sectionType, Resume resume, DataInputStream dataInputStream) throws IOException {
        CompanySection company = getCompanySection(dataInputStream);
        resume.setContact(sectionType, company);
    }

    private void readListSection(SectionType sectionType, Resume resume, DataInputStream dataInputStream) throws IOException {
        ListSection qualifications = getListSection(dataInputStream);
        resume.setContact(sectionType, qualifications);
    }

    private void readTextSection(SectionType sectionType, Resume resume, DataInputStream dataInputStream) throws IOException {
        TextSection textSection = getTextSection(dataInputStream);
        resume.setContact(sectionType, textSection);
    }

    private void writeCompany(Company company, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(company.getPeriods().size());
        dataOutputStream.writeUTF(company.toString());
    }

    private TextSection getTextSection(DataInputStream dataInputStream) throws IOException {
        StringBuilder raw = new StringBuilder(dataInputStream.readUTF());
        raw.delete(0, 18).delete(raw.length() - 2, raw.length());
        return new TextSection(raw.toString());
    }

    private ListSection getListSection(DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            texts.add(dataInputStream.readUTF());
        }
        return new ListSection(texts);
    }

    private CompanySection getCompanySection(DataInputStream dataInputStream) throws IOException {
        int companiesSize = dataInputStream.readInt();
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < companiesSize; i++) {
            companies.add(getCompany(dataInputStream));
        }
        return new CompanySection(companies);
    }

    private Company getCompany(DataInputStream dataInputStream) throws IOException {
        int numberOfPeriods = dataInputStream.readInt();
        String rawCompany = dataInputStream.readUTF();
        String nameRegex = "name='(.*?)'";
        String name = parseString(rawCompany, nameRegex);
        String urlRegex = "url='(.*?)'";
        String url = parseString(rawCompany, urlRegex);
        List<Company.Period> periods = getPeriods(rawCompany, numberOfPeriods);
        return new Company(name, url, periods);
    }

    private List<Company.Period> getPeriods(String rawCompany, int numberOfPeriods) {
        List<Company.Period> periods = new ArrayList<>();
        for (int i = 0; i < numberOfPeriods; i++) {
            periods.add(createPeriod(rawCompany, i));
        }
        return periods;
    }

    private Company.Period createPeriod(String rawCompany, int offset) {
        String regex = "title='(.*?)', description='(.*?)', startDate=(\\d{4}-\\d{2}-\\d{2}), endDate=" + "(\\d{4" +
                "}-\\d{2}-\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rawCompany);
        for (int i = 0; i <= offset; i++) {
            matcher.find();
        }
        String title = matcher.group(1);
        String description = matcher.group(2);
        LocalDate startDate = LocalDate.parse(matcher.group(3));
        LocalDate endDate = LocalDate.parse(matcher.group(4));
        return new Company.Period(title, description, startDate, endDate);
    }

    private String parseString(String rawCompany, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rawCompany);
        matcher.find();
        return matcher.group(1);
    }
}
