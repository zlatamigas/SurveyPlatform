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
    <title><fmt:message key="title.forgotpasswordreceivekey"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<div class="container">

    <h1 class="display-4"><fmt:message key="forgotpasswordreceivekey.header"/></h1>
    <hr class="my-4">

    <form action="controller" method="post">
        <input type="hidden" name="command" value="${CommandType.CONFIRM_CHANGE_PASSWORD_KEY}">
        <div class="form-group">
            <input type="text" class="form-control" name="${DataHolder.PARAMETER_FORGOT_PASSWORD_CHANGE_KEY}" placeholder="<fmt:message key="forgotpasswordreceivekey.key"/>">
        </div>
        <button type="submit" class="btn btn-primary" ><fmt:message key="forgotpasswordreceivekey.credentials.submit"/></button>
    </form>

</div>
</body>
</html>