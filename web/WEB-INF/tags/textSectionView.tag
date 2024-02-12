<%--
  User: Danylo Sashchuk
  Date: 12/23/23
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="sectionType" required="true" %>
<%@ attribute name="section" required="true" type="com.webcv.model.TextSection" %>

<c:if test="${not empty section}">
    <div class="panel">
        <div class="${sectionType}">
            <button type="button" class="collapse-button">${sectionType}</button>
            <div class="collapsible-content">
                <div class="text-container">
                        ${section.text}
                </div>
            </div>
        </div>
    </div>
</c:if>