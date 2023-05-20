package com.basejava;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class Aclass implements Serializable {
    public Bclass bclass;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aclass aclass = (Aclass) o;
        return Objects.equals(bclass, aclass.bclass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bclass);
    }
}
