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
    <title><fmt:message key="title.themeswaiting"/></title>
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
                document.getElementById("collapseTheme").classList.add("show");
                document.getElementById("navThemesWaiting").classList.add("active");
                document.getElementById("navThemes").setAttribute("disabled", "disabled");
            </script>
        </div>
        <div class="col-9">
            <c:forEach items="${sessionScope.requested_themes}" var="theme">
                <div class="card">
                    <div class="card-header">
                        <div class="row justify-content-between">
                            <div class="col">
                                <h5>${theme.themeName}</h5>
                            </div>
                            <div class="col col-auto">
                                <form action="controller" method="post">
                                    <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.CONFIRM_THEME}">
                                    <input type="hidden" name="${DataHolder.PARAMETER_THEME_ID}" value="${theme.themeId}">
                                    <button class="btn" type="submit">Confirm</button>
                                </form>
                            </div>
                            <div class="col col-auto">
                                <form action="controller" method="post">
                                    <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.DELETE_THEME}">
                                    <input type="hidden" name="${DataHolder.PARAMETER_THEME_ID}" value="${theme.themeId}">
                                    <button class="btn" type="submit">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>


</div>

</body>
</html>
