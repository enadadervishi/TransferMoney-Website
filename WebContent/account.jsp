<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Account" />
</jsp:include>

<div class="row padding">
    <div class="col-12 mb-3">
        <div class="card">
            <div class="card-body">
                <div class="float-right">
                    <form method="post" action="deleteAccount">
                        <input type="hidden" id="accountId" name="accountId" value="${accountId}">
                        <input type="submit" class="btn btn-danger" value="Delete">
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
                            <td>Account ID</td>
                            <td>${accountId}</td>
                        </tr>
                        <tr>
                            <td>Balance</td>
                            <td>${accountBalance}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="col-12 mb-3">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title text-center">Transfer</h5>
                <form class="form-signin" method="post" action="transfer">
                    <div class="form-label-group">
                        <input type="text" id="destinationUserId" name="destinationUserId" class="form-control" placeholder="Recipient User Id" required autofocus>
                        <label for="destinationUserId">Recipient User Id</label>
                    </div>
                    <div class="form-label-group">
                        <input type="text" id="destinationAccountId" name="destinationAccountId" class="form-control" placeholder="Recipient Account Id" required>
                        <label for="destinationAccountId">Recipient Account Id</label>
                    </div>
                    <div class="form-label-group">
                        <input type="text" id="amount" name="amount" class="form-control" placeholder="Amount" required>
                        <label for="amount">Amount</label>
                    </div>
                    <c:if test="${not empty transferError}">
                        <div id="transfer-error" class="alert alert-danger rounded mr-2" role="alert">${transferError}</div>
                    </c:if>

                    <input type="hidden" id="accountId" name="accountId" value="${accountId}">

                    <input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="Transfer">
                </form>
            </div>
        </div>
    </div>

    <div class="col-12">
        <table class="table">
            <thead>
                <th scope="col">Time:</th>
                <th scope="col">Account:</th>
                <th scope="col">Amount:</th>
                <th scope="col">Direction:</th>
            </thead>
            <tbody>
            <c:forEach items="${transfers}" var="transfer">
                <tr>
                    <td>${transfer.getDateTimeString()}</td>
                    <c:choose>
                        <c:when test="${transfer.sourceAccountId == accountId}">
                            <td>${transfer.destinationAccountId}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${transfer.sourceAccountId}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${transfer.amount}</td>
                    <c:choose>
                        <c:when test="${transfer.sourceAccountId == accountId}">
                            <td>Outgoing</td>
                        </c:when>
                        <c:otherwise>
                            <td>Incoming</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="footer.jsp"/>
