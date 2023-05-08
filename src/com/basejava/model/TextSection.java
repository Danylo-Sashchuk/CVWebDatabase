package com.basejava.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private String text;

    public TextSection(String text) {
        Objects.requireNonNull(text, "Text must not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextSection{" + "text='" + text + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}