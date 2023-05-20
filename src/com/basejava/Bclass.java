package com.basejava;

import java.io.Serializable;
import java.util.Objects;

public class Bclass implements Serializable {
    public String text;
    public Bclass() {
        text = "I am one";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bclass bclass = (Bclass) o;
        return Objects.equals(text, bclass.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
