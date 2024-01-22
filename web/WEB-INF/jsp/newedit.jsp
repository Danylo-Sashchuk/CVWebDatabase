<%--
  User: Danylo Sashchuk
  Date: 12/11/23
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="static" uri="https://www.webcv.com/static" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="resume" scope="request" type="com.webcv.model.Resume"/>
<%@ page import="com.webcv.model.ContactType" %>
<%@ page import="com.webcv.model.SectionType" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edit.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/intersectional.css">
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<nav>
    <jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
</nav>
<div class="form-wrapper">
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">

        <div class="full-name-input-container">
            <input type="text" name="fullname" value="${resume.fullName}">
        </div>


        <%--    Contacts section    --%>
        <c:set var="contacts" value="${resume.contacts}"/>
        <jsp:useBean id="contacts" type="java.util.Map<com.webcv.model.ContactType, java.lang.String>"/>
        <div class="panel">
            <div class="contacts">
                <button type="button" class="collapse-button">Contacts</button>
                <div class="collapsible-content">
                    <c:forEach var="contactType" items="${ContactType.values()}">
                        <div class="contact">
                            <div class="contact-type">${contactType.title}:</div>
                            <input type="text" name="${contactType.name()}"
                                   value="${contacts.get(contactType)}" class="contact-input"/>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <%--    /Contacts section    --%>

        <c:set var="sections" value="${resume.sections}"/>

        <%--    Postiton    --%>
        <c:if test="${sections.get(SectionType.POSITION) != null}">
            <c:set var="position" value="${sections.get(SectionType.POSITION)}"/>
            <div class="panel">
                <div class="position">
                    <button type="button" class="collapse-button">Position</button>
                    <div class="collapsible-content">
                        <div class="text-container">
                            <input type="text" name="position" value="${position.text}">
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <%--    /Postiton    --%>

        <%--    Experience    --%>
        <c:set var="experience" value="${sections.get(SectionType.EXPERIENCE)}"/>
        <c:if test="${experience != null}">
            <div class="panel">
                <div class="experience">
                    <button type="button" class="collapse-button">Experience</button>
                    <div class="collapsible-content">
                        <div class="company-container">
                            <c:forEach var="company" items="${experience.companies}" varStatus="companyCounter">
                                <div class="company" data-company-index="${companyCounter}">
                                    <div class="company-name">
                                        <input type="text" name="company-name${companyCounter.index}" value="${company.name}">
                                    </div>
                                    <div class="periods-container">
                                        <c:forEach var="period" items="${company.periods}" varStatus="periodCounter">
                                            <div class="period">
                                                <div class="period-title">
                                                    <input type="text"
                                                           name="period-title${companyCounter.index}${periodCounter.index}"
                                                           value="${period.title}">
                                                </div>
                                                <div class="period-time">
                                                    <input type="month"
                                                           name="period-time-start${companyCounter}${periodCounter}"
                                                           value="${static:formatDate(period.startDate)}">
                                                    to
                                                    <input type="month"
                                                           name="period-time-end${companyCounter}${periodCounter}"
                                                           value="${static:formatDate(period.endDate)}">
                                                </div>
                                                <div class="period-description">
                                                    <input type="text"
                                                           name="period-description${companyCounter}${periodCounter}"
                                                           value="${period.description}">
                                                </div>
                                            </div>
                                            <div class="button-row">
                                                <div class="remove-period-button-container">
                                                    <button type="button" class="remove-period-button">Remove period
                                                    </button>
                                                </div>
                                                <div class="add-period-button-container">
                                                    <button type="button" class="add-period-button">Add new period
                                                    </button>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="remove-company-button-container">
                                    <button type="button" class="remove-company-button">Remove company</button>
                                </div>
                                <div class="add-company-button-container">
                                    <button type="button" class="add-company-button">Add new company</button>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>


        <div class="submit-container">
            <input type="submit" class="submit-button">
        </div>
    </form>
</div>
<div class="bottom">
    <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</div>

<script src="${pageContext.request.contextPath}/resources/view.js"></script>
</body>
</html>
