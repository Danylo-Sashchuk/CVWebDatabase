package com.basejava.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private final List<Period> periods;
    private String name;
    private Link website;

    public Company(String name, String url, List<Period> periods) {
        this.website = new Link(name, url);
        this.name = name;
        this.periods = periods;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link getWebsite() {
        return website;
    }

    public void setWebsite(Link website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Company{" + "periods=" + periods + ", name='" + name + '\'' + ", website=" + website + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!periods.equals(company.periods)) return false;
        if (!name.equals(company.name)) return false;
        return Objects.equals(website, company.website);
    }

    @Override
    public int hashCode() {
        int result = periods.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }
}
