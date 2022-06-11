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
    <title><fmt:message key="title.surveys"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">

    <div class="accordion" id="allSurveys">


        <c:forEach items="${sessionScope.surveys}" var="survey">

            <div class="card">
                <div class="card-header" id="heading${survey.surveyId}">

                    <div class="row justify-content-between">
                        <div class="col">
                            <h5>${survey.name}</h5>
                        </div>
                        <div class="col col-auto">
                            <button class="btn" type="button" data-toggle="collapse"
                                    data-target="#collapse${survey.surveyId}" aria-expanded="true"
                                    aria-controls="collapse${survey.surveyId}"><i class="fas fa-angle-down"></i></button>
                        </div>
                    </div>
                </div>

                <div id="collapse${survey.surveyId}" class="collapse" aria-labelledby="heading${survey.surveyId}"
                     data-parent="#allSurveys">
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-muted">${survey.theme.themeName}</h6>
                        <p class="card-text">${survey.description}</p>

                        <form ></form>

                        <form id="startAttemptSurveyForm${survey.surveyId}" action="controller" method="post">
                            <input type="hidden" name="command" value="${CommandType.START_SURVEY_ATTEMPT}">
                            <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}" value="${survey.surveyId}">
                            <button form="startAttemptSurveyForm${survey.surveyId}" type="submit" class="btn btn-primary">
                                <fmt:message key="surveyattempt.startattempt"/></button>
                        </form>
                    </div>
                </div>

            </div>

        </c:forEach>

    </div>

</div>

</body>
</html>
