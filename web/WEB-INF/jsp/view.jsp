<%--
  User: Danylo
  Date: 7/16/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.webcv.model.ListSection" %>
<%@ page import="com.webcv.model.CompanySection" %>
<%@ page import="com.webcv.model.TextSection" %>
<%@ page import="com.webcv.model.SectionType" %>
<%@ page import="com.webcv.util.HtmlUtil" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <jsp:useBean id="resume" type="com.webcv.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.webcv.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <table>
        <c:forEach var="sectionType" items="${SectionType.values()}">
            <jsp:useBean id="sectionType" type="com.webcv.model.SectionType"/>
            <c:set var="section" value="${resume.sections.get(sectionType)}"/>
            <jsp:useBean id="section" type="com.webcv.model.AbstractSection"/>
            <tr>
                <td colspan="2"><h2><a name="sectionType.name">${sectionType.title}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${sectionType=='POSITION'}">
                    <tr>
                        <td colspan="2">
                            <h3><%=((TextSection) section).getText()%>
                            </h3>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionType=='PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <%=((TextSection) section).getText()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionType=='QUALIFICATIONS' || sectionType=='ACHIEVEMENTS'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getTexts()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                    <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>">
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty company.website.url}">
                                        <h3>${company.website.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${company.website.url}">${company.website.name}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="period" items="${company.periods}">
                            <jsp:useBean id="period" type="com.webcv.model.Company.Period"/>
                            <tr>
                                <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(period)%>
                                </td>
                                <td><b>${period.title}</b><br>${period.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>