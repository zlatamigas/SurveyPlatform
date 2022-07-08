<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.command.CommandType" %>
<%@ page import="epam.zlatamigas.surveyplatform.controller.navigation.DataHolder" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserStatus" %>
<%@ page import="epam.zlatamigas.surveyplatform.model.entity.UserRole" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title><fmt:message key="title.user.edit"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/view/fragment/header.jsp"/>

<div class="container">

    <form id="finishEditUserForm" action="controller" method="post">
        <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_EDIT_USER}">
        <input type="hidden" name="${DataHolder.PARAMETER_USER_ID}" value="${sessionScope.edited_user.userId}">

        <div class="content-container">
            <div class="padding-container">

                <h1 class="header-text"><fmt:message key="edituser.header"/></h1>
                <hr class="my-4">

                <div class="row align-items-center form-group">
                    <div class="col-3">
                        <p class="card-text"><fmt:message key="label.email"/></p>
                    </div>
                    <div class="col">
                        <p class="form-control">${sessionScope.edited_user.email}</p>
                    </div>
                </div>
                <div class="row align-items-center form-group">
                    <div class="col-3">
                        <p class="card-text"><fmt:message key="label.user.role"/></p>
                    </div>
                    <div class="col">
                            <select name="${DataHolder.PARAMETER_USER_ROLE}" class="form-control">
                                <option value="${UserRole.ADMIN}" <c:if test="${sessionScope.edited_user.role == UserRole.ADMIN}">selected</c:if>>
                                    <fmt:message key="role.admin"/>
                                </option>
                                <option value="${UserRole.USER}" <c:if test="${sessionScope.edited_user.role == UserRole.USER}">selected</c:if>>
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
                        <select name="${DataHolder.PARAMETER_USER_STATUS}" class="form-control">
                            <option value="${UserStatus.ACTIVE}" <c:if test="${sessionScope.edited_user.status == UserStatus.ACTIVE}">selected</c:if>>
                                <fmt:message key="status.user.active"/>
                            </option>
                            <option value="${UserStatus.BANNED}" <c:if test="${sessionScope.edited_user.status == UserStatus.BANNED}">selected</c:if>>
                                <fmt:message key="status.user.banned"/>
                            </option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

    </form>
    <form id="cancelEditUserForm" action="controller" method="post">
        <input type="hidden" name="${DataHolder.PARAMETER_COMMAND}" value="${CommandType.CANCEL_EDIT_USER}">
    </form>

    <div class="bottom-actions-container">
        <div class="btn-group-custom">
            <button form="finishEditUserForm" type="submit" class="btn btn-success"><fmt:message key="button.save"/></button>
            <button form="cancelEditUserForm" type="submit" class="btn btn-warning"><fmt:message key="button.cancel"/></button>
        </div>
    </div>
</div>

</body>
</html>
