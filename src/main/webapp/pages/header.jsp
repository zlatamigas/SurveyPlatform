<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#"><fmt:message key="header.navbar.homepage"/></a>
        <div class="navbar-nav mr-auto mt-2 mt-lg-0"></div>

        <c:if test="${sessionScope.user != null}">
        <form class="form-inline mt-2 mt-lg-0" action="controller">
            <input type="hidden" name="command" value="logout">
            <label class="mr-sm-2">${user}</label>
            <button type="submit" class="btn btn-sm btn-outline-warning my-2 my-sm-0"><fmt:message key="header.navbar.logout"/></button>
        </form>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <form class="form-inline mt-2 mt-lg-0" action="controller">
                <input type="hidden" name="command" value="start_authentication">
                <button type="submit" class="btn btn-sm btn-outline-primary"><fmt:message key="header.navbar.login"/></button>
            </form>
        </c:if>
    </nav>
</header>
