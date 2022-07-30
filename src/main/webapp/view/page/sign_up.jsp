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
    <title><fmt:message key="title.signup"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/view/fragment/onedit_header.jsp"/>

<div class="container">

    <div class="form-container">

        <h1 class="header-text"><fmt:message key="signup.header"/></h1>
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
                <label><fmt:message key="label.email"/>
                    <a tabindex="0"
                       data-toggle="popover" data-trigger="hover"
                       data-content="<fmt:message key="popover.hint.email"/>">
                        <i class="fas fa-info-circle"></i>
                    </a>
                </label>
                <input type="text" class="form-control" name="${AttributeParameterHolder.PARAMETER_EMAIL}" placeholder="<fmt:message key="placeholder.email"/>">
            </div>
            <div class="form-group">
                <div class="text-danger">
                    <c:if test="${requestScope.form_invalid.password!=null}">
                        <fmt:message key="${requestScope.form_invalid.password}"/>
                    </c:if>
                </div>
                <label><fmt:message key="label.password"/>
                    <a tabindex="0"
                       data-toggle="popover" data-trigger="hover"
                       data-content="<fmt:message key="popover.hint.password"/>">
                        <i class="fas fa-info-circle"></i>
                    </a>
                </label>
                <input type="password" class="form-control" name="${AttributeParameterHolder.PARAMETER_PASSWORD}" placeholder="<fmt:message key="placeholder.password"/>">
            </div>
            <div class="form-group">
                <div class="text-danger">
                    <c:if test="${requestScope.form_invalid.password_repeat!=null}">
                        <fmt:message key="${requestScope.form_invalid.password_repeat}"/>
                    </c:if>
                </div>
                <label><fmt:message key="label.password.repeat"/>
                    <a tabindex="0"
                       data-toggle="popover" data-trigger="hover"
                       data-content="<fmt:message key="popover.hint.password"/>">
                        <i class="fas fa-info-circle"></i>
                    </a>
                </label>
                <input type="password" class="form-control" name="${AttributeParameterHolder.PARAMETER_PASSWORD_REPEAT}" placeholder="<fmt:message key="placeholder.password.repeat"/>">
            </div>
            <button type="submit" class="btn btn-custom-fill" ><fmt:message key="button.signup"/></button>
        </form>

    </div>

</div>

<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>