<%@ page contentType="text/html" language="java" %>
<%@ page import="com.zlatamigas.surveyplatform.controller.command.CommandType" %>
<html>
<head>
    <title>Index</title>
</head>
<body>

<jsp:forward page="controller?command=${CommandType.HOME}"/>

</body>
</html>