package com.basejava.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private String uuid;
    private String fullName;

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        if (uuid.isEmpty()) {
            throw new IllegalArgumentException("uuid must not be empty");
        }
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume() {
    }

    public Map<ContactType, String> getContacts() {
        return new HashMap<>(contacts);
    }

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void removeContact(ContactType type) {
        contacts.remove(type);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return new HashMap<>(sections);
    }

    public void addSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Resume{" + "uuid='" + uuid + '\'' + ", fullName='" + fullName + '\'' + ", contacts=" + contacts + ", "
               + "sections=" + sections + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(contacts, resume.contacts) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }
}
