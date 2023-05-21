package com.basejava.storage.files.serialization;

import com.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            Map<ContactType, String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> allSections = resume.getSections();
            writeTextSection((TextSection) allSections.get(SectionType.POSITION), dataOutputStream);
            writeTextSection((TextSection) allSections.get(SectionType.PERSONAL), dataOutputStream);

            writeListSection((ListSection) allSections.get(SectionType.ACHIEVEMENTS), dataOutputStream);
            writeListSection((ListSection) allSections.get(SectionType.QUALIFICATIONS), dataOutputStream);

            writeCompanySection((CompanySection) allSections.get(SectionType.EXPERIENCE), dataOutputStream);
            writeCompanySection((CompanySection) allSections.get(SectionType.EDUCATION), dataOutputStream);
        }
    }

    private void readCompanySection(SectionType sectionType, Resume resume, DataInputStream dataInputStream) throws IOException {
        int numberOfCompanies = dataInputStream.readInt();
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < numberOfCompanies; i++) {
            companies.add(readCompany(dataInputStream));
        }
        resume.addSection(sectionType, new CompanySection(companies));
    }

    private Company readCompany(DataInputStream dataInputStream) throws IOException {
        int numberOfPeriods = dataInputStream.readInt();
        String name = dataInputStream.readUTF();
        String url = dataInputStream.readUTF();
        List<Company.Period> periods = new ArrayList<>();
        for (int i = 0; i < numberOfPeriods; i++) {
            periods.add(readPeriod(dataInputStream));
        }
        return new Company(name, url, periods);
    }

    private Company.Period readPeriod(DataInputStream dataInputStream) throws IOException {
        String title = dataInputStream.readUTF();
        String description = dataInputStream.readUTF();
        LocalDate startDate = LocalDate.parse(dataInputStream.readUTF());
        LocalDate endDate = LocalDate.parse(dataInputStream.readUTF());
        return new Company.Period(title, description, startDate, endDate);
    }

    private void writeCompanySection(CompanySection companySection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(companySection.getCompanies().size());
        for (Company company : companySection.getCompanies()) {
            writeCompany(company, dataOutputStream);
        }
    }

    private void writeCompany(Company company, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(company.getPeriods().size());
        dataOutputStream.writeUTF(company.getName());
        dataOutputStream.writeUTF(company.getWebsite().getUrl());
        for (Company.Period period : company.getPeriods()) {
            writePeriod(period, dataOutputStream);
        }
    }

    private void writePeriod(Company.Period period, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(period.getTitle());
        dataOutputStream.writeUTF(period.getDescription());
        dataOutputStream.writeUTF(period.getStartDate().toString());
        dataOutputStream.writeUTF(period.getEndDate().toString());
    }

    private void readListSection(SectionType sectionType, Resume resume, DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dataInputStream.readUTF());
        }
        resume.addSection(sectionType, new ListSection(list));
    }

    private void writeListSection(ListSection listSection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(listSection.getTexts().size());
        for (String text : listSection.getTexts()) {
            dataOutputStream.writeUTF(text);
        }
    }

    private void readTextSection(SectionType sectionType, Resume resume, DataInputStream dataInputStream) throws IOException {
        resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
    }

    private void writeTextSection(TextSection textSection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(textSection.getText());
    }
}
