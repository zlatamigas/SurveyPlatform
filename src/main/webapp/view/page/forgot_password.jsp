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
    <title><fmt:message key="title.changepassword.forgotpassword"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- Bootstrap and jQuery --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/lib/jquery-3.5.1/jquery-3.5.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container">

    <h1 class="display-4"><fmt:message key="forgotpassword.header"/></h1>
    <hr class="my-4">

    <form action="controller" method="post">
        <input type="hidden" name="command" value="${CommandType.SEND_FORGOTTEN_PASSWORD_KEY}">
        <div class="form-group">
            <input type="text" class="form-control" name="${DataHolder.PARAMETER_EMAIL}" placeholder="<fmt:message key="placeholder.email"/>">
        </div>
        <button type="submit" class="btn btn-primary" ><fmt:message key="button.password.key.send"/></button>
    </form>

</div>
</body>
</html>