<%--
  User: Danylo Sashchuk
  Date: 12/16/23
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.webcv.model.ContactType" %>
<%@ page import="com.webcv.model.SectionType" %>
<%@ page import="com.webcv.model.ListSection" %>
<%@ page import="com.webcv.model.TextSection" %>
<%@ page import="com.webcv.model.CompanySection" %>

<%@ page import="com.webcv.util.HtmlUtil" %>
<%@ page import="com.webcv.util.DateUtil" %>

<jsp:useBean id="resume" scope="request" type="com.webcv.model.Resume"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/view.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/intersectional.css">
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<nav>
    <jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
</nav>
<div class="form-wrapper">
    <div class="fixed-buttons-container">
        <button type="button" class="fixed-button expand-all-button" onclick="clickExpandAll()">Expand all</button>
        <button type="button" class="fixed-button collapse-all-button" onclick="clickCollapseAll()">Collapse all
        </button>
    </div>
    <div class="go-top-container">
        <button type="button" class="go-top" onclick="scrollToTop()">Go top</button>
    </div>

    <div class="full-name">${resume.fullName}</div>

    <%--    Contacts section   --%>
    <c:set var="contacts" value="${resume.contacts}"/>
    <jsp:useBean id="contacts" type="java.util.Map<com.webcv.model.ContactType, java.lang.String>"/>
    <c:if test="${contacts.size() != 0}">
        <div class="panel">
            <div class="contacts">
                <button type="button" class="collapse-button">Contacts</button>
                <div class="collapsible-content">
                    <c:forEach var="contactType" items="${ContactType.values()}">
                        <c:if test="${contacts.get(contactType) != null}">
                            <div class="contact">
                                <div class="contact-type">${contactType.title}:</div>
                                <div class="contact-value">${HtmlUtil.formatContact(contactType, contacts.get(contactType))}</div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>
    <%--    /Contacts section   --%>

    <%--    Main sections   --%>
    <c:set var="sections" value="${resume.sections}"/>
    <c:forEach var="sectionType" items="${SectionType.values()}">
        <c:choose>
            <c:when test="${sectionType == SectionType.POSITION || sectionType == SectionType.PERSONAL}">
                <myTags:textSectionView sectionType="${sectionType.title}" section="${sections.get(sectionType)}"/>
            </c:when>
            <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                <myTags:companySectionView sectionType="${sectionType.title}" section="${sections.get(sectionType)}"/>
            </c:when>
            <c:when test="${sectionType == SectionType.EDUCATION || sectionType == SectionType.QUALIFICATIONS}">
                <myTags:listSectionView sectionType="${sectionType.title}" section="${sections.get(sectionType)}"/>
            </c:when>
        </c:choose>
    </c:forEach>

</div>
<div class="bottom">
    <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</div>

<script src="${pageContext.request.contextPath}/resources/view.js"></script>
<script src="${pageContext.request.contextPath}/resources/interpage.js"></script>
</body>
</html>
