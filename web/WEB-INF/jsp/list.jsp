<%@ page import="com.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>All resumes</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.basejava.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContacts().get(ContactType.EMAIL))%></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>