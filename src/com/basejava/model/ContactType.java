package com.basejava.model;

public enum ContactType {
    PHONE_NUMBER("Телефон"),
    EMAIL("E-mail") {
        @Override
        public String toHtml(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    SKYPE("Skype") {
        @Override
        public String toHtml(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ContactType{" + "title='" + title + '\'' + '}';
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml1(value);
    }

    protected String toHtml1(String value) {
        return title + ": " + value;
    }
}
