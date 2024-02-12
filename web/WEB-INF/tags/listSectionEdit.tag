<%--
  User: Danylo Sashchuk
  Date: 2/11/24
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="sectionType" required="true" %>
<%@ attribute name="section" required="true" type="com.webcv.model.ListSection" %>

<div class="panel">
    <div class="${sectionType}">
        <button type="button" class="collapse-button">${sectionType}</button>
        <div class="collapsible-content">
                <textarea name="${sectionType}"><c:forEach var="item" items="${section.texts}"
                                                           varStatus="status">    ${item}${status.last ? '' : '&#xa;'}</c:forEach></textarea>
        </div>
    </div>
</div>