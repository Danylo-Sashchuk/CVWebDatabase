package com.basejava.model;

public enum ContactType {
    PHONENUMBER("Телефон"), EMAIL("E-mail"), SKYPE("Skype"), LINK("Ссылка");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
