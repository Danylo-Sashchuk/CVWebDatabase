package com.basejava.util;

import com.basejava.model.*;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO Simplify nextName
//TODO transform into TypeAdapterFactory
public class JsonSectionAdapter extends TypeAdapter<AbstractSection> {
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
        while (in.hasNext()) {
            in.nextName();
            String sectionType = in.nextString();
            switch (sectionType) {
                case "TextSection" -> {
                    TextSection textSection = new TextSection();
                    in.nextName();
                    textSection.setText(in.nextString());
                    in.endObject();
                    return textSection;
                }
                case "ListSection" -> {
                    in.nextName();
                    in.beginArray();
                    List<String> texts = new ArrayList<>();
                    while (in.hasNext()) {
                        texts.add(in.nextString());
                    }
                    in.endArray();
                    in.endObject();
                    return new ListSection(texts);
                }
                case "CompanySection" -> {
                    List<Company> companies = new ArrayList<>();
                    in.nextName();
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        in.nextName();
                        String name = in.nextString();
                        in.nextName();
                        String url = in.nextString();
                        List<Company.Period> periods = new ArrayList<>();
                        in.nextName();
                        in.beginArray();
                        while (in.hasNext()) {
                            in.beginObject();
                            in.nextName();
                            String title = in.nextString();
                            in.nextName();
                            String description = in.nextString();
                            in.nextName();
                            LocalDate startDate = LocalDate.parse(in.nextString());
                            in.nextName();
                            LocalDate endDate = LocalDate.parse(in.nextString());
                            periods.add(new Company.Period(title, description, startDate, endDate));
                            in.endObject();
                        }
                        in.endArray();
                        companies.add(new Company(name, url, periods));
                        in.endObject();
                    }
                    in.endArray();
                    in.endObject();
                    return new CompanySection(companies);
                }
            }
        }
        return null;
    }
}
