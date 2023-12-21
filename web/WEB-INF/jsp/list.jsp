<%@ page import="com.webcv.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/intersectional.css">
    <title>All resumes</title>
</head>
<body>
<nav>
    <jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
</nav>
<div class="main-div">
    <div class="add-resume-button">
        <a href="resume?action=add"><img src="img/add.png"></a>
    </div>
    <br>
    <div class="div-list-table">
        <table class="list-table" border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="com.webcv.model.Resume"/>
                <tr>
                    <td class="name-column"><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td><%=ContactType.EMAIL.toHtml(resume.getContacts().get(ContactType.EMAIL))%>
                    </td>
                    <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                    <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div class="bottom">
    <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</div>
</body>
</html>