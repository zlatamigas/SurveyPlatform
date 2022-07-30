<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="com.zlatamigas.surveyplatform.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<html lang="${sessionScope.localisation}">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="nav flex-column nav-pills left-navbar" role="tablist" aria-orientation="vertical">

    <a id="navUserAccount" class="nav-link" role="tab" aria-selected="false"
       href="${pageContext.request.contextPath}/controller?command=${CommandType.USER_ACCOUNT}"><fmt:message key="leftnav.navbar.account"/></a>

    <div class="dropdown-divider"></div>

    <a id="navUserSurveys" class="nav-link" role="tab" aria-selected="false"
       href="${pageContext.request.contextPath}/controller?command=${CommandType.USER_SURVEYS}"><fmt:message key="leftnav.navbar.usersurveys"/></a>




    <div class="dropdown-divider"></div>
    <a id="navThemes"
       class="nav-link"
       type="button" data-toggle="collapse"
       data-target="#collapseTheme" aria-expanded="false" aria-controls="collapseTheme"><fmt:message key="leftnav.navbar.themes"/></a>

    <div id="collapseTheme" class="collapse">
        <div class="left-nav-collapse">
            <a id="navThemesConfirmed" class="nav-link" role="tab" aria-selected="false" href="${pageContext.request.contextPath}/controller?command=${CommandType.THEMES_CONFIRMED}"><fmt:message key="leftnav.navbar.themes.confirmed"/></a>
            <c:if test="${sessionScope.user.role == UserRole.ADMIN}">
                <a id="navThemesWaiting" class="nav-link" role="tab" aria-selected="false" href="${pageContext.request.contextPath}/controller?command=${CommandType.THEMES_WAITING}"><fmt:message key="leftnav.navbar.themes.waiting"/></a>
            </c:if>
        </div>
    </div>
    <c:if test="${sessionScope.user.role == UserRole.ADMIN}">
        <div class="dropdown-divider"></div>
        <a id="navUsers" class="nav-link" role="tab" aria-selected="false"
           href="${pageContext.request.contextPath}/controller?command=${CommandType.USERS}">
            <fmt:message key="leftnav.navbar.users"/></a>
    </c:if>


</div>
</body>
</html>
