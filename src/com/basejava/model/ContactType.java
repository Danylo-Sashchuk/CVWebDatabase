package com.basejava.model;

public enum ContactType {
    PHONE_NUMBER("Телефон"), EMAIL("E-mail"), SKYPE("Skype"), LINK("Ссылка");

    private final String title;

    @Override
    public String toString() {
        return "ContactType{" + "title='" + title + '\'' + '}';
    }

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
