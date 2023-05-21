package com.basejava.storage.files.serialization;

import com.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }

            int newSize = dataInputStream.readInt();

            TextSection position = getTextSection(dataInputStream);
            resume.addSection(SectionType.POSITION, position);
            TextSection personal = getTextSection(dataInputStream);
            resume.addSection(SectionType.PERSONAL, personal);

            ListSection achievements = getListSection(dataInputStream);
            resume.addSection(SectionType.ACHIEVEMENTS, achievements);
            ListSection qualifications = getListSection(dataInputStream);
            resume.addSection(SectionType.QUALIFICATIONS, qualifications);


            CompanySection companies = getCompanySection(dataInputStream);
            resume.addSection(SectionType.EXPERIENCE, companies);
            CompanySection education = getEducationSection(dataInputStream);
            resume.addSection(SectionType.EDUCATION, education);;
            return resume;
        }
    }

    private CompanySection getEducationSection(DataInputStream dataInputStream) {

        return null;
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                String a1 = entry.getKey().name();
                String a2 = entry.getValue();
                dataOutputStream.writeUTF(a1);
                dataOutputStream.writeUTF(a2);
            }

            Map<SectionType, AbstractSection> allSections = resume.getSections();
            dataOutputStream.writeInt(allSections.size());

            TextSection position = (TextSection) allSections.get(SectionType.POSITION);
            dataOutputStream.writeUTF(String.valueOf(position));
            TextSection personal = (TextSection) allSections.get(SectionType.PERSONAL);
            dataOutputStream.writeUTF(String.valueOf(personal));

            ListSection achievements = (ListSection) allSections.get(SectionType.ACHIEVEMENTS);
            dataOutputStream.writeInt(achievements.getTexts().size());
            for (String text : achievements.getTexts()) {
                dataOutputStream.writeUTF(text);
            }

            ListSection qualifications = (ListSection) allSections.get(SectionType.QUALIFICATIONS);
            dataOutputStream.writeInt(qualifications.getTexts().size());
            for (String text : qualifications.getTexts()) {
                dataOutputStream.writeUTF(text);
            }

            CompanySection experiences = (CompanySection) allSections.get(SectionType.EXPERIENCE);
            dataOutputStream.writeInt(experiences.getCompanies().size());
            for (Company company : experiences.getCompanies()) {
                writeCompany(company, dataOutputStream);
            }
        }
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
