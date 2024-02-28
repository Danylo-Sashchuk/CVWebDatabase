<%--
  User: Danylo
  Date: 7/20/23
--%>
<%@ page import="com.webcv.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.webcv.model.SectionType" %>
<%@ page import="com.webcv.util.DateUtil" %>
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
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="com.webcv.model.SectionType"/>
            <c:set var="section" value="${resume.sections.get(sectionType)}"/>
            <c:if test="${section == null}">
                <c:choose>
                    <c:when test="${sectionType == SectionType.POSITION || sectionType == SectionType.PERSONAL}">
                        <c:set var="section" value="<%=TextSection.getEmpty()%>"/>
                    </c:when>
                    <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                        <c:set var="section" value="<%=ListSection.getEmpty()%>"/>
                    </c:when>
                    <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                        <c:set var="section" value="<%=CompanySection.getEmpty()%>"/>
                    </c:when>
                </c:choose>
            </c:if>
            <jsp:useBean id="section" type="com.webcv.model.AbstractSection"/>
            <dl>
                <dt>${sectionType.title}</dt>
                <c:choose>
                    <c:when test="${sectionType == SectionType.POSITION || sectionType == SectionType.PERSONAL}">
                        <dd><input type="text" name="${sectionType}" class="text-input"
                                   value="<%=((TextSection) section).getText()%>" size="50"></dd>
                    </c:when>
                    <c:when test="${sectionType == SectionType.ACHIEVEMENTS || sectionType == SectionType.QUALIFICATIONS}">
                        <%
                            String cssClassName = sectionType == SectionType.ACHIEVEMENTS ? "achievements-input" : "qualifications-input¬";
                            int areaSize = sectionType == SectionType.ACHIEVEMENTS ? 90 : 40;
                        %>
                        <dd>
                            <textarea name="${sectionType}" class="<%=cssClassName%>" rows=5
                                      cols=<%=areaSize%>><%=String.join("\n", ((ListSection) section).getTexts())%></textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                        <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>"
                                   varStatus="counter">
                            <jsp:useBean id="company" type="com.webcv.model.Company"/>
                            <div class="company">
                                <dl>
                                    <dt>Organization name:</dt>
                                    <dd><input type="text" name="${sectionType}" size=40
                                               value="${company.name}"></dd>
                                </dl>
                                <dl>
                                    <dt>Organization website:</dt>
                                    <dd><input type="text" name="${sectionType}.url" size=40
                                               value="${company.website.url}"></dd>
                                </dl>
                                <br>
                                <div style="margin-left: 30px">
                                    <c:forEach var="period" items="${company.periods}">
                                        <jsp:useBean id="period" type="com.webcv.model.Company.Period"/>
                                        <dl>
                                            <dt>Position:</dt>
                                            <dd><input type="text"
                                                       name="${sectionType}[${counter.index}].title"
                                                       size=40 value="${period.title}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Start date:</dt>
                                            <dd><input type="text"
                                                       name="${sectionType}[${counter.index}].startDate"
                                                       value="<%=DateUtil.format(period.getStartDate())%>"
                                                       placeholder="MM/yyyy"></dd>
                                        </dl>
                                        <dl>
                                            <dt>End date:</dt>
                                            <dd><input type="text"
                                                       name="${sectionType}[${counter.index}].endDate"
                                                       value="<%=DateUtil.format(period.getEndDate())%>"
                                                       placeholder="MM/yyyy"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Description:</dt>
                                            <dd><textarea
                                                    name="${sectionType}[${counter.index}].description"
                                                    rows=5 cols=40>${period.description}</textarea></dd>
                                        </dl>
                                        <br>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
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
