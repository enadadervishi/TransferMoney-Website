package it.polimi.tiw.controllers;

import com.google.gson.Gson;
import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.exceptions.OperationException;
import it.polimi.tiw.exceptions.PermissionException;
import it.polimi.tiw.models.Account;
import it.polimi.tiw.models.Transfer;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.JsonResult;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RequestData;
import it.polimi.tiw.operation.RequestPath;
import it.polimi.tiw.operation.model.Response;
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

@WebServlet(name = "TransferServlet", urlPatterns = {"/transfer/*"})// /transfer/10
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
            	return new JsonResult(gson, Response.error("expected 2 params: " + requestPath));
            }

            long accountId = requestPath.getLongParam(1);
            Account account = database.getAccounts().selectById(accountId);
            auth.checkAccountAccess(account);

            // account = source || account = destination
            // [{transfer}, {transfer}]
            List<Transfer> transfers = database.getTransfers().selectByAccountIdDescending(accountId);
            return new JsonResult(gson, Response.success(transfers));
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
                	return new JsonResult(gson, Response.error("expected 2 params: " + requestPath));
                }

                long accountId = requestPath.getLongParam(1);
                Account account = database.getAccounts().selectById(accountId);
                auth.checkAccountAccess(account);

                try {
                    TransferData data = RequestData.parseInto(gson, request, TransferData.class);
                    TransferOperation operation = new TransferOperation(database);

                    operation.perform(data, account);
                    
                    boolean isInAddressBook = database.getAddressBook().containsRecipient(account.getUserId(),
                            data.getDestinationUserId(),
                            data.getDestinationAccountId());
                    TransferResult transferResult = new TransferResult(account.getBalance(),
                            isInAddressBook);

                    return new JsonResult(gson, Response.success(transferResult));
                } catch (OperationException e) {
                    return new JsonResult(gson, Response.error(e.getMessage()));
                }
            } catch (ModelNotFoundException e) {
                throw new PermissionException("no access to account");
            }
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
