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

    <div>
        <form action="controller" method="get">
            <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.SEARCH_SURVEYS}">
            <div class="form-row">
                <div class="col">
                    <input type="text" class="form-control" placeholder="Search.." name="${DataHolder.PARAMETER_ATTRIBUTE_SEARCH_WORDS}" value="${sessionScope.search_words}">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
                </div>
                <div class="col-auto">
                    <button type="button" class="btn btn-warning" onclick=""><i class="fas fa-trash-alt"></i></button>
                </div>
            </div>
            <div class="form-row justify-content-md-end">
                <div class="col-md-6">
                    <div class="input-group">
                <div class="input-group-prepend">
                    <div class="input-group-text"><i class="fas fa-filter"></i></div>
                </div>
                <select id="theme" class="form-control" name="${DataHolder.PARAMETER_ATTRIBUTE_FILTER_THEME_ID}">
                    <option value="0" <c:if test="${sessionScope.filter_theme_id == 0}">selected</c:if>><fmt:message key="surveys.themes.all"/></option>
                    <c:forEach items="${sessionScope.themes}" var="theme">
                        <option value="${theme.themeId}" <c:if test="${sessionScope.filter_theme_id == theme.themeId}">selected</c:if>>${theme.themeName}</option>
                    </c:forEach>
                </select>
            </div>
                </div>
                <div class="col-md-3">
                    <div class="input-group">
                <div class="input-group-prepend">
                    <div class="input-group-text"><i class="fas fa-sort-amount-down"></i></div>
                </div>
                <select id="order" class="form-control" name="${DataHolder.PARAMETER_ATTRIBUTE_ORDER_TYPE}">
                    <option value="ASC" <c:if test="${sessionScope.order_type == 'ASC'}">selected</c:if>><fmt:message key="surveys.order.az"/></option>
                    <option value="DESC" <c:if test="${sessionScope.order_type == 'DESC'}">selected</c:if>><fmt:message key="surveys.order.za"/></option>
                </select>
            </div>
                </div>
            </div>
        </form>
    </div>


    <div class="accordion" id="allSurveys">


        <c:forEach items="${sessionScope.surveys_page}" var="survey">

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

    <c:set var="forbidPrevious" value="${sessionScope.pagination_current_page <= 0}"/>
    <c:set var="forbidNext" value="${(sessionScope.pagination_current_page + 1) * DataHolder.SURVEYS_PER_PAGE >= sessionScope.surveys.size()}"/>

    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-end">
            <li class="page-item <c:if test="${forbidPrevious}">disabled</c:if>">

                <a class="page-link" href="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.PAGINATE_SURVEYS}&${DataHolder.PARAMETER_PAGINATION_PAGE_OFFSET}=${sessionScope.pagination_current_page - 1}"
                <c:if test="${forbidPrevious}">
                   tabindex="-1" aria-disabled="true"
                </c:if>
                >Previous</a>
            </li>
            <li class="page-item  <c:if test="${forbidNext}">disabled</c:if>">

                <a class="page-link" href="controller?${DataHolder.PARAMETER_COMMAND}=${CommandType.PAGINATE_SURVEYS}&${DataHolder.PARAMETER_PAGINATION_PAGE_OFFSET}=${sessionScope.pagination_current_page + 1}"
                <c:if test="${forbidNext}">
                    tabindex="-1" aria-disabled="true"
                </c:if>
                >Next</a>
            </li>
        </ul>
    </nav>
</div>

</body>
</html>
