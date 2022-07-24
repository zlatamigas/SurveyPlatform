<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder" %>


<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.changepassword.forgotpassword"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/view/fragment/onedit_header.jsp"/>

<div class="container">

    <div class="form-container">
        <h1 class="display-4"><fmt:message key="forgotpassword.header"/></h1>
        <hr class="my-4">

        <form action="controller" method="post">
            <input type="hidden" name="command" value="${CommandType.FINISH_SEND_KEY}">
            <div class="form-group">
                <div class="text-danger">
                    <c:if test="${requestScope.form_invalid.email!=null}">
                        <fmt:message key="${requestScope.form_invalid.email}"/>
                    </c:if>
                </div>
                <label><fmt:message key="label.email"/>
                </label>
                <input type="text" class="form-control" name="${AttributeParameterHolder.PARAMETER_EMAIL}"
                       placeholder="<fmt:message key="placeholder.email"/>">
            </div>
            <button type="submit" class="btn btn-custom-fill"><fmt:message key="button.password.key.send"/></button>
        </form>
    </div>
</div>

<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>