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
    <title><fmt:message key="title.changepassword.change"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

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
<body>

<div class="container">

    <div class="content-container"/>
        <h1 class="header-text"><fmt:message key="forgotpassword.changepassword.header"/></h1>
        <hr class="my-4">

        <form action="controller" method="post">
            <input type="hidden" name="command" value="${CommandType.CHANGE_PASSWORD}">
            <div class="form-group">
                <input type="text" disabled  class="form-control" name="${DataHolder.SESSION_ATTRIBUTE_CHANGE_PASSWORD_EMAIL}">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD}" placeholder="<fmt:message key="placeholder.password"/>">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD_REPEAT}" placeholder="<fmt:message key="placeholder.password.repeat"/>">
            </div>
            <button type="submit" class="btn btn-custom-fill" ><fmt:message key="button.password.change"/></button>
        </form>
    </div>
</div>
</body>
</html>