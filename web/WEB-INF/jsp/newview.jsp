<%--
  User: Danylo Sashchuk
  Date: 12/16/23
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.webcv.model.ContactType" %>
<%@ page import="com.webcv.model.SectionType" %>
<%@ page import="com.webcv.model.ListSection" %>
<%@ page import="com.webcv.util.HtmlUtil" %>

<jsp:useBean id="resume" scope="request" type="com.webcv.model.Resume"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/resume-view.css">
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="form-wrapper">
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

    <%--    Achievements section   --%>
    <c:set var="achievements" value="${sections.get(SectionType.ACHIEVEMENTS)}"/>
    <c:set var="achievements" value="${achievements}" target="ListSection"/>
    <c:if test="${achievements != null}">
        <jsp:useBean id="achievements" type="com.webcv.model.ListSection"/>
        <div class="panel">
            <div class="achievements">
                <button type="button" class="collapse-button">Achievements</button>
                <div class="collapsible-content">
                    <ol class="achievements-list">
                        <c:forEach var="achievement" items="${achievements.texts}">
                        <li>${achievement}</li>
                        </c:forEach>
                </div>
            </div>
        </div>
    </c:if>

</div>
<script>
    function assignClicksAndExpand() {
        const coll = document.getElementsByClassName("collapse-button");

        for (let i = 0; i < coll.length; i++) {
            coll[i].addEventListener("click", function () {
                this.classList.toggle("activated");
                const content = this.nextElementSibling;
                if (content.style.maxHeight) {
                    content.style.maxHeight = null;
                } else {
                    content.style.maxHeight = content.scrollHeight + "px";
                }
            });
            coll[i].click();
        }
    }

    function assignTransition() {
        const collapsibleContents = document.getElementsByClassName("collapsible-content");
        for (let i = 0; i < collapsibleContents.length; i++) {
            collapsibleContents[i].style.maxHeight = collapsibleContents[i].scrollHeight + "px";
            collapsibleContents[i].style.transition = "max-height 0.2s ease-in-out";
        }
    }

    assignClicksAndExpand();
    assignTransition();

</script>

</body>
</html>
