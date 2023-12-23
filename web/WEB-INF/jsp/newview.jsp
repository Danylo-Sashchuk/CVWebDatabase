<%--
  User: Danylo Sashchuk
  Date: 12/16/23
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <%--    Postiton section   --%>
    <c:set var="position" value="${sections.get(SectionType.POSITION)}"/>
    <c:set var="position" value="${position}" target="TextSection"/>
    <c:if test="${position != null}">
        <jsp:useBean id="position" type="com.webcv.model.TextSection"/>
        <div class="panel">
            <div class="position">
                <button type="button" class="collapse-button">Position</button>
                <div class="collapsible-content">
                    <div class="text-container">
                            ${position.text}
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <%--    /Postiton section   --%>

    <%--    Experience section   --%>
    <c:set var="experience" value="${sections.get(SectionType.EXPERIENCE)}"/>
    <c:set var="experience" value="${experience}" target="CompanySection"/>
    <c:if test="${experience != null}">
        <jsp:useBean id="experience" type="com.webcv.model.CompanySection"/>
        <div class="panel">
            <div class="experience">
                <button type="button" class="collapse-button">Experience</button>
                <div class="collapsible-content">
                    <div class="company-container">
                        <c:forEach var="company" items="${experience.companies}">
                            <div class="company">
                                <div class="company-name">${company.name}</div>
                                <c:forEach var="period" items="${company.periods}">
                                    <div class="period">
                                        <div class="period-title">${period.title}</div>
                                        <div class="period-time">${DateUtil.format(period.startDate)}
                                            - ${DateUtil.format(period.endDate)}</div>
                                    </div>
                                    <div class="period-description">${period.description}</div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <%--    /Experience section   --%>

    <%--    Achievements section   --%>
    <c:set var="achievements" value="${sections.get(SectionType.ACHIEVEMENTS)}"/>
    <c:set var="achievements" value="${achievements}" target="ListSection"/>
    <c:if test="${achievements != null}">
        <jsp:useBean id="achievements" type="com.webcv.model.ListSection"/>
        <div class="panel">
            <div class="achievements">
                <button type="button" class="collapse-button">Achievements</button>
                <div class="collapsible-content">
                    <ol class="list-container">
                        <c:forEach var="achievement" items="${achievements.texts}">
                        <li>${achievement}</li>
                        </c:forEach>
                </div>
            </div>
        </div>
    </c:if>
    <%--    /Achievements section   --%>

    <%--    Qualifications section   --%>
    <c:set var="qualifications" value="${sections.get(SectionType.QUALIFICATIONS)}"/>
    <c:set var="qualifications" value="${qualifications}" target="ListSection"/>
    <c:if test="${qualifications != null}">
        <jsp:useBean id="qualifications" type="com.webcv.model.ListSection"/>
        <div class="panel">
            <div class="qualifications">
                <button type="button" class="collapse-button">Qualifications</button>
                <div class="collapsible-content">
                    <ol class="list-container">
                        <c:forEach var="qualification" items="${qualifications.texts}">
                            <li>${qualification}</li>
                        </c:forEach>
                    </ol>
                </div>
            </div>
        </div>
    </c:if>
    <%--    /Qualifications section   --%>

    <%--    Education section   --%>
    <c:set var="education" value="${sections.get(SectionType.EDUCATION)}"/>
    <c:set var="education" value="${education}" target="CompanySection"/>
    <c:if test="${education != null}">
        <jsp:useBean id="education" type="com.webcv.model.CompanySection"/>
        <div class="panel">
            <div class="education">
                <button type="button" class="collapse-button">Education</button>
                <div class="collapsible-content">
                    <div class="company-container">
                        <c:forEach var="company" items="${education.companies}">
                            <div class="company">
                                <div class="company-name">${company.name}</div>
                                <c:forEach var="period" items="${company.periods}">
                                    <div class="period">
                                        <div class="period-title">${period.title}</div>
                                        <div class="period-time">${period.startDate} - ${period.endDate}</div>
                                        <div class="period-description">${period.description}</div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <%--    /Education section   --%>

    <%--    Personal section   --%>
    <c:set var="personal" value="${sections.get(SectionType.PERSONAL)}"/>
    <c:set var="personal" value="${personal}" target="TextSection"/>
    <c:if test="${personal != null}">
        <jsp:useBean id="personal" type="com.webcv.model.TextSection"/>
        <div class="panel">
            <div class="personal">
                <button type="button" class="collapse-button">Personal</button>
                <div class="collapsible-content">
                    <div class="text-container">
                            ${personal.text}
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <%--    /Personal section   --%>
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
