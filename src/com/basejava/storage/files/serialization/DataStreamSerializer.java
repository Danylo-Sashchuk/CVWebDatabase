package com.basejava.storage.files.serialization;

import com.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializationStrategy {
    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contactsNumber = dataInputStream.readInt();
            for (int i = 0; i < contactsNumber; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }

            int sectionsNumber = dataInputStream.readInt();
            for (int i = 0; i < sectionsNumber; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType) {
                    case PERSONAL, POSITION -> {
                        resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                    }
                    case ACHIEVEMENTS, QUALIFICATIONS -> {
                        int numberOfPoints = dataInputStream.readInt();
                        List<String> list = new ArrayList<>(numberOfPoints);
                        for (int j = 0; j < numberOfPoints; j++) {
                            list.add(dataInputStream.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(list));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        int numberOfCompanies = dataInputStream.readInt();
                        List<Company> companies = new ArrayList<>();
                        for (int j = 0; j < numberOfCompanies; j++) {
                            int numberOfPeriods = dataInputStream.readInt();
                            String name = dataInputStream.readUTF();
                            String url = dataInputStream.readUTF();
                            List<Company.Period> periods = new ArrayList<>();
                            for (int k = 0; k < numberOfPeriods; k++) {
                                String title = dataInputStream.readUTF();
                                String description = dataInputStream.readUTF();
                                LocalDate startDate = LocalDate.parse(dataInputStream.readUTF());
                                LocalDate endDate = LocalDate.parse(dataInputStream.readUTF());
                                periods.add(new Company.Period(title, description, startDate, endDate));
                            }
                            companies.add(new Company(name, url, periods));
                        }
                        resume.addSection(sectionType, new CompanySection(companies));
                    }
                }
            }
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
            Set<Map.Entry<ContactType, String>> entries = contacts.entrySet();
            writeWithException(entries, dataOutputStream, ((entry, dataOutputStream1) -> {
                dataOutputStream1.writeUTF(entry.getKey().name());
                dataOutputStream1.writeUTF(entry.getValue());
            }));

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dataOutputStream.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                SectionType sectionType = section.getKey();
                AbstractSection abstractSection = section.getValue();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL, POSITION -> {
                        dataOutputStream.writeUTF(((TextSection) abstractSection).getText());
                    }
                    case ACHIEVEMENTS, QUALIFICATIONS -> {
                        List<String> texts = ((ListSection) abstractSection).getTexts();
                        dataOutputStream.writeInt(texts.size());
                        writeWithException(texts, dataOutputStream,
                                (text, dataOutputStream1) -> dataOutputStream1.writeUTF(text));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companies = ((CompanySection) abstractSection).getCompanies();
                        dataOutputStream.writeInt(companies.size());
                        writeWithException(companies, dataOutputStream, ((company, dataOutputStream1) -> {
                            dataOutputStream1.writeInt(company.getPeriods().size());
                            dataOutputStream1.writeUTF(company.getName());
                            dataOutputStream1.writeUTF(company.getWebsite().getUrl());
                            for (Company.Period period : company.getPeriods()) {
                                dataOutputStream1.writeUTF(period.getTitle());
                                dataOutputStream1.writeUTF(period.getDescription());
                                dataOutputStream1.writeUTF(period.getStartDate().toString());
                                dataOutputStream1.writeUTF(period.getEndDate().toString());
                            }
                        }));
                    }
                }
            }
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dataOutputStream,
                                        Communicator<T> communicator) throws IOException {
        Objects.requireNonNull(collection);
        for (T t : collection) {
            communicator.communicate(t, dataOutputStream);
        }
    }

    @FunctionalInterface
    interface Communicator<T> {
        void communicate(T t, DataOutputStream dataOutputStream) throws IOException;
    }

    private void readContacts(Resume resume, DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
        }
    }

    private void readTextSection(SectionType sectionType, Resume resume, DataInputStream dataInputStream) throws IOException {
        resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
    }

    private void readListSection(SectionType sectionType, Resume resume, DataInputStream dataInputStream) throws IOException {
        int size = dataInputStream.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dataInputStream.readUTF());
        }
        resume.addSection(sectionType, new ListSection(list));
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

    private void writeContacts(Resume resume, DataOutputStream dataOutputStream) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        dataOutputStream.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dataOutputStream.writeUTF(entry.getKey().name());
            dataOutputStream.writeUTF(entry.getValue());
        }
    }

    private void writeTextSection(TextSection textSection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(textSection.getText());
    }

    private void writeListSection(ListSection listSection, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(listSection.getTexts().size());
        for (String text : listSection.getTexts()) {
            dataOutputStream.writeUTF(text);
        }
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
}
