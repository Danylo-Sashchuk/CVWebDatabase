package com.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> texts;

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
