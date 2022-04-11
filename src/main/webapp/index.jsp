<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebApp</title>
</head>
<body>
<h1>Main page loaded</h1>
<br/>
<%--<a href="controller">Hello Servlet</a>--%>
<form action="controller">
    <input type="hidden" name="command" value="login">
    Login:<input type="text" name="login">
    Password:<input type="password" name="password">
    <input type="submit" name="submit" value="Push">
</form>
<p>${login_msg}</p>
<p>${pageContext.getSession().getId()}</p>
<p>${pageContext.session.id}</p>
<p>${forward_filter_attr}</p>
</body>
</html>