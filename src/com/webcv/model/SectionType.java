package com.webcv.model;

public enum SectionType {
    POSITION("Позиция"), EXPERIENCE("Опыт работы"), ACHIEVEMENTS("Достижения"), QUALIFICATIONS("Квалификация"),
    EDUCATION("Образование"), PERSONAL("Личные качества");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SectionType{" + "title='" + title + '\'' + '}';
    }

    public String getTitle() {
        return title;
    }
}
