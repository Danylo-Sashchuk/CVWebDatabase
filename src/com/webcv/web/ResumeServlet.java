package com.webcv.web;

import com.webcv.model.Resume;
import com.webcv.model.SectionType;
import com.webcv.model.TextSection;
import com.webcv.storage.Storage;
import com.webcv.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String uuid = request.getParameter("uuid");
        String name = request.getParameter("fullname");
        String personal = request.getParameter("personal");
        String position = request.getParameter("position");
        Resume resume = new Resume(uuid, name);
        resume.setContact(SectionType.PERSONAL, new TextSection(personal));
        resume.setContact(SectionType.POSITION, new TextSection(position));
        storage.update(resume);
        response.sendRedirect("resume");
    }
}
