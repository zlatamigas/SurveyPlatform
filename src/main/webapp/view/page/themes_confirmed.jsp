<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.SurveyStatus" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.themesconfirmed"/></title>
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
                document.getElementById("navThemesConfirmed").classList.add("active");
                document.getElementById("navThemes").setAttribute("disabled", "disabled");
            </script>
        </div>
        <div class="col-9">
            <div id="themesContainer" class="hide-on-popup">
                <button id="showAddTheme" class="btn btn-primary">Add new theme</button>
                <c:forEach items="${sessionScope.requested_themes}" var="theme">
                    <div class="card">
                        <div class="card-header">
                            <div class="row justify-content-between">
                                <div class="col">
                                    <h5>${theme.themeName}</h5>
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
            <div id="addThemePopup" class="popup">
                <div class="close-btn"><i class="fas fa-times"></i></div>
                <form action="controller" method="post">
                    <h2>New theme</h2>
                    <div class="form-row">
                        <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="">
                        <div class="col">
                            <input type="text" name="${DataHolder.PARAMETER_THEME_NAME}" class="form-control">
                        </div>
                    </div>
                    <div class="form-row justify-content-end">
                        <div class="col-auto">
                            <button type="button" class="btn btn-warning">Cancel</button>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-confirm">Ok</button>
                        </div>
                    </div>
                </form>
            </div>
            <script>
                document.querySelector("#showAddTheme").addEventListener("click", function (){
                    document.querySelector("#addThemePopup").classList.add("active");
                    document.querySelector("#themesContainer").classList.add("active");
                });
                document.querySelector("#addThemePopup .close-btn").addEventListener("click", function (){
                    document.querySelector("#addThemePopup").classList.remove("active");
                    document.querySelector("#themesContainer").classList.remove("active");
                });
            </script>
        </div>
    </div>
</div>

</body>
</html>
