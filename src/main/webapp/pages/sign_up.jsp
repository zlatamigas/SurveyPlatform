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
    <title><fmt:message key="title.signup"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<div class="container">

    <h1 class="display-4"><fmt:message key="signup.header"/></h1>
    <hr class="my-4">

    <div class="text-danger">
        <c:if test="${requestScope.user_exists!=null}">
            <fmt:message key="${requestScope.user_exists}"/>
        </c:if>
    </div>

    <form action="controller" method="post">
        <input type="hidden" name="command" value="${CommandType.FINISH_SIGN_UP}">
        <div class="form-group">
            <div class="text-danger">
                <c:if test="${requestScope.form_invalid.email!=null}">
                    <fmt:message key="${requestScope.form_invalid.email}"/>
                </c:if>
            </div>
            <label><fmt:message key="signup.email"/></label>
            <input type="text" class="form-control" name="${DataHolder.PARAMETER_EMAIL}" placeholder="<fmt:message key="signup.email.placeholder"/>">
        </div>
        <div class="form-group">
            <div class="text-danger">
                <c:if test="${requestScope.form_invalid.password!=null}">
                    <fmt:message key="${requestScope.form_invalid.password}"/>
                </c:if>
            </div>
            <label><fmt:message key="signup.password"/></label>
            <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD}" placeholder="<fmt:message key="signup.password.placeholder"/>">
        </div>
        <div class="form-group">
            <div class="text-danger">
                <c:if test="${requestScope.form_invalid.password_repeat!=null}">
                    <fmt:message key="${requestScope.form_invalid.password_repeat}"/>
                </c:if>
            </div>
            <label><fmt:message key="signup.repeatpassword"/></label>
            <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD_REPEAT}" placeholder="<fmt:message key="signup.password.placeholder"/>">
        </div>
        <button type="submit" class="btn btn-primary" ><fmt:message key="signup.credentials.submit"/></button>
    </form>


</div>
</body>
</html>