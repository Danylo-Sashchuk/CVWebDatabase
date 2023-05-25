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
public class JsonSectionAdapter extends TypeAdapter<AbstractSection> {
    @Override
    public void write(JsonWriter out, AbstractSection section) throws IOException {
        String sectionType = section.getClass().getSimpleName();
        out.beginObject();
        out.name("SectionType").value(sectionType);
        switch (sectionType) {
            case "TextSection" -> writeSection(out, (TextSection) section);
            case "ListSection" -> writeSection(out, (ListSection) section);
            case "CompanySection" -> writeSection(out, (CompanySection) section);
        }
        out.endObject();
    }

    private void writeSection(JsonWriter out, CompanySection section) throws IOException {
        out.name("companies").beginArray();
        for (Company company : section.getCompanies()) {
            writeCompany(out, company);
        }
        out.endArray();
    }

    private void writeCompany(JsonWriter out, Company company) throws IOException {
        out.beginObject();
        out.name("name").value(company.getName());
        out.name("website").value(company.getWebsite().getUrl());

        out.name("periods").beginArray();
        for (Company.Period period : company.getPeriods()) {
            writePeriod(out, period);
        }
        out.endArray();

        out.endObject();
    }

    private void writePeriod(JsonWriter out, Company.Period period) throws IOException {
        out.beginObject();
        out.name("title").value(period.getTitle());
        out.name("description").value(period.getDescription());
        out.name("start").value(period.getStartDate().toString());
        out.name("end").value(period.getEndDate().toString());
        out.endObject();
    }

    private void writeSection(JsonWriter out, TextSection section) throws IOException {
        out.name("text").value(section.getText());
    }

    private void writeSection(JsonWriter out, ListSection section) throws IOException {
        out.name("texts").beginArray();
        for (String text : section.getTexts()) {
            out.value(text);
        }
        out.endArray();
    }

    @Override
    public AbstractSection read(JsonReader in) throws IOException {
        in.beginObject();
        while (in.hasNext()) {
            in.nextName();
            String sectionType = in.nextString();
            switch (sectionType) {
                case "TextSection" -> {
                    return readTextSection(in);
                }
                case "ListSection" -> {
                    return readListSection(in);
                }
                case "CompanySection" -> {
                    return readCompanySection(in);
                }
            }
        }
        return null;
    }

    private CompanySection readCompanySection(JsonReader in) throws IOException {
        List<Company> companies = new ArrayList<>();
        in.nextName();
        in.beginArray();
        while (in.hasNext()) {
            readCompany(in, companies);
        }
        in.endArray();
        in.endObject();
        return new CompanySection(companies);
    }

    private void readCompany(JsonReader in, List<Company> companies) throws IOException {
        in.beginObject();
        in.nextName();
        String name = in.nextString();
        in.nextName();
        String url = in.nextString();
        List<Company.Period> periods = new ArrayList<>();
        in.nextName();
        in.beginArray();
        while (in.hasNext()) {
            readPeriod(in, periods);
        }
        in.endArray();
        companies.add(new Company(name, url, periods));
        in.endObject();
    }

    private void readPeriod(JsonReader in, List<Company.Period> periods) throws IOException {
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

    private ListSection readListSection(JsonReader in) throws IOException {
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

    private TextSection readTextSection(JsonReader in) throws IOException {
        TextSection textSection = new TextSection();
        in.nextName();
        textSection.setText(in.nextString());
        in.endObject();
        return textSection;
    }
}
