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

        <div class="content-container">
            <div class="edit-question">

                <h1 class="header-text"><fmt:message key="editquestion.header"/></h1>
                <hr class="my-4">

                <div class="form-group">
                    <div class="text-danger">
                        <c:if test="${requestScope.form_invalid.question_formulation!=null}">
                            <fmt:message key="${requestScope.form_invalid.question_formulation}"/>
                        </c:if>
                    </div>
                    <label for="questionFormulation"><fmt:message key="label.question.formulation"/></label>
                    <textarea name="${DataHolder.PARAMETER_QUESTION_FORMULATION}" class="form-control"
                           id="questionFormulation" rows="3">${sessionScope.edited_question.formulation}</textarea>
                </div>
                <div class="form-check">
                    <input type="checkbox" name="${DataHolder.PARAMETER_QUESTION_SELECT_MULTIPLE}" class="form-check-input"
                           id="questionSelectMultiple" value="${DataHolder.PARAMETER_QUESTION_SELECT_MULTIPLE}"
                           <c:if test="${sessionScope.edited_question.selectMultiple == true}">checked="checked"</c:if>/>
                    <label for="questionSelectMultiple"><fmt:message key="label.question.selectmultiple"/></label>
                </div>
            </div>
        </div>

        <div class="content-container">


            <c:set var="i" value="0"/>
            <div id="answers-container" class="form-group">
                <c:forEach items="${sessionScope.edited_question.answers}" var="answer">
                    <c:set var="i" value="${i+1}"/>
                    <div id="rowanswer${i}" class="row row-question">
                        <div class="col">
                            <textarea id="textarea${i}" name="${DataHolder.PARAMETER_ANSWER_TEXT}${i}" rows="3"
                                      class="form-control textarea-answer">${answer.answer}</textarea>

                        </div>
                        <div class="col-auto">
                            <button type="button"
                                    class="btn btn-outline-danger btn-delete-answer"
                                    onclick="removeQuestion(${i});">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <input id="input-last-answer" type="hidden" name="${DataHolder.PARAMETER_LAST_ANSWER_POSITION}" value="${i}">

            <script>
                let questionsCount = ${i};

                function removeQuestion(id){
                    console.log(id)
                    let answerRow = document.getElementById("rowanswer" + id);
                    if(answerRow && answerRow.parentNode) {
                        answerRow.parentNode.removeChild(answerRow);
                    }
                }

                function addQuestion(){

                    questionsCount++;
                    const pos = questionsCount;

                    let answersContainer = document.getElementById("answers-container");

                    let answerRow = document.createElement("div");
                    answerRow.id = "rowanswer" + pos;
                    answerRow.classList.add("row", "row-question");

                    let answerTextCol = document.createElement("div");
                    answerTextCol.classList.add("col");

                    let answerTextarea = document.createElement("textarea");
                    answerTextarea.name = "${DataHolder.PARAMETER_ANSWER_TEXT}" + pos;
                    answerTextarea.id = "textarea" + pos;
                    answerTextarea.rows = 3;
                    answerTextarea.classList.add("form-control","textarea-answer");

                    answerTextCol.appendChild(answerTextarea);

                    let answerDeleteCol = document.createElement("div");
                    answerDeleteCol.classList.add("col-auto");

                    let answerDeleteBtn = document.createElement("button");
                    answerDeleteBtn.classList.add("btn","btn-outline-danger","btn-delete-answer");
                    answerDeleteBtn.type = "button";
                    answerDeleteBtn.innerHTML = '<i class="fas fa-trash"></i>';
                    answerDeleteBtn.onclick = function(){ removeQuestion(pos);};

                    answerDeleteCol.appendChild(answerDeleteBtn);

                    answerRow.appendChild(answerTextCol);
                    answerRow.appendChild(answerDeleteCol);

                    answersContainer.appendChild(answerRow);

                    let inputLastAnswer = document.getElementById("input-last-answer");
                    inputLastAnswer.value = questionsCount;
                }
            </script>

            <button type="button" class="btn btn-custom-fill" onclick="addQuestion()">
                <fmt:message key="button.add"/>
            </button>

        </div>

        <div class="bottom-actions-container">
            <div class="btn-group-custom">
                <button type="submit" formmethod="post" formaction="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.FINISH_EDIT_QUESTION}"
                        class="btn btn-success"><fmt:message
                        key="button.save"/></button>
                <button form="cancelEditQuestionForm" type="submit" class="btn btn-warning">
                    <fmt:message key="button.cancel"/></button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
