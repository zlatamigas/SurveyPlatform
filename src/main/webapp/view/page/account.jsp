<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserStatus" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>


<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.account"/></title>
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
                let activeLink = document.getElementById("navUserAccount");
                activeLink.classList.add("active");
            </script>
        </div>
        <div class="col-9">
            <div class="card account-card">
                <div class="card-header">
                    <h4 class="card-title"><i class="fas fa-bars"></i> <fmt:message key="account.personalinfo"/> </h4>
                </div>
                <div class="card-body">
                    <div class="row align-items-center">
                        <div class="col-3">
                            <p class="card-text"><fmt:message key="label.email"/></p>
                        </div>
                        <div class="col">
                            <p class="card-text">${sessionScope.user.email}</p>
                        </div>
                    </div>
                    <div class="row align-items-center">
                        <div class="col-3">
                            <p class="card-text"><fmt:message key="label.user.role"/></p>
                        </div>
                        <div class="col">
                            <p class="card-text">
                            <c:if test="${sessionScope.user.role == UserRole.ADMIN}">
                                <fmt:message key="role.admin"/>
                            </c:if>
                            <c:if test="${sessionScope.user.role == UserRole.USER}">
                                <fmt:message key="role.user"/>
                            </c:if>
                            </p>
                        </div>
                    </div>
                    <div class="row align-items-center">
                        <div class="col-3">
                            <p class="card-text"><fmt:message key="label.user.status"/></p>
                        </div>
                        <div class="col">
                            <c:if test="${sessionScope.user.status == UserStatus.ACTIVE}">
                                <p class="card-text text-success"><fmt:message key="status.user.active"/></p>
                            </c:if>
                            <c:if test="${sessionScope.user.status == UserStatus.BANNED}">
                                <p class="card-text text-danger"><fmt:message key="status.user.banned"/></p>
                            </c:if>
                        </div>
                    </div>
                    <div class="row align-items-center">
                        <div class="col-3">
                            <p class="card-text"><fmt:message key="label.user.registrationdate"/></p>
                        </div>
                        <div class="col">
                            <p class="card-text">${sessionScope.user.registrationDate}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card account-card">
                <div class="card-header">
                    <h4 class="card-title"><i class="fas fa-user-cog"></i> <fmt:message key="account.management"/></h4>
                </div>
                <div class="card-body">
                    <div class="row align-items-center">
                        <div class="col">
                            <p class="card-text"><fmt:message key="account.changepassword"/></p>
                        </div>
                        <div class="col-auto">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="${CommandType.SEND_FORGOTTEN_PASSWORD_KEY}">
                                <input type="hidden" name="${DataHolder.PARAMETER_EMAIL}" value="${sessionScope.user.email}"/>
                                <button type="submit" class="btn btn-dark" ><i class="fas fa-angle-right"></i></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>

</body>
</html>
