package com.basejava.util;

import com.basejava.model.AbstractSection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDate.class,
            new LocalDateAdapterJson()).registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter()).setPrettyPrinting().create();
    //TODO change to the TypeAdapter

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}
