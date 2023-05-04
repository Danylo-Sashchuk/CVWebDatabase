package com.basejava.model;

import java.util.List;

public class ListSection extends AbstractSection {
    private final List<String> text;

    public ListSection(List<String> text) {
        this.text = text;
    }

    public List<String> getText() {
        return text;
    }
}
