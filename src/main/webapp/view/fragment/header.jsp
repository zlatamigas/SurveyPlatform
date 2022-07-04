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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/lib/jquery-3.5.1/jquery-3.5.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/js/bootstrap.bundle.min.js"></script>

    <%-- Fontawesome Icons --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/fontawesome-free-5.15.4-web/css/all.css">
    <script defer src="${pageContext.request.contextPath}/static/lib/fontawesome-free-5.15.4-web/js/all.js"></script>

    <%-- Custom style --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">

        <span class="navbar-brand"><fmt:message key="header.navbar.brand"/> </span>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.HOME}">
                        <fmt:message key="header.navbar.homepage"/>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.LIST_SURVEYS}">
                        <fmt:message key="header.navbar.surveys"/>
                    </a>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-expanded="false">
                        ${sessionScope.localisation}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.CHANGE_LOCALISATION}&${DataHolder.PARAMETER_LOCALISATION}=en">en</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=${CommandType.CHANGE_LOCALISATION}&${DataHolder.PARAMETER_LOCALISATION}=ru">ru</a>
                    </div>
                </li>
            </ul>

            <c:choose>
                <c:when test="${sessionScope.user != null && sessionScope.user.role != UserRole.GUEST}">
                    <form class="form-inline my-2 my-lg-0" action="controller">
                        <input type="hidden" name="command" value="${CommandType.LOGOUT}">
                        <label class="mr-sm-2">
                                <c:choose>
                                    <c:when test="${sessionScope.user.role == UserRole.ADMIN}">
                                        <fmt:message key="header.navbar.welcome.admin"/>
                                    <div class="account-icon-container">
                                        <a href="${pageContext.request.contextPath}/controller?command=${CommandType.USER_ACCOUNT}" class="account-icon-link">
                                            <i class="fas fa-user-tie account-icon"></i>
                                        </a>
                                    </div>
                                    </c:when>
                                    <c:when test="${sessionScope.user.role == UserRole.USER}">
                                        <fmt:message key="header.navbar.welcome.user"/>
                                    <div class="account-icon-container">
                                        <a href="${pageContext.request.contextPath}/controller?command=${CommandType.USER_ACCOUNT}" class="account-icon-link">
                                            <i class="fas fa-user account-icon" ></i>
                                        </a>
                                    </div>
                                    </c:when>
                                </c:choose>
                                </label>
                        <button type="submit" class="btn btn-sm btn-outline-warning my-2 my-sm-0">
                            <fmt:message key="header.navbar.logout"/>
                        </button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form class="form-inline mt-2 mt-lg-0" action="controller">
                        <input type="hidden" name="command" value="${CommandType.START_SIGN_IN}">
                        <button type="submit" class="btn btn-sm btn-outline-primary">
                            <fmt:message key="header.navbar.login"/>
                        </button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
</header>
