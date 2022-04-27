<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.authorisation"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Zlata Migas">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<div class="container">

    <h1 class="display-4"><fmt:message key="authorisation.header"/></h1>
    <hr class="my-4">

    <form action="controller" method="POST">
        <input type="hidden" name="command" value="login">
        <div class="form-group">
            <label><fmt:message key="authorisation.email"/></label>
            <input type="text" class="form-control" name="login">
        </div>
        <div class="form-group">
            <label><fmt:message key="authorisation.password"/></label>
            <input type="password" class="form-control" name="password">
        </div>
        <button type="submit" class="btn btn-primary" ><fmt:message key="authorisation.credentials.submit"/></button>
    </form>
    <p>${login_msg}</p>
</div>
</body>
</html>