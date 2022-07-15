<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.util.search.SearchParameter" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserStatus" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<c:set var="itemsPerPage" value="4" scope="page"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.users"/></title>
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
                let activeLink = document.getElementById("navUsers");
                activeLink.classList.add("active");
            </script>
        </div>
        <div class="col-9">
            <div class="content-container">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="${CommandType.START_CREATE_USER}">
                    <button type="submit" class="btn btn-custom-fill"><fmt:message key="button.create"/></button>
                </form>

                <div class="search-container">
                    <form action="controller" method="get">
                        <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.SEARCH_USERS}">
                        <div class="form-row row-search">
                            <div class="col">
                                <input type="text" class="form-control input-search" placeholder="<fmt:message key="placeholder.search"/>" name="${DataHolder.REQUEST_ATTRIBUTE_PARAMETER_SEARCH_WORDS}" value="${requestScope.search_words}">
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-search"><i class="fa fa-search"></i></button>
                            </div>
                        </div>
                        <div class="form-row justify-content-md-end row-filter">
                            <div class="col-md-4">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text"><i class="fas fa-user-alt"></i></div>
                                    </div>
                                    <select id="filter_user_role" class="form-control" name="${DataHolder.REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_ROLE}">
                                        <option value="${SearchParameter.DEFAULT_FILTER_STR_ALL}" <c:if test="${requestScope.filter_survey_status == SearchParameter.DEFAULT_FILTER_STR_ALL}">selected</c:if>><fmt:message key="filter.all"/></option>
                                        <option value="${UserRole.ADMIN}" <c:if test="${requestScope.filter_user_role == UserRole.ADMIN.name()}">selected</c:if>><fmt:message key="role.admin"/></option>
                                        <option value="${UserRole.USER}" <c:if test="${requestScope.filter_user_role == UserRole.USER.name()}">selected</c:if>><fmt:message key="role.user"/></option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text"><i class="fas fa-wrench"></i></div>
                                    </div>
                                    <select id="filter_user_status" class="form-control" name="${DataHolder.REQUEST_ATTRIBUTE_PARAMETER_FILTER_USER_STATUS}">
                                        <option value="${SearchParameter.DEFAULT_FILTER_STR_ALL}" <c:if test="${requestScope.filter_survey_status == SearchParameter.DEFAULT_FILTER_STR_ALL}">selected</c:if>><fmt:message key="filter.all"/></option>
                                        <option value="${UserStatus.ACTIVE}" <c:if test="${requestScope.filter_user_status == UserStatus.ACTIVE.name()}">selected</c:if>><fmt:message key="status.user.active"/></option>
                                        <option value="${UserStatus.BANNED}" <c:if test="${requestScope.filter_user_status == UserStatus.BANNED.name()}">selected</c:if>><fmt:message key="status.user.banned"/></option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
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
                    <div class="accordion" id="users">
                        <div id="pagination-page-container">
                            <c:set var="userPage" value="1" scope="page"/>
                            <div id="pagination-page-${userPage}" style="display: none">
                                <c:if test="${requestScope.users != null && requestScope.users.size() > 0}">
                                <c:forEach var="userPageIndex" begin="0" end="${requestScope.users.size() - 1}">
                                <c:set var="user" value="${requestScope.users.get(userPageIndex)}" scope="page"/>
                                <c:if test="${userPageIndex / itemsPerPage >= userPage}">
                            </div>
                            <c:set var="userPage" value="${userPage + 1}"/>
                            <div id="pagination-page-${userPage}" style="display: none">
                                </c:if>
                                <div class="card">
                                    <div class="card-header">
                                        <div class="row justify-content-between">
                                            <div class="col">
                                                <h5>${user.email}</h5>
                                            </div>
                                            <div class="col col-auto">
                                                <form action="controller" method="post">
                                                    <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.START_EDIT_USER}">
                                                    <input type="hidden" name="${DataHolder.PARAMETER_USER_ID}" value="${user.userId}">
                                                    <button class="btn btn-info" type="submit"><i class="fas fa-user-edit"></i></button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:remove var="user" scope="page"/>
                                </c:forEach>
                                </c:if>
                            </div>
                            <c:remove var="userPage" scope="page"/>
                        </div>
                    </div>
                </div>

                <div class="pagination">
                    <ul></ul>
                </div>
                <script>
                    const element = document.querySelector(".pagination ul");
                    let totalPages = Math.max(Math.ceil(${requestScope.users.size() / itemsPerPage}), 1);
                    let page = 1;
                    element.innerHTML = createPagination(totalPages, page);
                </script>
            </div>
        </div>
    </div>


</div>

</body>
</html>
