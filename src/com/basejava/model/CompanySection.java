package com.basejava.model;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Company> companies;

    public CompanySection(Company... companies) {
        this(List.of(companies));
    }

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "organizations must not be null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }

    @Override
    public String toString() {
        return "CompanySection{" + "companies=" + companies + '}';
    }
}
