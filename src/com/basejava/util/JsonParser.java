package com.basejava.util;

import com.basejava.model.AbstractSection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
                    .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter<>()) //TODO change to the TypeAdapter
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapterJson()).create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}
