<%--
  User: Danylo Sashchuk
  Date: 12/11/23
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <input type="text" id="${contactType.name()}"
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
                            <input type="text" id="position" value="${position.text}">
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
