<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Login" />
</jsp:include>

<div class="row">
    <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
            <div class="card-body">
                <h5 class="card-title text-center">Sign In</h5>
                <form class="form-signin" method="post" action="login">
                    <div class="form-label-group">
                        <input type="text" id="username" name="username" class="form-control" placeholder="Username" value="${username}" required autofocus>
                        <label for="username">Username</label>
                    </div>

                    <div class="form-label-group">
                        <input type="password" id="password" name="password" class="form-control" placeholder="Password" value="${password}" required>
                        <label for="password">Password</label>
                    </div>

                    <c:if test="${not empty loginError}">
                        <div id="login-error" class="alert alert-danger rounded mr-2" role="alert">${loginError}</div>
                    </c:if>

                    <input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="Sign in">
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                    Don't have an account? &nbsp; <a href="register">Sign Up</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
