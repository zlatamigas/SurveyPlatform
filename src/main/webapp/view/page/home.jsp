<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.home"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>

<jsp:include page="/view/fragment/header.jsp"/>
<script>
    let activeLink = document.getElementById("navHome");
    activeLink.classList.add("active");
</script>

<div class="home-container">

    <div class="row align-items-center">
        <div class="col col-md-3 offset-md-2">
            <img src="${pageContext.request.contextPath}/static/pict/survey.jpg" style="width: 100%"
                alt="Survey. Illustration вектор создан(а) storyset - ru.freepik.com">
        </div>
        <div class="col col-md-4 offset-md-1">
            <h5 class="header-text"><fmt:message key="header.navbar.brand"/></h5>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <div class="card text-center">
                <div class="card-body">
                    <h5 class="card-title"><fmt:message key="home.participate.header"/></h5>
                    <p class="card-text"></p>
                    <a class="btn btn-outline-custom"
                       href="${pageContext.request.contextPath}/controller?command=${CommandType.SURVEYS}">
                        <fmt:message key="home.participate.button"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="col">
            <c:choose>
                <c:when test="${sessionScope.user.role == UserRole.ADMIN || sessionScope.user.role == UserRole.USER}">
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title"><fmt:message key="home.account.header"/></h5>
                            <p class="card-text"></p>
                            <a class="btn btn-outline-custom"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.USER_ACCOUNT}">
                                <fmt:message key="home.account.button"/>
                            </a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="card text-center">
                        <div class="card-body">
                            <h5 class="card-title"><fmt:message key="home.signin.header"/></h5>
                            <p class="card-text"></p>
                            <a class="btn btn-outline-custom"
                               href="${pageContext.request.contextPath}/controller?command=${CommandType.SIGN_IN}">
                                <fmt:message key="home.signin.button"/>
                            </a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>


</div>

<jsp:include page="/view/fragment/footer.jsp"/>

</body>
</html>
