<%--
  Created by IntelliJ IDEA.
  User: zlata
  Date: 29.03.2022
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
</head>
<body>
<p>Request from: ${pageContext.errorData.requestURI}</p>
<p>Servlet name: ${pageContext.errorData.servletName}</p>
<p>Status code:  ${pageContext.errorData.statusCode}</p>
<p>Exception:    ${pageContext.exception}</p>
</body>
</html>
