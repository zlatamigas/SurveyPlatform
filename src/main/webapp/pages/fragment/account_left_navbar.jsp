<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<html lang="${sessionScope.localisation}">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div class="nav flex-column nav-pills" role="tablist" aria-orientation="vertical">
    <a id="navLinkUserSurveys" class="nav-link" role="tab" aria-selected="false" href="${pageContext.request.contextPath}/controller?command=${CommandType.LIST_USER_CREATED_SURVEYS}">My surveys</a>
    <a id="navThemes" class="nav-link" role="tab" aria-selected="false">Themes</a>
    <a id="navUsers" class="nav-link" role="tab" aria-selected="false">Users</a>
    <a id="navEditAccount" class="nav-link" role="tab" aria-selected="false">Account</a>
</div>
</body>
</html>
