package com.basejava.model;

public enum SectionType {
    PERSONAL("Личные качества"), POSITION("Позиция"), ACHIEVEMENTS("Достижения"), QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"), EDUCATION("Образование");

    @Override
    public String toString() {
        return "SectionType{" + "title='" + title + '\'' + '}';
    }

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
