package com.basejava.web;

import com.basejava.model.Resume;
import com.basejava.storage.Storage;
import com.basejava.util.Config;
import com.basejava.util.ResumeTestData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Storage storage = Config.get().getStorage();
        ResumeTestData resumeTestData = ResumeTestData.getInstance();
        Resume resume1 = resumeTestData.createResume("John Doe");
        Resume resume2 = resumeTestData.createResume("Jane Smith");
        Resume resume3 = resumeTestData.createResume("David Johnson");
        Resume resume4 = resumeTestData.createResume("Emily Wilson");
        Resume resume5 = resumeTestData.createResume("Michael Brown");
        Resume resume6 = resumeTestData.createResume("Sarah Davis");

        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);
        storage.save(resume5);
        storage.save(resume6);

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Object List</title></head>");
        out.println("<body>");
        out.println("<table>");
        out.println("<tr><th>UUID</th><th>Name</th></tr>");

        for (Resume resume : storage.getAllSorted()) {
            out.println("<tr>");
            out.println("<td>" + resume.getUuid() + "</td>");
            out.println("<td>" + resume.getFullName() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
