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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/resume-view.css">
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

    <myTags:textSection sectionType="Position" section="${sections.get(SectionType.POSITION)}"/>

    <myTags:companySection sectionType="Experience" section="${sections.get(SectionType.EXPERIENCE)}"/>

    <myTags:listSection sectionType="Achievements" section="${sections.get(SectionType.ACHIEVEMENTS)}"/>

    <myTags:listSection sectionType="Qualifications" section="${sections.get(SectionType.QUALIFICATIONS)}"/>

    <myTags:companySection sectionType="Education" section="${sections.get(SectionType.EDUCATION)}"/>

    <myTags:textSection sectionType="Personal" section="${sections.get(SectionType.PERSONAL)}"/>

</div>
<div class="bottom">
    <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</div>
<script>
    function scrollToTop() {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    }

    function clickExpandAll() {
        const buttons = document.querySelectorAll('.collapse-button:not(.activated)');
        buttons.forEach(button => {
            button.click();
        });
    }

    function clickCollapseAll() {
        const buttons = document.querySelectorAll('.collapse-button.activated');
        buttons.forEach(button => {
            button.click();
        });
    }

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
                content.addEventListener('transitionend', adjustMainContentPadding);
            });
            coll[i].click();
            adjustMainContentPadding();
        }
    }

    function assignTransition() {
        const collapsibleContents = document.getElementsByClassName("collapsible-content");
        for (let i = 0; i < collapsibleContents.length; i++) {
            collapsibleContents[i].style.maxHeight = collapsibleContents[i].scrollHeight + "px";
            collapsibleContents[i].style.transition = "max-height 0.2s ease-in-out";
        }
    }

    function adjustMainContentPadding() {
        const mainContent = document.querySelector('.form-wrapper');

        const collapsibleContents = document.getElementsByClassName('collapsible-content');

        let totalExpandedHeight = 0;
        for (let content of collapsibleContents) {
            if (content.style.maxHeight) {
                totalExpandedHeight += content.scrollHeight;
            }
        }
        mainContent.style.paddingBottom = totalExpandedHeight + 'px';
    }

    window.addEventListener('scroll', function () {
        var goTopButton = document.querySelector('.go-top');
        // Check if the page is scrolled down by 100px
        if (window.pageYOffset > 100) {
            goTopButton.style.display = 'block'; // Show the button
        } else {
            goTopButton.style.display = 'none'; // Hide the button
        }
    });

    // On page load, hide the "Go top" button
    document.addEventListener('DOMContentLoaded', function () {
        var goTopButton = document.querySelector('.go-top');
        goTopButton.style.display = 'none';
    });

    document.addEventListener('DOMContentLoaded', function () {
        assignClicksAndExpand();
        assignTransition();
    });

</script>

</body>
</html>
