<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="${sessionScope.localisation}">
<head>
    <title>Ошибка 404</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Zlata Migas">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <style>
        html, body{
            height: 100%;
            margin: 0;
        }
    </style>

</head>
<body>
<div class="container-fluid" style="height: 100%; position: absolute; margin: 0;">

        <div class="row align-items-center" style="height: 100%">
            <div class="col">
                <h3 class="display-4">Упс, страница утеряна!</h3>
                <hr class="my-4">
                <form class="form-inline" action="controller">
                    <input type="hidden" name="command" value="default">
                    <label class="mr-sm-2">Вернуться на главную страницу</label>
                    <button type="submit" class="btn btn-outline-primary my-2 my-sm-0">&#8962;</button>
                </form>
            </div>
            <div class="col">
                <img src="${pageContext.request.contextPath}/static/pict/error_404.png" alt="Страница потеряна">
            </div>
        </div>


</div>
</body>
</html>
