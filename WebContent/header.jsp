<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title><%=request.getParameter("title")%></title>
        <meta charset="ISO-8859-1">

        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/app.css"/>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css"/>
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark bg-primary">
            <a class="navbar-brand" style="cursor: pointer;" href="home">
                <img src="<%=request.getContextPath()%>/images/logo.gif" alt="logo" width="120" height="45">
            </a>

            <c:if test="${not empty loggedUser}">
                <form class="form-inline ml-auto" method="post" action="logout">
                    <input type="submit" class="btn btn-outline-light" value="Logout">
                </form>
            </c:if>
        </nav>
        <div class="container-fluid body-content">
