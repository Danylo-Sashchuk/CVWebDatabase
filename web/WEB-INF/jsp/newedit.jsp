<%--
  User: Danylo Sashchuk
  Date: 12/11/23
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="static" uri="https://www.webcv.com/static" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="resume" scope="request" type="com.webcv.model.Resume"/>
<%@ page import="com.webcv.model.ContactType" %>
<%@ page import="com.webcv.model.SectionType" %>
<%@ page import="com.webcv.model.TextSection" %>
<%@ page import="com.webcv.model.CompanySection" %>
<%@ page import="com.webcv.model.ListSection" %>

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
        <div class="fixed-buttons-container">
            <button type="button" class="fixed-button expand-all-button" onclick="clickExpandAll()">Expand all</button>
            <button type="button" class="fixed-button collapse-all-button" onclick="clickCollapseAll()">Collapse all
            </button>
        </div>
        <div class="go-top-container">
            <button type="button" class="go-top" onclick="scrollToTop()">Go top</button>
        </div>
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

        <c:forEach var="sectionType" items="${SectionType.values()}">
            <c:set var="section" value="${sections.get(sectionType)}"/>
            <c:choose>
                <c:when test="${sectionType == SectionType.POSITION || sectionType == SectionType.PERSONAL}">
                    <c:if test="${section == null}">
                        <c:set var="section" value="<%=TextSection.getEmpty()%>"/>
                    </c:if>
                    <myTags:textSectionEdit sectionType="${sectionType.title}"
                                            section="${section}"/>
                </c:when>
                <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                    <c:if test="${section == null}">
                        <c:set var="section" value="<%=CompanySection.getEmpty()%>"/>
                    </c:if>
                    <myTags:companySectionEdit sectionType="${sectionType.title}"
                                               section="${section}"/>
                </c:when>
                <c:when test="${sectionType == SectionType.QUALIFICATIONS || sectionType == SectionType.ACHIEVEMENTS}">
                    <c:if test="${section == null}">
                        <c:set var="section" value="<%=ListSection.getEmpty()%>"/>
                    </c:if>
                    <myTags:listSectionEdit sectionType="${sectionType.title}" section="${section}"/>
                </c:when>
            </c:choose>
        </c:forEach>

        <div class="submit-container">
            <input type="submit" class="submit-button">
        </div>
    </form>
</div>
<div class="bottom">
    <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</div>

<script src="${pageContext.request.contextPath}/resources/edit.js"></script>
<script src="${pageContext.request.contextPath}/resources/interpage.js"></script>
</body>
</html>
