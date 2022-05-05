<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.error500"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid" style="height: 100%; position: absolute; margin: 0;">

    <div class="row align-items-center" style="height: 100%">
        <div class="col">
            <h3 class="display-4"><fmt:message key="error500.title"/></h3>
            <hr class="my-4">
            <form class="form-inline" action="controller">
                <input type="hidden" name="command" value="default">
                <label class="mr-sm-2"><fmt:message key="error500.text"/></label>
                <button type="submit" class="btn btn-outline-primary my-2 my-sm-0">&#8962;</button>
            </form>
        </div>
        <div class="col">
            <img src="${pageContext.request.contextPath}/static/pict/error_500.png" alt="<fmt:message key="error500.pictalttext"/>">
        </div>
    </div>


</div>
</body>
</html>
