<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>


<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.signin"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- Bootstrap and jQuery --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/lib/jquery-3.5.1/jquery-3.5.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/js/bootstrap.bundle.min.js"></script>

    <%-- Custom style --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>
<body>

<div class="container">

    <div class="authorisation-container">

        <h1 class="authorisation-header-text"><fmt:message key="signin.header"/></h1>
        <hr class="my-4">

        <c:if test="${requestScope.user_invalid!=null}">
        <div class="text-danger">
            <fmt:message key="${requestScope.user_invalid}"/>
        </div>
        </c:if>
        <c:if test="${requestScope.user_banned!=null}">
        <div class="text-danger">
            <fmt:message key="${requestScope.user_banned}"/>
        </div>
        </c:if>

        <form action="controller" method="post">
            <input type="hidden" name="command" value="${CommandType.FINISH_SIGN_IN}">
            <div class="form-group">
                <div class="text-danger">
                    <c:if test="${requestScope.form_invalid.email!=null}">
                        <fmt:message key="${requestScope.form_invalid.email}"/>
                    </c:if>
                </div>
                <label><fmt:message key="label.email"/></label>
                <input type="text" class="form-control" name="${DataHolder.PARAMETER_EMAIL}"
                       placeholder="<fmt:message key="placeholder.email"/>">
            </div>
            <div class="form-group">
                <div class="text-danger">
                    <c:if test="${requestScope.form_invalid.password!=null}">
                        <fmt:message key="${requestScope.form_invalid.password}"/>
                    </c:if>
                </div>
                <label><fmt:message key="label.password"/></label>
                <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD}"
                       placeholder="<fmt:message key="placeholder.password"/>">
                <div style=" text-align: end;">
                    <a href="${pageContext.request.contextPath}/controller?command=${CommandType.TO_FORGOT_PASSWORD}">
                        <fmt:message key="signin.forgotpassword"/></a>
                </div>
            </div>
            <button type="submit" class="btn btn-authorisation"><fmt:message key="button.signin"/></button>
        </form>

        <div class="offer-register-container">
            <p><fmt:message key="signin.registernow"/></p>
            <a class="btn btn-outline-custom" href="${pageContext.request.contextPath}/controller?command=${CommandType.START_SIGN_UP}">
                <fmt:message key="button.registernow"/></a>
        </div>
    </div>
</div>
</body>
</html>