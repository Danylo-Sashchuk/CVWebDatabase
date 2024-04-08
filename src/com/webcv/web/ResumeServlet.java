package com.webcv.web;

import com.webcv.model.*;
import com.webcv.storage.Storage;
import com.webcv.util.Config;
import com.webcv.util.DateUtil;
import com.webcv.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage storage = Config.get().getStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume = new Resume();
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> {
                resume = storage.get(uuid);
                request.setAttribute("resume", resume);
                request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
            }
            case "edit" -> {
                resume = storage.get(uuid);
                request.setAttribute("resume", resume);
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
            }
            case "add" -> {
                request.setAttribute("resume", resume);
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String uuid = request.getParameter("uuid");
        String fullname = request.getParameter("fullname");
        boolean isCreate = uuid == null || uuid.length() == 0;
        Resume resume;
        if (isCreate) {
            resume = new Resume(fullname);
        } else {
            resume = storage.get(uuid);
        }
        resume.setFullName(fullname);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.removeContact(type);
            } else {
                resume.setContact(type, value);
            }
        }

        for (SectionType type : SectionType.values()) {
            switch (type) {
                case POSITION:
                case PERSONAL:
                    String text = request.getParameter(type.getTitle());
                    if (HtmlUtil.isEmpty(text)) {
                        resume.removeSection(type);
                    } else {
                        resume.setSection(type, new TextSection(text));
                    }
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    String value = request.getParameter(type.getTitle());
                    if (HtmlUtil.isEmpty(value)) {
                        resume.removeSection(type);
                    } else {
                        resume.setSection(type, new ListSection(value.split("\\n")));
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Company> companies = new ArrayList<>();
                    int companyCounter = 0;
                    while (request.getParameter(type.getTitle() + "[" + companyCounter + "].name") != null) {
                        String name = request.getParameter(type.getTitle() + "[" + companyCounter + "].name");
                        if (HtmlUtil.isEmpty(name)) {
                            companyCounter++;
                            continue;
                        }
                        //TODO: check if url with https://
                        String url = request.getParameter(type.getTitle() + "[" +
                                                          companyCounter + "].url");
                        List<Company.Period> periods = new ArrayList<>();
                        int periodCounter = 0;
                        while (request.getParameter(type.getTitle() + "[" + companyCounter + "].period[" + periodCounter + "].title") != null) {
                            String title = request.getParameter(String.format("%s[%d].period[%d].title",
                                    type.getTitle(), companyCounter, periodCounter));
                            if (HtmlUtil.isEmpty(title)) {
                                periodCounter++;
                                continue;
                            }
                            String description = request.getParameter(String.format("%s[%d].period[%d].description",
                                    type.getTitle(), companyCounter, periodCounter));
                            String startDate = request.getParameter(String.format("%s[%d].period[%d].start",
                                    type.getTitle(), companyCounter, periodCounter));
                            String endDate = request.getParameter(String.format("%s[%d].period[%d].end",
                                    type.getTitle(), companyCounter, periodCounter));

                            periods.add(new Company.Period(title, description, DateUtil.parse(startDate),
                                    DateUtil.parse(endDate)));
                            periodCounter++;
                        }
                        companies.add(new Company(name, url, periods));
                        companyCounter++;
                    }
                    resume.setSection(type, new CompanySection(companies));
                    break;
            }
        }

        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }
}
