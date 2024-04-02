<%--
  User: Danylo Sashchuk
  Date: 12/23/23
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="static" uri="https://www.webcv.com/static" %>
<%@ attribute name="sectionType" required="true" %>
<%@ attribute name="section" required="true" type="com.webcv.model.CompanySection" %>

<c:if test="${not empty section}">
    <div class="panel">
        <div class="${sectionType}">
            <button type="button" class="collapse-button">${sectionType}</button>
            <div class="collapsible-content">
                <div class="company-container">
                    <c:forEach var="company" items="${section.companies}">
                        <div class="company">
                            <div class="company-name">${company.name}</div>
                            <div class="company-website"><a href="${company.website}">${company.name}</a></div>
                            <c:forEach var="period" items="${company.periods}">
                                <div class="period">
                                    <div class="period-title">${period.title}</div>
                                    <div class="period-time">${static:formatDate(period.startDate)}
                                        - ${static:formatDate(period.endDate)}</div>
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
