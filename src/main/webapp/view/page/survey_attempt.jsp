<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>
<%@ taglib uri="/WEB-INF/tld/customtag.tld" prefix="ct"%>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

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

<jsp:include page="/view/fragment/onedit_header.jsp"/>

<div class="container">

    <div class="content-container">
        <div class="padding-container">
            <h1 class="header-text">${sessionScope.survey_attempt.name}</h1>
            <h5 class="subheader-text">${sessionScope.survey_attempt.theme.themeName}</h5>

            <c:if test="${sessionScope.survey_attempt.description != ''}">
                <div class="description-text">${sessionScope.survey_attempt.description}</div>
            </c:if>
        </div>
    </div>

    <div class="content-container">
        <div class="edit-question-list">
            <form id="finishSurveyAttemptForm" action="controller" method="post">
                <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_SURVEY_ATTEMPT}">

                <c:forEach var="question" items="${sessionScope.survey_attempt.questions}">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="card-title">${question.formulation}</h5>
                        </div>
                        <div class="card-body">

                            <div class="text-danger">
                                <ct:question-validation-feedback/>
                            </div>

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
        </div>
    </div>

    <div class="bottom-actions-container">
        <div class="btn-group-custom">
            <button type="button"
                    data-toggle="modal" data-target="#finishSurveyAttempt"
                    class="btn btn-success">
                <fmt:message key="button.survey.attempt.finish"/>
            </button>
            <button type="button"
                    data-toggle="modal" data-target="#cancelSurveyAttempt"
                    class="btn btn-warning">
                <fmt:message key="button.cancel"/>
            </button>
        </div>
    </div>



    <div id="finishSurveyAttempt"
         class="modal fade" tabindex="-1"  aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered ">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <fmt:message key="confirm.finish.survey"/>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-footer">
                    <button form="finishSurveyAttemptForm"
                            formaction="${pageContext.request.contextPath}/controller?command=${CommandType.FINISH_SURVEY_ATTEMPT}"
                            formmethod="post"
                            type="submit"
                            class="btn btn-success">
                        <fmt:message key="button.survey.attempt.finish"/>
                    </button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="button.cancel"/></button>
                </div>
            </div>
        </div>
    </div>

    <div id="cancelSurveyAttempt"
         class="modal fade" tabindex="-1"  aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered ">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <fmt:message key="confirm.cancel.survey"/>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-footer">
                    <form action="controller" method="post">
                        <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.SURVEYS}">
                        <button type="submit" class="btn btn-primary"><fmt:message key="button.ok"/></button>
                    </form>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="button.cancel"/></button>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>
