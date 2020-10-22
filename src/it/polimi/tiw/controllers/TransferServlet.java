package it.polimi.tiw.controllers;

import com.google.gson.Gson;
import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.exceptions.OperationException;
import it.polimi.tiw.exceptions.PermissionException;
import it.polimi.tiw.models.Account;
import it.polimi.tiw.models.Transfer;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.ErrorResult;
import it.polimi.tiw.operation.JsonResult;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RequestData;
import it.polimi.tiw.operation.RequestPath;
import it.polimi.tiw.operation.transfer.TransferData;
import it.polimi.tiw.operation.transfer.TransferOperation;
import it.polimi.tiw.operation.transfer.TransferResult;
import it.polimi.tiw.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TransferServlet", urlPatterns = {"/transfer/*"})
public class TransferServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private ControllerOperations operations = null;

    public void init() throws ServletException {
        operations = ControllerOperations.createDefault();
    }

    public void destroy() {
        Closeables.closeSilently(operations);
    }

    // GET
    // transfer/id
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            RequestPath requestPath = new RequestPath(request);
            if (requestPath.getPathCount() != 2) {
                return new ErrorResult(HttpServletResponse.SC_BAD_REQUEST, "expected 2 params: " + requestPath);
            }

            long accountId = requestPath.getLongParam(1);
            Account account = database.getAccounts().selectById(accountId);
            auth.checkAccountAccess(account);

            List<Transfer> transfers = database.getTransfers().selectByAccountIdDescending(accountId);
            return new JsonResult(gson, transfers);
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }

    // POST
    // transfer/id
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            try {
                RequestPath requestPath = new RequestPath(request);
                if (requestPath.getPathCount() != 2) {
                    return new ErrorResult(HttpServletResponse.SC_BAD_REQUEST, "expected 2 params: " + requestPath);
                }

                long accountId = requestPath.getLongParam(1);
                Account account = database.getAccounts().selectById(accountId);
                auth.checkAccountAccess(account);

                try {
                    TransferData data = RequestData.parseInto(gson, request, TransferData.class);
                    TransferOperation operation = new TransferOperation(database);

                    operation.perform(data, account);

                    TransferResult transferResult = new TransferResult(account.getBalance(),
                            database.getAddressBook().containsRecipient(account.getUserId(),
                                    data.getDestinationUserId(),
                                    data.getDestinationAccountId()));

                    return new JsonResult(gson, transferResult);
                } catch (OperationException e) {
                    return new ErrorResult(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                }
            } catch (ModelNotFoundException e) {
                throw new PermissionException("no access to account");
            }
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
