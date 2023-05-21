package com.basejava.util;

import com.basejava.model.AbstractSection;
import com.basejava.model.TextSection;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
//TODO Implement Section Adapter
public class SectionAdapter extends TypeAdapter<AbstractSection> {
    @Override
    public void write(JsonWriter out, AbstractSection value) throws IOException {
        String s = value.toString();
        out.value(s);
    }

    @Override
    public AbstractSection read(JsonReader in) throws IOException {
        in.beginObject();
        return new TextSection();
    }
}
