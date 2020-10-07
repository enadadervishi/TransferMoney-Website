<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>

<div class="row padding">
    <div class="col-12 mb-3">
        <div class="card">
            <div class="card-body">
                <div class="float-right">
                    <form method="post" action="newAccount">
                        <input type="submit" class="btn btn-primary" value="Add Account">
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="col-12 mb-5">
        <div class="card">
            <div class="card-body">
                <table class="table">
                    <tbody>
                        <tr>
                            <td>User ID</td>
                            <td>${loggedUser}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="col-12">
        <div id="boards-container" class="card-columns">
            <c:forEach items="${accounts}" var="account">
                <div class="card bg-primary" onclick="location.href='account?id=${account.id}';">
                    <div class="card-body" style="cursor: pointer;">
                        <h6 class="side-by-side-header">${account.id}</h6>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
