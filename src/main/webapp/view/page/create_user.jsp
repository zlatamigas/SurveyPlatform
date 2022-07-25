<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.AttributeParameterHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserStatus" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.user.add"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container">

    <div class="content-container">
        <div class="padding-container">

            <h1 class="header-text"><fmt:message key="createuser.header"/></h1>
            <hr class="my-4">

            <div class="text-danger">
                <c:if test="${requestScope.user_exists!=null}">
                    <fmt:message key="${requestScope.user_exists}"/>
                </c:if>
            </div>

            <form id="finishCreateUserForm" action="controller" method="post">
                <input type="hidden" name="command" value="${CommandType.FINISH_CREATE_USER}">
                <div class="form-group">
                    <div class="text-danger">
                        <c:if test="${requestScope.form_invalid.email!=null}">
                            <fmt:message key="${requestScope.form_invalid.email}"/>
                        </c:if>
                    </div>
                    <label><fmt:message key="label.email"/>
                        <a tabindex="0"
                           data-toggle="popover" data-trigger="hover"
                           data-content="<fmt:message key="popover.hint.email"/>">
                            <i class="fas fa-info-circle"></i>
                        </a>
                    </label>
                    <input type="text" class="form-control" name="${AttributeParameterHolder.PARAMETER_EMAIL}" placeholder="<fmt:message key="placeholder.email"/>">
                </div>
                <div class="form-group">
                    <div class="text-danger">
                        <c:if test="${requestScope.form_invalid.password!=null}">
                            <fmt:message key="${requestScope.form_invalid.password}"/>
                        </c:if>
                    </div>
                    <label><fmt:message key="label.password"/>
                        <a tabindex="0"
                           data-toggle="popover" data-trigger="hover"
                           data-content="<fmt:message key="popover.hint.password"/>">
                            <i class="fas fa-info-circle"></i>
                        </a>
                    </label>
                    <input type="password" class="form-control" name="${AttributeParameterHolder.PARAMETER_PASSWORD}" placeholder="<fmt:message key="placeholder.email"/>">
                </div>
                <div class="form-group">
                    <div class="text-danger">
                        <c:if test="${requestScope.form_invalid.password_repeat!=null}">
                            <fmt:message key="${requestScope.form_invalid.password_repeat}"/>
                        </c:if>
                    </div>
                    <label><fmt:message key="label.password.repeat"/>
                        <a tabindex="0"
                           data-toggle="popover" data-trigger="hover"
                           data-content="<fmt:message key="popover.hint.password"/>">
                            <i class="fas fa-info-circle"></i>
                        </a>
                    </label>
                    <input type="password" class="form-control" name="${AttributeParameterHolder.PARAMETER_PASSWORD_REPEAT}" placeholder="<fmt:message key="placeholder.password.repeat"/>">
                </div>
                <div class="form-row">
                    <div class="col form-group">
                        <label><fmt:message key="label.user.role"/> </label>
                        <select name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_USER_ROLE}" class="form-control">
                            <option value="${UserRole.ADMIN}" >
                                <fmt:message key="role.admin"/>
                            </option>
                            <option value="${UserRole.USER}" selected>
                                <fmt:message key="role.user"/>
                            </option>
                        </select>
                    </div>
                    <div class="col form-group">
                        <label><fmt:message key="label.user.status"/> </label>
                        <select name="${AttributeParameterHolder.REQUEST_ATTRIBUTE_PARAMETER_USER_STATUS}" class="form-control">
                            <option value="${UserStatus.ACTIVE}" selected>
                                <fmt:message key="status.user.active"/>
                            </option>
                            <option value="${UserStatus.BANNED}">
                                <fmt:message key="status.user.banned"/>
                            </option>
                        </select>
                    </div>
                </div>
            </form>

            <form id="cancelCreateUserForm" action="controller" method="get">
                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.USERS}">
            </form>
        </div>
    </div>

    <div class="bottom-actions-container">
        <div class="btn-group-custom">
            <button form="finishCreateUserForm" type="submit" class="btn btn-success"><fmt:message key="button.create"/></button>
            <button form="cancelCreateUserForm" type="submit" class="btn btn-warning"><fmt:message key="button.cancel"/></button>
        </div>
    </div>
</div>

<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>
