package com.webcv.model;

public enum SectionType {
    POSITION("Position"), EXPERIENCE("Experience"), ACHIEVEMENTS("Achievements"), QUALIFICATIONS("Qualifications"),
    EDUCATION("Education"), PERSONAL("Personal");

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
