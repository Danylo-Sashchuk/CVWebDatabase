package com.webcv.model;

public enum ContactType {
    PHONE_NUMBER("Phone number"),
    EMAIL("E-mail") {
        @Override
        public String toHtml(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    SKYPE("Skype") {
        @Override
        public String toHtml(String value) {
            return this.toHtml1("<a href='skype:" + value + "'>" + value + "</a>");
        }
    },
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub");

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
