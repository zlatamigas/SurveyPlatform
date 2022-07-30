<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="com.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder" %>
<%@ page import="com.zlatamigas.surveyplatform.model.entity.SurveyStatus" %>
<%@ page import="com.zlatamigas.surveyplatform.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<c:set var="itemsPerPage" value="10" scope="page"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.themes.confirmed"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="${pageContext.request.contextPath}/static/js/pagination.js"></script>
</head>

<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container-fluid">

    <div class="row">
        <div class="col-3">
            <jsp:include page="/view/fragment/account_left_navbar.jsp"/>
            <script>
                document.getElementById("collapseTheme").classList.add("show");
                document.getElementById("navThemesConfirmed").classList.add("active");
                document.getElementById("navThemes").setAttribute("disabled", "disabled");
            </script>
        </div>
        <div class="col-9">
            <div class="content-container">

                <button id="showAddTheme" class="btn btn-custom-fill"
                        data-toggle="modal" data-target="#addTheme">
                    <fmt:message key="button.create"/>
                </button>
                <div id="addTheme"
                     class="modal" tabindex="-1"  aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered ">
                        <div class="modal-content">
                            <form action="controller" method="post">
                                <div class="modal-header">
                                    <h5 class="modal-title"><fmt:message key="label.survey.theme"/></h5>
                                    <button type="button" class="close" data-dismiss="modal">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.ADD_THEME}">
                                    <input type="hidden" name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS}" value="<c:out value="${requestScope.search_words}"/>">
                                    <input type="hidden" name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE}" value="${requestScope.order_type}">

                                    <div id="themeValidationFeedback" class="text-danger">
                                        <p>
                                            <c:if test="${requestScope.form_invalid.theme_name != null}">
                                                <fmt:message key="${requestScope.form_invalid.theme_name}"/>
                                            </c:if>
                                            <c:if test="${requestScope.theme_exists != null}">
                                                <fmt:message key="${requestScope.theme_exists}"/>
                                            </c:if>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label><fmt:message key="label.theme.name"/>
                                            <a tabindex="0"
                                                  data-toggle="popover" data-trigger="hover"
                                                  data-content="<fmt:message key="popover.hint.theme.name"/>">
                                            <i class="fas fa-info-circle"></i>
                                        </a>
                                        </label>
                                        <input id="inputThemeName" type="text" name="${AttributeParameterHolder.PARAMETER_THEME_NAME}" class="form-control">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-success"><fmt:message key="button.add"/></button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="button.cancel"/></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <script type="text/javascript">
                    <c:if test="${(requestScope.form_invalid.theme_name != null) || (requestScope.theme_exists != null)}">
                    $(window).on('load', function() {
                        $('#addTheme').modal('show');
                    });
                    </c:if>

                    $('#addTheme').on('hidden.bs.modal', function (event) {
                        $("#inputThemeName").val("");
                        $("#themeValidationFeedback").hide();
                    })
                </script>

                <div class="search-container">
                    <form  id="themesConfirmedSearchForm" action="controller" method="get">
                        <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.THEMES_CONFIRMED}">
                        <div class="form-row row-search">
                            <div class="col">
                                <input type="text" class="form-control" placeholder="<fmt:message key="placeholder.search"/>" name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS}" value="<c:out value="${requestScope.search_words}"/>">
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-search"><i class="fa fa-search"></i></button>
                            </div>
                        </div>
                        <div class="form-row justify-content-md-end row-filter">

                            <div class="col-md-3">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text"><i class="fas fa-sort-amount-down"></i></div>
                                    </div>
                                    <select id="order" class="form-control" name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_ORDER_TYPE}">
                                        <option value="ASC" <c:if test="${requestScope.order_type == 'ASC'}">selected</c:if>><fmt:message key="order.asc"/></option>
                                        <option value="DESC" <c:if test="${requestScope.order_type == 'DESC'}">selected</c:if>><fmt:message key="order.desc"/></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="pagination-page-container">
                    <div id="pagination-page-container">
                        <c:set var="themePage" value="1" scope="page"/>
                        <div id="pagination-page-${themePage}" style="display: none">
                        <c:if test="${requestScope.requested_themes != null && requestScope.requested_themes.size() > 0}">
                            <c:forEach var="themeIndex" begin="0" end="${requestScope.requested_themes.size() - 1}">
                                <c:set var="theme" value="${requestScope.requested_themes.get(themeIndex)}" scope="page"/>
                                <c:if test="${themeIndex / itemsPerPage >= themePage}">
                                    </div>
                                    <c:set var="themePage" value="${themePage + 1}"/>
                                    <div id="pagination-page-${themePage}" style="display: none">
                                </c:if>
                                    <c:choose>
                                        <c:when test="${sessionScope.user.role == UserRole.ADMIN}">
                                            <div class="card">
                                                <div class="card-header">
                                                    <div class="row justify-content-between">
                                                        <div class="col">
                                                            <h5><c:out value="${theme.themeName}"/></h5>
                                                        </div>
                                                        <div class="col col-auto">

                                                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteTheme${theme.themeId}">
                                                                <i class="fas fa-trash"></i>
                                                            </button>

                                                            <div id="deleteTheme${theme.themeId}"
                                                                 class="modal fade" tabindex="-1"  aria-hidden="true">
                                                                <div class="modal-dialog modal-dialog-centered ">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h5 class="modal-title"><fmt:message key="confirm.delete.theme.header"/></h5>
                                                                            <button type="button" class="close" data-dismiss="modal">
                                                                                <span aria-hidden="true">&times;</span>
                                                                            </button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <fmt:message key="confirm.delete.theme"/> <c:out value="${theme.themeName}"/><fmt:message key="confirm.questionmark"/>
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button form="themesConfirmedSearchForm"
                                                                                    formaction="${pageContext.request.contextPath}/controller?command=${CommandType.DELETE_THEME}&${AttributeParameterHolder.PARAMETER_THEME_ID}=${theme.themeId}"
                                                                                    formmethod="post"
                                                                                    type="submit"
                                                                                    class="btn btn-danger">
                                                                                <fmt:message key="button.delete"/>
                                                                            </button>
                                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="button.cancel"/></button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test="${sessionScope.user.role == UserRole.USER}">
                                            <div class="card">
                                                <div class="card-header">
                                                    <h5><c:out value="${theme.themeName}"/></h5>
                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                <c:remove var="theme" scope="page"/>
                                </c:forEach>
                                </c:if>
                                </div>
                        <c:remove var="themePage" scope="page"/>
                        </div>
                </div>

                <div class="pagination">
                    <ul></ul>
                </div>
                <script>
                    const element = document.querySelector(".pagination ul");
                    let totalPages = Math.max(Math.ceil(${requestScope.requested_themes.size() / itemsPerPage}), 1);
                    let page = 1;
                    element.innerHTML = createPagination(totalPages, page);
                </script>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>
