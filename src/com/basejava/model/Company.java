package com.basejava.model;

import java.time.LocalDate;
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

    public static class Period {
        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;

        public Period(String title, String description, LocalDate startDate, LocalDate endDate) {
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "Period{" + "title='" + title + '\'' + ", description='" + description + '\'' + ", startDate=" + startDate + ", endDate=" + endDate + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!Objects.equals(title, period.title)) return false;
            if (!Objects.equals(description, period.description)) return false;
            if (!Objects.equals(startDate, period.startDate)) return false;
            return Objects.equals(endDate, period.endDate);
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            return result;
        }
    }
}
