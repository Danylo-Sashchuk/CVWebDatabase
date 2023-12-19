<%--
  User: Danylo Sashchuk
  Date: 12/11/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="resume" scope="request" type="com.webcv.model.Resume"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/resume-view.css">
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="form-wrapper">
    <form method="post" action="resume">
        <div class="full-name">${resume.fullName}</div>
    </form>
</div>
</body>
</html>
