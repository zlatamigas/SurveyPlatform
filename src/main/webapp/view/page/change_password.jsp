<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>


<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.changepassword.change"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/view/fragment/onedit_header.jsp"/>

<div class="container">

    <div class="form-container">
        <h1 class="header-text"><fmt:message key="forgotpassword.changepassword.header"/></h1>
        <hr class="my-4">

        <form action="controller" method="post">
            <input type="hidden" name="command" value="${CommandType.FINISH_CHANGE_PASSWORD}">
            <div class="form-group">
                <div class="text-danger">
                    <c:if test="${requestScope.form_invalid.email!=null}">
                        <fmt:message key="${requestScope.form_invalid.email}"/>
                    </c:if>
                </div>
                <label><fmt:message key="label.email"/></label>
                <p class="form-control">
                <c:choose>
                    <c:when test="${sessionScope.user != null && sessionScope.user.role != UserRole.GUEST}">
                       ${sessionScope.user.email}
                    </c:when>
                    <c:otherwise>
                       ${requestScope.user_email}
                    </c:otherwise>
                </c:choose>
                </p>
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
                <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD}"
                       placeholder="<fmt:message key="placeholder.password"/>">
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
                <input type="password" class="form-control" name="${DataHolder.PARAMETER_PASSWORD_REPEAT}"
                       placeholder="<fmt:message key="placeholder.password.repeat"/>">
            </div>
            <button type="submit" class="btn btn-custom-fill"><fmt:message key="button.password.change"/></button>
        </form>
    </div>

    <jsp:include page="/view/fragment/footer.jsp"/>
</div>
</body>
</html>