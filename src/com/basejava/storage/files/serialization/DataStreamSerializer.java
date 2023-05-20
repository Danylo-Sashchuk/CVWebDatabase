package com.basejava.storage.files.serialization;

import com.basejava.model.*;

import java.io.*;
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
            resume.addSection(SectionType.POSITION, new TextSection(dataInputStream.readUTF()));
            resume.addSection(SectionType.PERSONAL, new TextSection(dataInputStream.readUTF()));


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
            TextSection position = (TextSection) allSections.get(SectionType.POSITION);
            dataOutputStream.writeUTF(String.valueOf(position));

            TextSection personal = (TextSection) allSections.get(SectionType.PERSONAL);
            dataOutputStream.writeUTF(String.valueOf(personal));
        }
    }
}
