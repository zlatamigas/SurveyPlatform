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
    <title><fmt:message key="title.users"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container-fluid">


    <div class="row">
        <div class="col-3">
            <jsp:include page="/view/fragment/account_left_navbar.jsp"/>
            <script>
                let activeLink = document.getElementById("navUsers");
                activeLink.classList.add("active");
            </script>
        </div>
        <div class="col-9">
            <form action="controller" method="get">
                <input type="hidden" name="command" value="${CommandType.START_CREATE_USER}">
                <button type="submit" class="btn btn-primary"><fmt:message key="button.create"/></button>
            </form>
            <div class="accordion" id="users">
                <div id="usersContainer" class="hide-on-popup">
                    <c:forEach items="${sessionScope.users}" var="user">
                        <c:if test="${sessionScope.user.userId != user.userId}">
                        <div class="card">
                            <div class="card-header">
                                <div class="row justify-content-between">
                                    <div class="col">
                                        <h5>${user.email}</h5>
                                    </div>
                                    <div class="col col-auto">
                                        <form action="controller" method="post">
                                            <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.START_EDIT_USER}">
                                            <input type="hidden" name="${DataHolder.PARAMETER_USER_ID}" value="${user.userId}">
                                            <button class="btn btn-info" type="submit"><i class="fas fa-user-edit"></i></button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>


</div>

</body>
</html>
