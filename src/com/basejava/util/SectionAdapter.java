package com.basejava.util;

import com.basejava.model.*;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

//TODO Implement Section Adapter
public class SectionAdapter extends TypeAdapter<AbstractSection> {
    @Override
    public void write(JsonWriter out, AbstractSection section) throws IOException {
        Class<? extends AbstractSection> objectClass = section.getClass();
        out.beginObject();
        out.name("SectionType").value(objectClass.getSimpleName());
        switch (objectClass.getSimpleName()) {
            case "TextSection" -> {
                TextSection textSection = (TextSection) section;
                out.name("text").value(textSection.getText());
            }
            case "ListSection" -> {
                ListSection listSection = (ListSection) section;
                out.name("texts").beginArray();
                for (String text : listSection.getTexts()) {
                    out.value(text);
                }
                out.endArray();
            }
            case "CompanySection" -> {
                CompanySection companySection = (CompanySection) section;
                List<Company> companies = companySection.getCompanies();
                out.name("companies").beginArray();
                for (Company company : companies) {
                    out.beginObject();
                    out.name("name").value(company.getName());
                    out.name("website").value(company.getWebsite().getUrl());
                    List<Company.Period> periods = company.getPeriods();
                    out.name("periods").beginArray();
                    for (Company.Period period : periods) {
                        out.beginObject();
                        out.name("title").value(period.getTitle());
                        out.name("description").value(period.getDescription());
                        out.name("start").value(period.getStartDate().toString());
                        out.name("end").value(period.getEndDate().toString());
                        out.endObject();
                    }
                    out.endArray();
                    out.endObject();
                }
                out.endArray();
            }
        }
        out.endObject();
    }

    @Override
    public AbstractSection read(JsonReader in) throws IOException {
        in.beginObject();
        return new TextSection();
    }
}
