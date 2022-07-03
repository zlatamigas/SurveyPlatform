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
        <fmt:message key="title.question.crud"/>
    </title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container">

    <form id="cancelEditQuestionForm" action="controller" method="post">
        <input type="hidden" name="command" value="${CommandType.CANCEL_EDIT_QUESTION}">
    </form>

    <form id="editQuestionForm" action="controller" method="post">

        <div class="form-group">
            <div class="text-danger">
                <c:if test="${requestScope.form_invalid.question_formulation!=null}">
                    <fmt:message key="${requestScope.form_invalid.question_formulation}"/>
                </c:if>
            </div>
            <label for="questionFormulation"><fmt:message key="label.question.formulation"/></label>
            <input type="text" name="${DataHolder.PARAMETER_QUESTION_FORMULATION}" class="form-control"
                   id="questionFormulation" minlength="1"
                   value="${sessionScope.edited_question.formulation}">
        </div>
        <div class="form-check">
            <input type="checkbox" name="${DataHolder.PARAMETER_QUESTION_SELECT_MULTIPLE}" class="form-check-input"
                   id="questionSelectMultiple" value="${DataHolder.PARAMETER_QUESTION_SELECT_MULTIPLE}"
                   <c:if test="${sessionScope.edited_question.selectMultiple == true}">checked="checked"</c:if>/>
            <label for="questionSelectMultiple"><fmt:message key="label.question.selectmultiple"/></label>
        </div>

        <c:set var="i" value="0"/>
        <div class="form-group">
            <c:forEach items="${sessionScope.edited_question.answers}" var="answer">
                <div class="row g-2">
                    <div class="col">
                        <input name="${DataHolder.PARAMETER_ANSWER_TEXT}${i}" type="text" class="form-control"
                               value="${answer.answer}"/>
                    </div>
                    <div class="col-auto">
                        <button type="submit" formmethod="post"
                                formaction="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.REMOVE_ANSWER}&${DataHolder.PARAMETER_ANSWER_POSITION}=${i}"
                                class="btn btn-primary mb-3">
                            <fmt:message key="button.delete"/>
                        </button>
                    </div>
                    <c:set var="i" value="${i+1}"/>
                </div>
            </c:forEach>
        </div>

        <button type="submit" formmethod="post" formaction="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.ADD_ANSWER}"
                class="btn btn-primary">
            <fmt:message key="button.add"/>
        </button>

        <hr>

        <div class="btn-group" role="group">
            <button type="submit" formmethod="post" formaction="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.FINISH_EDIT_QUESTION}"
                    class="btn btn-primary"><fmt:message
                    key="button.save"/></button>
            <button form="cancelEditQuestionForm" type="submit" class="btn btn-warning">
                <fmt:message key="button.cancel"/></button>
        </div>
    </form>
</div>

</body>
</html>
