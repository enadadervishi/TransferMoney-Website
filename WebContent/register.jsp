<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Register" />
</jsp:include>

<div class="row">
    <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
            <div class="card-body">
                <h5 class="card-title text-center">Register</h5>
                <form class="form-signin" method="post" action="register">
                    <div class="form-label-group">
                        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
                        <label for="username">Username</label>
                    </div>
                    <div class="form-label-group">
                        <input type="text" id="email" name="email" class="form-control" placeholder="Email" required>
                        <label for="email">Email</label>
                    </div>
                    <div class="form-label-group">
                        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                        <label for="password">Password</label>
                    </div>
                    <div class="form-label-group">
                        <input type="password" id="repeat-password" name="repeat-password" class="form-control" placeholder="Repeat Password" required>
                        <label for="repeat-password">Repeat Password</label>
                    </div>

                    <c:if test="${not empty registerError}">
                        <div id="register-error" class="alert alert-danger rounded mr-2" role="alert">${registerError}</div>
                    </c:if>

                    <input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="Create Account">
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                    Already have an account? &nbsp; <a href="login">Sign In</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
