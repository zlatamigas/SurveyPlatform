<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>

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

<div class="container">

<%--    <div class="card text-center">--%>
<%--        <div class="card-body">--%>
<%--            <h5 class="card-title">SurveyPlatform</h5>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <div class="card text-center">--%>
<%--        <div class="card-body">--%>
<%--            <h5 class="card-title">Participate in surveys</h5>--%>
<%--            <p class="card-text"></p>--%>
<%--            <a class="btn btn-primary"--%>
<%--               href="${pageContext.request.contextPath}/controller?command=${CommandType.TO_SURVEYS}">--%>
<%--                &lt;%&ndash;                    <fmt:message key=""/>&ndash;%&gt;--%>
<%--                Participate now--%>
<%--            </a>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <div class="card text-center">--%>
<%--        <div class="card-body">--%>
<%--            <h5 class="card-title">Create your own surveys</h5>--%>
<%--            <p class="card-text"></p>--%>
<%--            <a class="btn btn-primary"--%>
<%--               href="${pageContext.request.contextPath}/controller?command=${CommandType.START_SIGN_IN}">--%>
<%--                &lt;%&ndash;                    <fmt:message key=""/>&ndash;%&gt;--%>
<%--                Sign in--%>
<%--            </a>--%>
<%--        </div>--%>
<%--    </div>--%>


    <%--    <c:choose>--%>
    <%--        <c:when test="${sessionScope.user.role == UserRole.ADMIN}"></c:when>--%>
    <%--        <c:when test="${sessionScope.user.role == UserRole.USER}"></c:when>--%>
    <%--        <c:otherwise></c:otherwise>--%>
    <%--    </c:choose>--%>

</div>
</body>
</html>
