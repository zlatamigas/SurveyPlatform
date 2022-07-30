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
    <title><fmt:message key="title.changepassword.receivekey"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/view/fragment/onedit_header.jsp"/>

    <div class="container">

        <div class="form-container">
        <h1 class="display-4"><fmt:message key="forgotpassword.receivekey.header"/></h1>
        <hr class="my-4">

        <form action="controller" method="post">
            <input type="hidden" name="command" value="${CommandType.FINISH_CONFIRM_KEY}">
            <c:if test="${requestScope.password_change_confirm_key_invalid!=null}">
                <div class="text-danger">
                    <fmt:message key="${requestScope.password_change_confirm_key_invalid}"/>
                </div>
            </c:if>
            <div class="form-group">
                <label><fmt:message key="label.password.change.keysenttoemail"/> <c:out value="${sessionScope.change_password_email}"/></label>
                <input type="text" class="form-control" name="${AttributeParameterHolder.PARAMETER_RECEIVED_PASSWORD_CHANGE_KEY}" placeholder="<fmt:message key="placeholder.receivedkey"/>">
            </div>
            <button type="submit" class="btn btn-custom-fill" ><fmt:message key="button.password.key.confirm"/></button>
        </form>
        </div>
    </div>

<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>