<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>
<%@ page import="java.util.Map" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.SurveyQuestion" %>
<%@ page import="epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey" %>
<%@ page import="static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_MULTIPLE" %>
<%@ page import="static epam.zlatamigas.surveyplatform.controller.navigation.DataHolder.*" %>
<%@ page import="static epam.zlatamigas.surveyplatform.util.locale.LocalisedMessageKey.MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_SINGLE" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>
<fmt:message key="${LocalisedMessageKey.MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_MULTIPLE}" var="questionCheckboxInvalid" />
<fmt:message key="${LocalisedMessageKey.MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_SINGLE}" var="questionRadiobuttonInvalid" />

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.survey.attempt"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container">

    <div class="card">
        <div class="card-body">
            <h3 class="card-title">${sessionScope.survey_attempt.name}</h3>
            <hr>
            <h5 class="card-subtitle">${sessionScope.survey_attempt.theme.themeName}</h5>
            <div class="cart-text">${sessionScope.survey_attempt.description}</div>
        </div>

    </div>

    <form id="finishSurveyAttemptForm" action="controller" method="post">
        <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_SURVEY_ATTEMPT}">

        <c:forEach var="question" items="${sessionScope.survey_attempt.questions}">
            <div class="card">
                <div class="card-body">

                    <div class="text-danger">
                        <%
                            SurveyQuestion question = (SurveyQuestion) pageContext.getAttribute("question");
                            Map<String, String> validationFeedback =
                                    (Map<String, String>)request.getAttribute(REQUEST_ATTRIBUTE_FORM_INVALID);
                            if(validationFeedback != null) {
                                String fmtMessage = validationFeedback.get(PARAMETER_QUESTION_ID + question.getQuestionId());
                                if (fmtMessage != null) {
                                    if(fmtMessage.equals(MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_MULTIPLE)){
                                        String questionCheckboxInvalid =
                                                (String) pageContext.getAttribute("questionCheckboxInvalid");
                                        out.write(questionCheckboxInvalid);
                                    } else if (fmtMessage.equals(MESSAGE_INVALID_ANSWER_REQUIRE_SELECT_SINGLE)) {
                                        String questionRadiobuttonInvalid =
                                                (String) pageContext.getAttribute("questionRadiobuttonInvalid");
                                        out.write(questionRadiobuttonInvalid);
                                    }
                                }
                            }
                        %>
                    </div>

                    <p class="card-title">${question.formulation}</p>
                    <input type="hidden" name="${DataHolder.PARAMETER_QUESTION_SELECT_MULTIPLE}${question.questionId}"
                           value="${question.selectMultiple}">

                    <c:forEach items="${question.answers}" var="answer">
                        <div class="form-check">
                            <c:choose>
                                <c:when test="${question.selectMultiple == true}">
                                    <input type="checkbox" class="form-check-input"
                                           id="question${question.questionId}Answer${answer.questionAnswerId}"
                                           name="${DataHolder.BUTTONGROUP_NAME_CHECKBOX_ANSWERS}${question.questionId}"
                                           value="${answer.questionAnswerId}"
                                           <c:if test="${answer.selectedCount == 1}">checked</c:if>>
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" class="form-check-input"
                                           id="question${question.questionId}Answer${answer.questionAnswerId}"
                                           name="${DataHolder.BUTTONGROUP_NAME_RADIO_ANSWERS}${question.questionId}"
                                           value="${answer.questionAnswerId}"
                                           <c:if test="${answer.selectedCount == 1}">checked</c:if>>
                                </c:otherwise>
                            </c:choose>
                            <label class="form-check-label"
                                   for="question${question.questionId}Answer${answer.questionAnswerId}">${answer.answer}</label>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </form>

    <form id="cancelSurveyAttemptForm" action="controller" method="post">
        <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.CANCEL_SURVEY_ATTEMPT}">
    </form>

    <hr>

    <div class="btn-group" role="group">
        <button form="finishSurveyAttemptForm" type="submit" class="btn btn-success">
            <fmt:message key="button.survey.attempt.finish"/></button>
        <button form="cancelSurveyAttemptForm" type="submit" class="btn btn-warning">
            <fmt:message key="button.cancel"/></button>
    </div>
</div>

</body>
</html>
