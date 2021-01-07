<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<!-- This java server page is the root element -->
<html ng-app="myApp">
    <head>
    <!-- title of home.jsp, login.jsp ecc ecc -->
        <title>{{ pageTitle }}</title>
        <meta charset="ISO-8859-1">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" type="text/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.5/umd/popper.min.js" type="text/javascript"></script>
        <script src="//cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.7/angular.min.js" type="text/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.7/angular-route.min.js" type="text/javascript"></script>

        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="//cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

		<!-- Root path -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css"/>
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark bg-primary">
        	<!-- Link to app.module.js -->
            <a class="navbar-brand" style="cursor: pointer;" ng-click="showHome()"> 
                <img src="<%=request.getContextPath()%>/images/logo.gif" alt="logo" width="120" height="45">
            </a>

            <form class="form-inline ml-auto" method="post" action="logout">
                <input type="submit" class="btn btn-outline-light" value="Logout">
            </form>
        </nav>
        <div class="container-fluid body-content" ng-view>
        
        </div>


        <script src="<%=request.getContextPath()%>/app/app.module.js"></script>
        <script type="text/javascript">
             app.run(function($rootScope, $location) {
                $rootScope.loggedUser = ${loggedUser};
                console.log('logged in');
             });
        </script>
       
        <script src="<%=request.getContextPath()%>/app/components/home/home.ctrl.js"></script>
        <script src="<%=request.getContextPath()%>/app/components/account/account.ctrl.js"></script>
    </body>
</html>