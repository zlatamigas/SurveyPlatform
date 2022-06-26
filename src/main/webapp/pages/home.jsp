<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.homepage"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">


    <div class="container">
        <div class="row">
            <div class="col">
                <p>SurveyPlatform</p>
            </div>
        </div>
        <div class="row">
            <div class="col">
                Participate in surveys
            </div>
            <div class="col">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=${CommandType.LIST_SURVEYS}">
<%--                    <fmt:message key=""/>--%>
                    Participate now
                </a>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=${CommandType.START_SIGN_IN}">
<%--                    <fmt:message key=""/>--%>
                    Sign in
                </a>
            </div>
            <div class="col">
                Create your own surveys
            </div>
        </div>
    </div>

<%--    <c:choose>--%>
<%--        <c:when test="${sessionScope.user.role == UserRole.ADMIN}"></c:when>--%>
<%--        <c:when test="${sessionScope.user.role == UserRole.USER}"></c:when>--%>
<%--        <c:otherwise></c:otherwise>--%>
<%--    </c:choose>--%>

</div>

</body>
</html>
