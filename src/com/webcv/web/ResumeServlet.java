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
                request.getRequestDispatcher("/WEB-INF/jsp/newedit.jsp").forward(request, response);
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
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && (values == null || values.length < 2)) {
                resume.removeSection(type);
            } else {
                switch (type) {
                    case POSITION:
                    case PERSONAL:
                        resume.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        resume.setSection(type, new ListSection(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Company> companies = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + ".url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Company.Period> periods = new ArrayList<>();
                                String pfx = type.name() + "[" + i + "].";
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        periods.add(new Company.Period(titles[j], descriptions[j],
                                                DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j])));
                                    }
                                }
                                companies.add(new Company(name, urls[i], periods));
                            }
                        }
                        resume.setSection(type, new CompanySection(companies));
                        break;
                }
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
