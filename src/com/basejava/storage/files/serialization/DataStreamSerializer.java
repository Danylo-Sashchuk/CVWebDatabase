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

            readWithException(dataInputStream, () -> {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
                return null;
            });

            int sectionsNumber = dataInputStream.readInt();
            for (int i = 0; i < sectionsNumber; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType) {
                    case PERSONAL, POSITION ->
                            resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                    case ACHIEVEMENTS, QUALIFICATIONS -> {
                        List<String> list = readWithException(dataInputStream, dataInputStream::readUTF);
                        resume.addSection(sectionType, new ListSection(list));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Company> companies = readWithException(dataInputStream, () -> {
                            String name = dataInputStream.readUTF();
                            String url = dataInputStream.readUTF();
                            List<Company.Period> periods = readWithException(dataInputStream, () -> {
                                String title = dataInputStream.readUTF();
                                String description = dataInputStream.readUTF();
                                LocalDate startDate = LocalDate.parse(dataInputStream.readUTF());
                                LocalDate endDate = LocalDate.parse(dataInputStream.readUTF());
                                return new Company.Period(title, description, startDate, endDate);
                            });
                            return new Company(name, url, periods);
                        });
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
            writeWithException(contacts.entrySet(), dataOutputStream, entry -> {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dataOutputStream.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                SectionType sectionType = section.getKey();
                AbstractSection abstractSection = section.getValue();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL, POSITION -> dataOutputStream.writeUTF(((TextSection) abstractSection).getText());
                    case ACHIEVEMENTS, QUALIFICATIONS -> {
                        List<String> texts = ((ListSection) abstractSection).getTexts();
                        writeWithException(texts, dataOutputStream, dataOutputStream::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companies = ((CompanySection) abstractSection).getCompanies();
                        writeWithException(companies, dataOutputStream, company -> writeCompany(dataOutputStream, company));
                    }
                }
            }
        }
    }

    private void writeCompany(DataOutputStream dataOutputStream, Company company) throws IOException {
        dataOutputStream.writeUTF(company.getName());
        dataOutputStream.writeUTF(company.getWebsite().getUrl());
        dataOutputStream.writeInt(company.getPeriods().size());
        writePeriods(dataOutputStream, company);
    }

    private void writePeriods(DataOutputStream dataOutputStream, Company company) throws IOException {
        for (Company.Period period : company.getPeriods()) {
            dataOutputStream.writeUTF(period.getTitle());
            dataOutputStream.writeUTF(period.getDescription());
            dataOutputStream.writeUTF(period.getStartDate().toString());
            dataOutputStream.writeUTF(period.getEndDate().toString());
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dataOutputStream,
                                        Writer<T> writer) throws IOException {
        Objects.requireNonNull(collection);
        dataOutputStream.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private <T> List<T> readWithException(DataInputStream dataInputStream, Reader<T> reader) throws IOException {
        List<T> list = new ArrayList<>();
        int collectionSize = dataInputStream.readInt();
        for (int i = 0; i < collectionSize; i++) {
            list.add(reader.read());
        }
        return list;
    }

    @FunctionalInterface
    interface Writer<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    interface Reader<T> {
        T read() throws IOException;
    }
}
