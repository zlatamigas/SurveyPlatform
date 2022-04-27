<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Главная</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Zlata Migas">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Домашняя страница</a>
    <div class="navbar-nav mr-auto mt-2 mt-lg-0"></div>
    <form class="form-inline mt-2 mt-lg-0" action="controller">
        <input type="hidden" name="command" value="logout">
        <label class="mr-sm-2">Администратор ${user}</label>
        <input type="submit" class="btn btn-sm btn-outline-warning my-2 my-sm-0" value="Выход">
    </form>
</nav>

<div class="container">

    <form class="form-inline mt-2 mt-lg-0" action="controller">
        <input type="hidden" name="command" value="find_delayed_orders">
        <label class="mr-sm-2">Вывести список заказов, доставка которых задержана.</label>
        <input type="submit" name="submit" class="btn btn-sm btn-outline-info my-2 my-sm-0" value="&#10140;">
    </form>

    <form class="form-inline mt-2 mt-lg-0" action="controller">
        <input type="hidden" name="command" value="find_delivery_before_date_orders">
        <label class="mr-sm-3">Вывести информацию о заказах, которые должны быть выполнены к заданному сроку.</label>
        <input class="form-control mr-sm-3" type="date" name="before_date" value="2022-04-18">
        <input type="submit" name="submit" class="btn btn-sm btn-outline-info my-3 my-sm-0" value="&#10140;">
    </form>
</div>

</body>
</html>
