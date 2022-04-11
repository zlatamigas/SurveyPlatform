<%--
  Created by IntelliJ IDEA.
  User: zlata
  Date: 29.03.2022
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>User (forward) = ${user}</p>
<p>User (redirect/forward) = ${user_name}</p>
<form action="controller">
    <input type="hidden" name="command" value="logout">
    <input type="submit" value="Log out">
</form>
<p>${forward_filter_attr}</p>
</body>
</html>
