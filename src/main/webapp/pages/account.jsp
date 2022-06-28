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
    <title><fmt:message key="title.account"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>

<body>

<jsp:include page="/pages/fragment/header.jsp"/>

<div class="container-fluid">

    <c:choose>
        <c:when test="${sessionScope.user.role == UserRole.ADMIN}">

                <div class="row">
                    <div class="col-3">
                        <jsp:include page="fragment/account_left_navbar.jsp"/>
                    </div>
                    <div class="col-9">
                    </div>
                </div>
            </c:when>
            <c:when test="${sessionScope.user.role == UserRole.USER}"></c:when>
            <c:otherwise></c:otherwise>
        </c:choose>

</div>

</body>
</html>
