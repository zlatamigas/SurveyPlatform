<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<c:set var="itemsPerPage" value="10" scope="page"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.surveys.all"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="${pageContext.request.contextPath}/static/js/pagination.js"></script>
</head>

<body>

<jsp:include page="/view/fragment/header.jsp"/>
<script>
    let activeLink = document.getElementById("navSurveys");
    activeLink.classList.add("active");
</script>

<div class="container">

    <div class="content-container">

        <div class="search-container">
            <form action="controller" method="get">
                <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.SURVEYS}">
                <div class="form-row row-search">
                    <div class="col">
                        <input type="text" class="form-control input-search" placeholder="<fmt:message key="placeholder.search"/>" name="${DataHolder.REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS}" value="${requestScope.search_words}">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-search"><i class="fa fa-search"></i></button>
                    </div>
                </div>
                <div class="form-row justify-content-md-end row-filter">
                    <div class="col-md-6">
                        <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="input-group-text"><i class="fas fa-filter"></i></div>
                    </div>
                    <select id="theme" class="form-control" name="${DataHolder.REQUEST_ATTRIBUTE_PARAMETER_FILTER_THEME_ID}">
                        <option value="0" <c:if test="${requestScope.filter_theme_id == 0}">selected</c:if>><fmt:message key="filter.all"/></option>
                        <option value="-1" <c:if test="${requestScope.filter_theme_id == -1}">selected</c:if>><fmt:message key="filter.none"/></option>
                        <c:forEach items="${requestScope.requested_themes}" var="theme">
                            <option value="${theme.themeId}" <c:if test="${requestScope.filter_theme_id == theme.themeId}">selected</c:if>>${theme.themeName}</option>
                        </c:forEach>
                    </select>
                </div>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="input-group-text"><i class="fas fa-sort-amount-down"></i></div>
                    </div>
                    <select id="order" class="form-control" name="${DataHolder.REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE}">
                        <option value="ASC" <c:if test="${requestScope.order_type == 'ASC'}">selected</c:if>><fmt:message key="order.asc"/></option>
                        <option value="DESC" <c:if test="${requestScope.order_type == 'DESC'}">selected</c:if>><fmt:message key="order.desc"/></option>
                    </select>
                </div>
                    </div>
                </div>
            </form>
        </div>

        <div class="pagination-page-container">
            <div class="accordion" id="allSurveys">

                <div id="pagination-page-container">

                <c:set var="surveyPage" value="1" scope="page"/>
                    <div id="pagination-page-${surveyPage}" style="display: none">
                    <c:if test="${requestScope.surveys != null && requestScope.surveys.size() > 0}">
                    <c:forEach var="surveyIndex" begin="0" end="${requestScope.surveys.size() - 1}">
                        <c:set var="survey" value="${requestScope.surveys.get(surveyIndex)}" scope="page"/>
                        <c:if test="${surveyIndex / itemsPerPage >= surveyPage}">
                            </div>
                            <c:set var="surveyPage" value="${surveyPage + 1}"/>
                            <div id="pagination-page-${surveyPage}" style="display: none">
                        </c:if>
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

                                        <div class="btn-toolbar justify-content-end" role="toolbar">
                                        <form id="startAttemptSurveyForm${survey.surveyId}" action="controller" method="get">
                                            <input type="hidden" name="command" value="${CommandType.START_SURVEY_ATTEMPT}">
                                            <input type="hidden" name="${DataHolder.PARAMETER_SURVEY_ID}" value="${survey.surveyId}">
                                            <button form="startAttemptSurveyForm${survey.surveyId}" type="submit" class="btn btn-outline-success">
                                                <i class="fas fa-play"></i></button>
                                        </form>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        <c:remove var="survey" scope="page"/>
                    </c:forEach>
                    </c:if>
                    </div>
                <c:remove var="surveyPage" scope="page"/>

                </div>
            </div>
        </div>

        <div class="pagination">
            <ul></ul>
        </div>

        <script>
            const element = document.querySelector(".pagination ul");
            let totalPages = Math.max(Math.ceil(${requestScope.surveys.size() / itemsPerPage}), 1);
            let page = 1;
            element.innerHTML = createPagination(totalPages, page);
        </script>
        <c:remove var="surveys" scope="request"/>

    </div>
</div>

</body>
</html>
