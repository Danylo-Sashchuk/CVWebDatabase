<%--
  User: Danylo
  Date: 7/20/23
--%>
<%@ page import="com.webcv.model.ContactType" %>
<%@ page import="com.webcv.model.SectionType" %>
<%@ page import="com.webcv.model.TextSection" %>
<%@ page import="com.webcv.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.webcv.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" name="fullname" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Contacts:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.contacts.get(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Sections:</h3>
        <c:forEach var="sectionEntry" items="<%=resume.getSections()%>">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.webcv.model.SectionType, com.webcv.model.AbstractSection>"/>
            <c:set var="sectionType" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="sectionType" type="com.webcv.model.SectionType"/>
            <jsp:useBean id="section" type="com.webcv.model.AbstractSection"/>
            <dl>
                <dt>${sectionType.title}</dt>
                <c:choose>
                    <c:when test="${sectionType == SectionType.POSITION || sectionType == SectionType.PERSONAL}">
                        <% TextSection textSection = (TextSection) section;%>
                        <dd><input type="text" name="${sectionType}" class="text-input"
                                   value="<%=textSection.getText()%>" size="50"></dd>
                    </c:when>
                    <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                        <% ListSection listSection = (ListSection) section;
                            String cssClassName = sectionType == SectionType.ACHIEVEMENTS ? "achievements-input" : "qualifications-input¬";
                            int areaSize = sectionType == SectionType.ACHIEVEMENTS ? 90 : 40;
                        %>
                        <dd>
                            <textarea name="${sectionType}" class="<%=cssClassName%>" rows=5
                                      cols=<%=areaSize%>><%=String.join("\n", listSection.getTexts())%></textarea>
                        </dd>
                    </c:when>
<%--                    <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">--%>
<%--                        <c:forEach var="item" items="${section.value}">--%>
<%--                            <c:forEach var="position" items="${item.positions}">--%>
<%--                                <dt>${position.startDate} - ${position.endDate}</dt>--%>
<%--                                <dd><input type="text" name="${section.key.title}" size=50 value="${position.title}">--%>
<%--                                </dd>--%>
<%--                                <dd><textarea name="${section.key.title}" rows=5--%>
<%--                                              cols=50>${position.description}</textarea></dd>--%>
<%--                            </c:forEach>--%>
<%--                        </c:forEach>--%>
<%--                    </c:when>--%>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
