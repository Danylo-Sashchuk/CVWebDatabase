package com.webcv.model;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private List<String> texts;

    @Serial
    private static final long serialVersionUID = 1L;

    public ListSection() {
    }

    public static ListSection getEmpty() {
        return new ListSection("");
    }

    public ListSection(String... texts) {
        this(List.of(texts));
    }

    public ListSection(List<String> texts) {
        this.texts = texts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return Objects.equals(texts, that.texts);
    }

    @Override
    public String toString() {
        return "ListSection{" + "texts=" + texts + '}';
    }

    @Override
    public int hashCode() {
        return texts != null ? texts.hashCode() : 0;
    }

    public List<String> getTexts() {
        return texts;
    }
}
