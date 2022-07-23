<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<head>
    <%-- Bootstrap and jQuery --%>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/lib/jquery-3.5.1/jquery-3.5.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/popper/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/js/bootstrap.bundle.min.js"></script>

    <%-- Fontawesome Icons --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/fontawesome-free-5.15.4-web/css/all.css">
    <script defer src="${pageContext.request.contextPath}/static/lib/fontawesome-free-5.15.4-web/js/all.js"></script>

    <%-- Custom style --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">

    <script>
        $(function () {
            $('[data-toggle="popover"]').popover()
        })
    </script>
</head>

<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-custom">

        <span class="navbar-brand"><fmt:message key="header.navbar.brand"/> </span>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="navbar-nav mr-auto">
                <li id="navHome" class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.HOME}">
                        <fmt:message key="header.navbar.homepage"/>
                    </a>
                </li>

                <li id="navSurveys" class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.SURVEYS}">
                        <fmt:message key="header.navbar.surveys"/>
                    </a>
                </li>

                <li class="nav-item dropdown">

                    <a class="nav-link" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-globe"></i>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.CHANGE_LOCALISATION}&${DataHolder.SESSION_ATTRIBUTE_PARAMETER_LOCALISATION}=en">English</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.CHANGE_LOCALISATION}&${DataHolder.SESSION_ATTRIBUTE_PARAMETER_LOCALISATION}=ru">Русский</a>
                    </div>
                </li>
            </ul>

            <ul class="navbar-nav justify-content-end align-items-center">

                <c:choose>
                    <c:when test="${sessionScope.user != null && sessionScope.user.role != UserRole.GUEST}">
                        <li class="nav-item">
                            <c:choose>
                                <c:when test="${sessionScope.user.role == UserRole.ADMIN}">
                                    <a href="${pageContext.request.contextPath}/controller?command=${CommandType.USER_ACCOUNT}"
                                       class="btn btn-account">
                                        <span class="account-text"><fmt:message key="role.admin"/></span>
                                        <i class="fas fa-user-tie account-icon"></i>
                                    </a>
                                </c:when>
                                <c:when test="${sessionScope.user.role == UserRole.USER}">
                                    <a href="${pageContext.request.contextPath}/controller?command=${CommandType.USER_ACCOUNT}"
                                       class="btn btn-account">
                                        <span class="account-text"><fmt:message key="role.user"/></span>
                                        <i class="fas fa-user account-icon"></i>
                                    </a>
                                </c:when>
                            </c:choose>
                        </li>
                        <li class="nav-item">
                            <form action="controller">
                                <input type="hidden" name="command" value="${CommandType.LOG_OUT}">
                                <button type="submit" class="btn btn-logout">
                                    <fmt:message key="header.navbar.logout"/>
                                </button>
                            </form>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <form action="controller">
                                <input type="hidden" name="command" value="${CommandType.SIGN_IN}">
                                <button type="submit" class="btn btn-login">
                                    <fmt:message key="header.navbar.login"/>
                                </button>
                            </form>
                        </li>
                    </c:otherwise>
                </c:choose>
                </li>
            </ul>
        </div>
    </nav>
</header>
