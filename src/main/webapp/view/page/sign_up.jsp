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

    <%-- Bootstrap and jQuery --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/lib/jquery-3.5.1/jquery-3.5.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/bootstrap-4.6.1-dist/js/bootstrap.bundle.min.js"></script>
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
            <label><fmt:message key="label.email"/></label>
            <input type="text" class="form-control" name="${DataHolder.PARAMETER_EMAIL}" placeholder="<fmt:message key="placeholder.email"/>">
        </div>
        <div class="form-group">
            <div class="text-danger">
                <c:if test="${requestScope.form_invalid.password!=null}">
                    <fmt:message key="${requestScope.form_invalid.password}"/>
                </c:if>
            </div>
            <label><fmt:message key="label.password"/></label>
            <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD}" placeholder="<fmt:message key="placeholder.password"/>">
        </div>
        <div class="form-group">
            <div class="text-danger">
                <c:if test="${requestScope.form_invalid.password_repeat!=null}">
                    <fmt:message key="${requestScope.form_invalid.password_repeat}"/>
                </c:if>
            </div>
            <label><fmt:message key="label.password.repeat"/></label>
            <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD_REPEAT}" placeholder="<fmt:message key="placeholder.password.repeat"/>">
        </div>
        <button type="submit" class="btn btn-primary" ><fmt:message key="button.signup"/></button>
    </form>


</div>
</body>
</html>