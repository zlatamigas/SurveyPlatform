<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.SurveyStatus" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.usersurveys"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">

    <form action="controller" method="post">
        <input type="hidden" name="command" value="${CommandType.START_EDIT_SURVEY}">
        <input type="hidden" name="${DataHolder.PARAMETER_CREATE_NEW_SURVEY}" value="true">
        <button type="submit" class="btn btn-primary"><fmt:message key="usersurvey.createsurvey"/></button>
    </form>


    <div class="accordion" id="userSurveys">


        <c:forEach items="${sessionScope.user_surveys}" var="survey">

            <div class="card">
                <div class="card-header" id="heading${survey.surveyId}">

                    <div class="row justify-content-between">
                        <div class="col">
                            <h5 class="card-title">${survey.name}</h5>
                        </div>
                        <div class="col col-auto">
                            <button class="btn" type="button" data-toggle="collapse"
                                    data-target="#collapse${survey.surveyId}" aria-expanded="true"
                                    aria-controls="collapse${survey.surveyId}"><i class="fas fa-angle-down"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <div id="collapse${survey.surveyId}" class="collapse" aria-labelledby="heading${survey.surveyId}"
                     data-parent="#userSurveys">
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-muted">${survey.theme.themeName}</h6>
                        <p class="card-subtitle mb-2 text-muted">${survey.status}</p>

                        <p class="card-text">${survey.description}</p>

                        <div class="btn-toolbar justify-content-end" role="toolbar">

                            <form id="startEditSurveyForm${survey.surveyId}" action="controller" method="post">
                                <input type="hidden" name="command" value="${CommandType.START_EDIT_SURVEY}">
                                <input type="hidden" name="${DataHolder.PARAMETER_CREATE_NEW_SURVEY}" value="false">
                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}" value="${survey.surveyId}">
                            </form>

                            <form id="deleteSurveyForm${survey.surveyId}" action="controller" method="post">
                                <input type="hidden" name="command" value="${CommandType.DELETE_SURVEY}">
                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}" value="${survey.surveyId}">
                            </form>

                            <form id="stopSurveyForm${survey.surveyId}" action="controller" method="post">
                                <input type="hidden" name="command" value="${CommandType.STOP_SURVEY}">
                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}" value="${survey.surveyId}">
                            </form>

                            <form id="viewResultSurveyForm${survey.surveyId}" action="controller" method="post">
                                <input type="hidden" name="command" value="">
                                <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}" value="${survey.surveyId}">
                            </form>

                            <div class="btn-group" role="group">
                                <c:choose>
                                    <c:when test="${survey.status == SurveyStatus.NOT_STARTED}">
                                        <button form="startEditSurveyForm${survey.surveyId}" type="submit" class="btn btn-primary"><fmt:message
                                                key="usersurvey.editsurvey"/></button>
                                    </c:when>
                                    <c:when test="${survey.status == SurveyStatus.STARTED}">
                                        <button form="stopSurveyForm${survey.surveyId}" type="submit" class="btn btn-primary"><fmt:message
                                                key="usersurvey.stopsurvey"/></button>
                                    </c:when>
                                    <c:when test="${survey.status == SurveyStatus.CLOSED}">
                                        <button form="viewResultSurveyForm${survey.surveyId}" type="submit" class="btn btn-primary"><fmt:message
                                                key="usersurvey.viewresultsurvey"/></button>
                                    </c:when>
                                </c:choose>

                                <c:if test="${survey.status != SurveyStatus.STARTED}">
                                    <button form="deleteSurveyForm${survey.surveyId}" type="submit" class="btn btn-warning"><fmt:message
                                            key="usersurvey.deletesurvey"/></button>
                                </c:if>

                            </div>

                        </div>
                    </div>
                </div>

            </div>

        </c:forEach>

    </div>


</div>

</body>
</html>
