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
    <title><fmt:message key="title.addsurvey"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="container">

    <form action="controller" method="POST">

        <input type="hidden" name="command" value="finish_edit_survey">

        <div class="form-group">
            <label for="surveyName"><fmt:message key="editsurvey.label.surveyname"/></label>
            <input name="${DataHolder.PARAMETER_SURVEY_NAME}" type="text" class="form-control" id="surveyName" minlength="1" maxlength="200"
                   value="${sessionScope.edited_survey.name}">
        </div>

        <div class="form-group">
            <label for="surveyTheme"><fmt:message key="editsurvey.label.surveytheme"/></label>
            <select name="${DataHolder.PARAMETER_SURVEY_THEME_ID}" id="surveyTheme" class="form-control">
                <c:forEach items="${sessionScope.themes}" var="theme">
                    <c:choose>
                        <c:when test="${theme.themeId != sessionScope.edited_survey.theme.themeId}">
                            <option value="${theme.themeId}">${theme.themeName}</option>
                        </c:when>
                        <c:otherwise>
                            <option selected value="${theme.themeId}">${theme.themeName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="surveyDescription"><fmt:message key="editsurvey.label.surveydescription"/> </label>
            <textarea name="${DataHolder.PARAMETER_SURVEY_DESCRIPTION}" class="form-control" id="surveyDescription"
                      rows="3">${sessionScope.edited_survey.description}</textarea>
        </div>

        <button type="submit" class="btn btn-primary"><fmt:message key="editsurvey.savesurvey"/></button>
    </form>
    <hr>


    <c:forEach items="${sessionScope.edited_survey.questions}" var="question">
        <div class="card">
            <div class="card-header">

                <div class="row justify-content-between">
                    <div class="col">
                        <h5 class="card-title">${question.formulation}</h5>
                    </div>
                    <div class="col col-auto">

                        <form id="startEditQuestionForm${question.questionId}" action="controller" method="post">
                            <input type="hidden" name="command" value="${CommandType.START_EDIT_QUESTION}">
                            <input type="hidden" name="question_id" value="${question.questionId}">
                        </form>
                        <form id="deleteQuestionForm${question.questionId}" action="controller" method="post">
                            <input type="hidden" name="command" value="">
                            <input type="hidden" name="question_id" value="${question.questionId}">
                        </form>

                        <div class="btn-group" role="group">
                            <button type="submit" form="startEditQuestionForm${question.questionId}" class="btn btn-primary"><fmt:message key="editsurvey.editquestion"/></button>
                            <button type="submit" form="deleteQuestionForm${question.questionId}" class="btn btn-warning"><fmt:message key="editsurvey.deletequestion"/></button>
                        </div>
                    </div>
                </div>


            </div>
            <div class="card-body">
                <p class="card-subtitle mb-2 text-muted"><fmt:message key="editsurvey.question.selectmultiple"/> ${question.selectMultiple}</p>
                <ul>
                    <c:forEach items="${question.answers}" var="answer">
                        <li class="card-text">${answer.answer}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:forEach>


</div>

</body>
</html>
