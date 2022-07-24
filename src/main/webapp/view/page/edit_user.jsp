<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserStatus" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.User" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.Survey" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.util.search.SearchParameter" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.SurveyStatus" %>
<%@ taglib uri="/WEB-INF/tld/customtag.tld" prefix="ct" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.user.edit"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script>
        function enableEditUser() {
            let finishEditBtn = document.getElementById("finishEditUserButton");
            finishEditBtn.style.display = "inline-block";
            finishEditBtn.disabled = false;

            document.getElementById("selectRole").disabled = false;
            document.getElementById("selectStatus").disabled = false;

            let enableEditBtn = document.getElementById("enableEditUserButton");
            enableEditBtn.style.display = "none";
            enableEditBtn.disabled = true;
        }
    </script>
</head>
<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container">

    <div class="content-container">
        <div class="padding-container">
            <form id="finishEditUserForm" action="controller" method="post">
                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}"
                       value="${CommandType.FINISH_EDIT_USER}">
                <input type="hidden" name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_USER_ID}"
                       value="${requestScope.user_id}">

                <h1 class="header-text"><fmt:message key="edituser.header"/></h1>
                <hr class="my-4">

                <div class="row align-items-center form-group">
                    <div class="col-3">
                        <p class="card-text"><fmt:message key="label.email"/></p>
                    </div>
                    <div class="col">
                        <p class="form-control">${requestScope.user_email}</p>
                    </div>
                </div>
                <div class="row align-items-center form-group">
                    <div class="col-3">
                        <p class="card-text"><fmt:message key="label.user.role"/></p>
                    </div>
                    <div class="col">
                        <select id="selectRole" disabled
                                name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_USER_ROLE}"
                                class="form-control">
                            <option value="${UserRole.ADMIN}"
                                    <c:if test="${requestScope.user_role == UserRole.ADMIN}">selected</c:if>>
                                <fmt:message key="role.admin"/>
                            </option>
                            <option value="${UserRole.USER}"
                                    <c:if test="${requestScope.user_role == UserRole.USER}">selected</c:if>>
                                <fmt:message key="role.user"/>
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row align-items-center form-group">
                    <div class="col-3">
                        <p class="card-text"><fmt:message key="label.user.status"/></p>
                    </div>
                    <div class="col">
                        <select id="selectStatus" disabled
                                name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_USER_STATUS}"
                                class="form-control">
                            <option value="${UserStatus.ACTIVE}"
                                    <c:if test="${requestScope.user_status == UserStatus.ACTIVE}">selected</c:if>>
                                <fmt:message key="status.user.active"/>
                            </option>
                            <option value="${UserStatus.BANNED}"
                                    <c:if test="${requestScope.user_status == UserStatus.BANNED}">selected</c:if>>
                                <fmt:message key="status.user.banned"/>
                            </option>
                        </select>
                    </div>
                </div>

                <div class="bottom-actions-container justify-content-end">
                    <button id="finishEditUserButton" disabled style="display: none"
                            form="finishEditUserForm" type="submit" class="btn btn-success">
                        <fmt:message key="button.save"/></button>
                    <button id="enableEditUserButton" type="button" class="btn btn-outline-custom"
                            onclick="enableEditUser();">
                        <fmt:message key="button.edit"/>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="content-container">
        <div class="padding-container">
            <h3 class="text-danger"><i class="fas fa-user-alt-slash"></i> <fmt:message
                    key="confirm.delete.user.header"/></h3>
            <hr class="my-4"/>
            <p><fmt:message key="confirm.delete.user.description"/></p>

            <button type="button"
                    data-toggle="modal" data-target="#deleteUser"
                    class="btn btn-danger btn-delete-user">
                <fmt:message key="button.delete"/>
            </button>
            <div id="deleteUser"
                 class="modal fade" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title"><fmt:message
                                    key="confirm.delete.user.header"/></h5>
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <fmt:message key="confirm.delete.user"/> ${requestScope.user_email}
                            <fmt:message key="confirm.questionmark"/>
                            <fmt:message key="confirm.delete.user.description"/>
                        </div>
                        <div class="modal-footer">
                            <form action="controller" method="post">
                                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}"
                                       value="${CommandType.DELETE_USER}">
                                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_USER_ID}"
                                       value="${requestScope.user_id}">
                                <button type="submit" class="btn btn-danger">
                                    <fmt:message key="button.delete"/>
                                </button>
                            </form>

                            <button type="button" class="btn btn-secondary"
                                    data-dismiss="modal"><fmt:message
                                    key="button.cancel"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${requestScope.user_surveys.size() > 0}">
        <div class="content-container">
            <div class="pagination-page-container">

                <c:forEach var="survey" items="${requestScope.user_surveys}">
                    <div class="card">
                        <div class="card-header">
                            <div class="row justify-content-between">
                                <div class="col">
                                    <h5 class="card-title">${survey.name}</h5>
                                </div>
                                <div class="col col-auto">

                                    <button type="button"
                                            data-toggle="modal" data-target="#deleteSurvey${survey.surveyId}"
                                            class="btn btn-outline-danger">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                    <div id="deleteSurvey${survey.surveyId}"
                                         class="modal fade" tabindex="-1" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered ">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title"><fmt:message
                                                            key="confirm.delete.survey.header"/></h5>
                                                    <button type="button" class="close" data-dismiss="modal">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <fmt:message key="confirm.delete.survey"/> ${survey.name}
                                                    <fmt:message key="confirm.questionmark"/>
                                                </div>
                                                <div class="modal-footer">
                                                    <form action="controller" method="post">
                                                        <input type="hidden"
                                                               name="${AttributeParameterHolder.PARAMETER_COMMAND}"
                                                               value="${CommandType.ADMIN_DELETE_SURVEY}">
                                                        <input type="hidden"
                                                               name="${AttributeParameterHolder.PARAMETER_SURVEY_ID}"
                                                               value="${survey.surveyId}">
                                                        <input type="hidden"
                                                               name="${AttributeParameterHolder.PARAMETER_USER_ID}"
                                                               value="${survey.creator.userId}">
                                                        <button type="submit" class="btn btn-danger">
                                                            <fmt:message key="button.delete"/>
                                                        </button>
                                                    </form>

                                                    <button type="button" class="btn btn-secondary"
                                                            data-dismiss="modal"><fmt:message
                                                            key="button.cancel"/></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <h5 class="card-subtitle mb-3 text-muted">${survey.theme.themeName}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">
                                <c:choose>
                                    <c:when test="${survey.status == SurveyStatus.NOT_STARTED}">
                                        <fmt:message key="status.survey.notstarted"/>
                                    </c:when>
                                    <c:when test="${survey.status == SurveyStatus.STARTED}">
                                        <fmt:message key="status.survey.started"/>
                                    </c:when>
                                    <c:when test="${survey.status == SurveyStatus.CLOSED}">
                                        <fmt:message key="status.survey.closed"/>
                                    </c:when>
                                </c:choose>
                            </h6>

                            <c:choose>
                                <c:when test="${survey.status == SurveyStatus.STARTED}">
                                    <p class="card-subtitle mb-2 text-muted"><fmt:message
                                            key="label.survey.startdatetime"/> <ct:local-date-time
                                            datetime="${survey.startDateTime}"/></p>
                                </c:when>
                                <c:when test="${survey.status == SurveyStatus.CLOSED}">
                                    <p class="card-subtitle mb-2 text-muted"><fmt:message
                                            key="label.survey.startdatetime"/> <ct:local-date-time
                                            datetime="${survey.startDateTime}"/></p>
                                    <p class="card-subtitle mb-2 text-muted"><fmt:message
                                            key="label.survey.closedatetime"/> <ct:local-date-time
                                            datetime="${survey.closeDateTime}"/></p>
                                </c:when>
                            </c:choose>

                            <p class="card-text">${survey.description}</p>
                            <div class="btn-toolbar justify-content-end" role="toolbar">

                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </c:if>


    <div class="bottom-actions-container">
        <div class="btn-group-custom">
            <a href="${pageContext.request.contextPath}/controller?command=${CommandType.USERS}"
               class="btn btn-warning"><fmt:message key="button.back"/></a>
        </div>
    </div>
</div>

<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>
