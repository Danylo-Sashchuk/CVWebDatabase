package com.basejava.model;

import java.util.List;

public class CompanySection extends AbstractSection {

    private final List<Company> companies;

    public CompanySection(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }
}
