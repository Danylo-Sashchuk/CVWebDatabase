<%--
  User: Danylo Sashchuk
  Date: 2/11/24
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="static" uri="https://www.webcv.com/static" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="sectionType" required="true" %>
<%@ attribute name="section" required="true" type="com.webcv.model.CompanySection" %>
<div class="panel">
    <div class="${sectionType}">
        <button type="button" class="collapse-button">${sectionType}</button>
        <div class="collapsible-content">
            <div class="companies-container">
                <c:forEach var="company" items="${section.companies}" varStatus="companyCounter">
                    <div class="company">
                        <div class="company-name">
                            <input type="text" name="${sectionType}[${companyCounter.index}].name"
                                   value="${company.name}"
                                   placeholder="${sectionType} name">
                            <input type="text" name="${sectionType}[${companyCounter.index}.url"
                                   value="${company.website.url}"
                                   placeholder="${sectionType} website">
                        </div>
                        <div class="periods-container">
                            <c:forEach var="period" items="${company.periods}" varStatus="periodCounter">
                                <div class="period">
                                    <div class="period-title">
                                        <input type="text"
                                               name="${sectionType}[${companyCounter.index}].period[${periodCounter.index}].title"
                                               value="${period.title}"
                                               placeholder="Period title">
                                    </div>
                                    <div class="period-time">
                                        <input type="month"
                                               name="${sectionType}[${companyCounter.index}].period[${periodCounter.index}].start"
                                               value="${static:formatDate(period.startDate)}"
                                               placeholder="Start date">
                                        to
                                        <input type="month"
                                               name="${sectionType}[${companyCounter.index}].period[${periodCounter.index}].end"
                                               value="${static:formatDate(period.endDate)}"
                                               placeholder="End date">
                                    </div>
                                    <div class="period-description">
                                        <input type="text"
                                               name="${sectionType}[${companyCounter.index}].period[${periodCounter.index}].description"
                                               value="${period.description}"
                                               placeholder="Description">
                                    </div>
                                    <div class="button-row">
                                        <c:if test="${fn:length(company.periods) > 1}">
                                            <div class="remove-period-button-container">
                                                <button type="button" class="remove-period-button">Remove
                                                </button>
                                            </div>
                                        </c:if>
                                        <c:if test="${periodCounter.last}">
                                            <div class="add-period-button-container">
                                                <button type="button" class="add-period-button">Add new
                                                </button>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="company-buttons-container">
                            <div class="remove-company-button-container">
                                <button type="button" class="remove-company-button">Remove company</button>
                            </div>
                            <c:if test="${companyCounter.last}">
                                <div class="add-company-button-container">
                                    <button type="button" class="add-company-button">Add new company
                                    </button>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
