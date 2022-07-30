<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.error404"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- Bootstrap and jQuery --%>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/lib/jquery-3.5.1/jquery-3.5.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/js/bootstrap.bundle.min.js"></script>

    <%-- Fontawesome Icons --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/fontawesome-free-5.15.4-web/css/all.css">
    <script defer src="${pageContext.request.contextPath}/static/lib/fontawesome-free-5.15.4-web/js/all.js"></script>

    <%-- Custom style --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>
<body>
<div class="container-fluid" style="height: 100%; position: absolute; margin: 0;">

    <div class="row align-items-center" style="height: 100%">
        <div class="col">
            <h3 class="display-4"><fmt:message key="error404.title"/></h3>
            <hr class="my-4">

            <span class="mr-sm-2"><fmt:message key="error404.text"/></span>
            <a class="btn btn-outline-primary my-2 my-sm-0"
               href="${pageContext.request.contextPath}/controller?command=${CommandType.HOME}">
                <i class="fas fa-home"></i>
            </a>

        </div>
        <div class="col" style="text-align: center">
            <img src="${pageContext.request.contextPath}/static/pict/error_404.png"
                 alt="<fmt:message key="error404.picture.alttext"/>">
        </div>
    </div>

</div>
</body>
</html>
