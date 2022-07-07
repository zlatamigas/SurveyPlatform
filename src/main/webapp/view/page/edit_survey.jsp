<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title>
        <c:choose>
            <c:when test="${sessionScope.edited_survey.surveyId > 0}"><fmt:message key="title.survey.edit"/></c:when>
            <c:otherwise><fmt:message key="title.survey.add"/></c:otherwise>
        </c:choose>
    </title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container">

    <form id="cancelEditSurveyForm" action="controller" method="post">
        <input type="hidden" name="command" value="${CommandType.CANCEL_EDIT_SURVEY}">
    </form>

    <form id="editSurveyForm" action="controller" method="post">

        <div class="content-container">
            <div class="edit-survey">
                <div class="form-group">
                    <div class="text-danger">
                        <c:if test="${requestScope.form_invalid.survey_name!=null}">
                            <fmt:message key="${requestScope.form_invalid.survey_name}"/>
                        </c:if>
                    </div>
                    <label for="surveyName"><fmt:message key="label.survey.name"/></label>
                    <input name="${DataHolder.PARAMETER_SURVEY_NAME}" type="text" class="form-control" id="surveyName"
                           minlength="1" maxlength="200"
                           value="${sessionScope.edited_survey.name}">
                </div>
                <div class="form-group">
                    <label for="surveyTheme"><fmt:message key="label.survey.theme"/></label>
                    <select name="${DataHolder.PARAMETER_SURVEY_THEME_ID}" id="surveyTheme" class="form-control">

                        <option <c:if test="${sessionScope.edited_survey.theme.themeId == -1}">selected</c:if>
                                value="-1">
                            <fmt:message key="filter.none"/>
                        </option>
                        <c:forEach items="${sessionScope.themes}" var="theme">
                            <option <c:if test="${theme.themeId == sessionScope.edited_survey.theme.themeId}">selected</c:if>
                                    value="${theme.themeId}">${theme.themeName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <div class="text-danger">
                        <c:if test="${requestScope.form_invalid.survey_description!=null}">
                            <fmt:message key="${requestScope.form_invalid.survey_description}"/>
                        </c:if>
                    </div>
                    <label for="surveyDescription"><fmt:message key="label.survey.description"/> </label>
                    <textarea name="${DataHolder.PARAMETER_SURVEY_DESCRIPTION}" class="form-control" id="surveyDescription"
                              rows="3">${sessionScope.edited_survey.description}</textarea>
                </div>
            </div>
        </div>

        <div class="content-container">
            <div class="edit-question-list">
                <c:set var="i" value="0"/>
                <c:forEach items="${sessionScope.edited_survey.questions}" var="question">
                    <div class="card">
                        <div class="card-header">
                            <div class="row justify-content-between">
                                <div class="col">
                                    <h5 class="card-title">${question.formulation}</h5>
                                </div>
                                <div class="col col-auto">
                                    <div class="btn-group" role="group">
                                        <button formaction="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.START_EDIT_QUESTION}&${DataHolder.PARAMETER_CREATE_NEW_QUESTION}=false&${DataHolder.PARAMETER_QUESTION_ID}=${question.questionId}"
                                                type="submit" class="btn btn-outline-primary" formmethod="post">
                                            <i class="fas fa-pencil-alt"></i>
                                        </button>
                                        <button formaction="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.REMOVE_QUESTION}&${DataHolder.PARAMETER_QUESTION_ID}=${question.questionId}"
                                                type="submit" class="btn btn-outline-danger" formmethod="post">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                    <c:set var="i" value="${i+1}"/>
                                </div>
                            </div>

                        </div>
                        <div class="card-body">
                            <div class="card-select-multiple">
                                <p class="card-subtitle mb-2 text-muted">
                                    <fmt:message key="label.question.selectmultiple"/>
                                    <c:choose>
                                        <c:when test="${question.selectMultiple}">
                                            <i class="fas fa-check-circle text-success"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-times-circle text-danger"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <c:forEach items="${question.answers}" var="answer">
                                    <li class="card-text list-group-item">${answer.answer}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <button formaction="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.START_EDIT_QUESTION}&${DataHolder.PARAMETER_CREATE_NEW_QUESTION}=true"
                    formmethod="post" type="submit" class="btn btn-create">
                <fmt:message key="button.add"/></button>
        </div>

        <div class="bottom-actions-container">
            <div class="btn-group-custom">
                <button formmethod="post"
                        formaction="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.FINISH_EDIT_SURVEY}"
                        type="submit" class="btn btn-success">
                    <c:choose>
                    <c:when test="${sessionScope.edited_survey.surveyId > 0}"><fmt:message key="button.save"/></c:when>
                    <c:otherwise><fmt:message key="button.create"/></c:otherwise>
                    </c:choose>
                </button>
                <button form="cancelEditSurveyForm" type="submit" class="btn btn-warning text-light">
                    <fmt:message key="button.cancel"/></button>
            </div>
        </div>
    </form>

</div>

</body>
</html>
