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
    <title><fmt:message key="title.surveyresult"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- Google Charts JS --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/lib/gstatic-charts/loader.js"></script>
</head>
<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container">

    <div class="card">
        <div class="card-body">
            <h3 class="card-title">${sessionScope.survey_result.name}</h3>
            <hr>
            <h5 class="card-subtitle">${sessionScope.survey_result.theme.themeName}</h5>
            <div class="cart-text">${sessionScope.survey_result.description}</div>
        </div>

    </div>


    <c:forEach var="question" items="${sessionScope.survey_result.questions}">
        <div class="card">
            <div class="card-body">
                <p class="card-title">${question.formulation}</p>

                <c:choose>
                    <c:when test="${question.selectMultiple == true}">
                        <div id="questionChartCheckbox${question.questionId}"></div>
                    </c:when>
                    <c:otherwise>
                        <div style="position: relative; padding-bottom: 100%; height: 0; overflow:hidden;">
                            <div id="questionChartRadio${question.questionId}"
                                 style="position: absolute; top: 0; left: 0; width:100%; height:100%;"
                            ></div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:forEach>


    <script type="text/javascript">
        google.charts.load("current", {packages: ["corechart", "bar"]});
        google.charts.setOnLoadCallback(drawCharts);

        function drawCharts() {

            let answers, data, options, chart;
            let titles, values;

            <c:forEach var="question" items="${sessionScope.survey_result.questions}">
            <c:choose>
            <c:when test="${question.selectMultiple == true}">
            titles = [''];
            values = [''];

            <c:forEach var="answer" items="${question.answers}">
            titles.push('${answer.answer}');
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

</body>
</html>
