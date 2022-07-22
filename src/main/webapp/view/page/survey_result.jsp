<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ taglib uri="/WEB-INF/tld/customtag.tld" prefix="ct"%>


<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.survey.result"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- Google Charts JS --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/lib/gstatic-charts/loader.js"></script>
</head>
<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container">

    <div class="content-container">
        <div class="padding-container">
            <h1 class="header-text">${requestScope.survey_result.name}</h1>
            <h5 class="subheader-text">${requestScope.survey_result.theme.themeName}</h5>
            <h5 class="time-text">
                <ct:local-date-time datetime="${requestScope.survey_result.startDateTime}"/> -
                    <ct:local-date-time datetime="${requestScope.survey_result.closeDateTime}"/></h5>

            <c:if test="${requestScope.survey_result.description != ''}">
                <div class="description-text">${requestScope.survey_result.description}</div>
            </c:if>
        </div>
    </div>

    <div class="content-container">
        <div class="edit-question-list">
            <c:forEach var="question" items="${requestScope.survey_result.questions}">
                <div class="card">
                    <div class="card-header">
                        <div class="row justify-content-between">
                            <div class="col">
                                <h5 class="card-title">${question.formulation}</h5>
                            </div>
                            <div class="col col-auto">
                                <div class="btn-group" role="group">
                                    <div class="nav nav-pills"  role="tablist">
                                        <a class="btn btn-outline-success active" id="pills-question${question.questionId}-chart-tab" data-toggle="pill" href="#pills-question${question.questionId}-chart" role="tab" aria-controls="pills-question${question.questionId}-chart" aria-selected="true"><i class="fas fa-chart-pie"></i></a>
                                        <a class="btn btn-outline-primary" id="pills-question${question.questionId}-text-tab" data-toggle="pill" href="#pills-question${question.questionId}-text" role="tab" aria-controls="pills-question${question.questionId}-text" aria-selected="false"><i class="fas fa-list-ol"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">

                        <div class="tab-content">
                            <div class="tab-pane fade show active" id="pills-question${question.questionId}-chart" role="tabpanel" aria-labelledby="pills-question${question.questionId}-chart-tab">
                                <c:choose>
                                    <c:when test="${question.selectMultiple == true}">
                                        <div id="questionChartCheckbox${question.questionId}"></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="question-result-chart-radio-wrap">
                                            <div id="questionChartRadio${question.questionId}" class="question-result-chart-radio"></div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="tab-pane fade"
                                 id="pills-question${question.questionId}-text" role="tabpanel"
                                 aria-labelledby="pills-question${question.questionId}-text-tab">

                                <ct:survey-question-result question="${question}">
                                </ct:survey-question-result>
                            </div>
                        </div>


                    </div>
                </div>
            </c:forEach>
            <script type="text/javascript">
                google.charts.load("current", {packages: ["corechart", "bar"]});
                google.charts.setOnLoadCallback(drawCharts);

                function drawCharts() {

                    let answers, data, options, chart;
                    let titles, values;

                    <c:forEach var="question" items="${requestScope.survey_result.questions}">
                    <c:choose>
                    <c:when test="${question.selectMultiple == true}">
                    titles = [''];
                    values = [''];

                    <c:forEach var="answer" items="${question.answers}">
                    titles.push(`${answer.answer}`);
                    values.push(${answer.selectedCount});
                    </c:forEach>

                    answers = [titles, values];
                    data = google.visualization.arrayToDataTable(answers);
                    options = {
                        width: '100%',
                        legend: {position: 'bottom'},
                        bar: {groupWidth: "95%"}
                    };
                    chart = new google.visualization.ColumnChart(document.getElementById('questionChartCheckbox${question.questionId}'));
                    chart.draw(data, options);
                    </c:when>
                    <c:otherwise>
                    answers = [['Answer', 'Selected count']];
                    <c:forEach var="answer" items="${question.answers}">
                    answers.push(['${answer.answer}', ${answer.selectedCount}]);
                    </c:forEach>

                    data = google.visualization.arrayToDataTable(answers);
                    options = {
                        width: '100%',
                        height: '100%',
                        chartArea: {
                            width: "100%"
                        },
                        pieHole: 0.4,
                        legend: {
                            position: 'bottom',
                            width:'100%',
                        }
                    };

                    chart = new google.visualization.PieChart(document.getElementById('questionChartRadio${question.questionId}'));
                    chart.draw(data, options);
                    </c:otherwise>
                    </c:choose>

                    </c:forEach>
                }

                window.onresize = drawCharts;
            </script>
        </div>
    </div>

    <div class="bottom-actions-container">
        <div class="btn-group-custom">
            <a href="${pageContext.request.contextPath}/controller?command=${CommandType.USER_SURVEYS}"
               class="btn btn-warning"><fmt:message key="button.back"/></a>
        </div>
    </div>
</div>

</body>
</html>
