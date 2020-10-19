package it.polimi.tiw.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.exceptions.OperationException;
import it.polimi.tiw.exceptions.ParamException;
import it.polimi.tiw.exceptions.PermissionException;
import it.polimi.tiw.models.Account;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RenderAccountPageResult;
import it.polimi.tiw.operation.RequestParams;
import it.polimi.tiw.operation.transfer.TransferData;
import it.polimi.tiw.operation.transfer.TransferOperation;
import it.polimi.tiw.util.Closeables;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "TransferServlet", urlPatterns = {"/transfer"})
public class TransferServlet extends HttpServlet {

    private ControllerOperations operations = null;

    public void init() throws ServletException {
        operations = ControllerOperations.createDefault();
    }

    public void destroy() {
        Closeables.closeSilently(operations);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            try {
                RequestParams requestParams = new RequestParams(request);
                long accountId = requestParams.getLong("accountId");
                Account account = database.getAccounts().selectById(accountId);
                auth.checkAccountAccess(account);

                try {
                    TransferData data = TransferData.fromRequest(requestParams);
                    TransferOperation operation = new TransferOperation(database);

                    operation.perform(data, account);

                    return new RenderAccountPageResult(database, account);
                } catch (OperationException | ParamException e) {
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put("transferError", e.getMessage());

                    return new RenderAccountPageResult(database, account, attributes);
                }
            } catch (ModelNotFoundException e) {
                throw new PermissionException("no access to account");
            }
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
