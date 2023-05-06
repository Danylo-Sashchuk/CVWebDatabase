package com.basejava.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private final List<Period> periods;
    private String name;
    private String website;

    public Company(String name, String website, List<Period> periods) {
        this.name = name;
        this.website = website;
        this.periods = periods;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Company{" + "periods=" + periods + ", name='" + name + '\'' + ", website='" + website + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!Objects.equals(periods, company.periods)) return false;
        if (!Objects.equals(name, company.name)) return false;
        return Objects.equals(website, company.website);
    }

    @Override
    public int hashCode() {
        int result = periods != null ? periods.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }
}
