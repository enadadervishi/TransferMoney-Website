package com.lucy1098.assignments.website.controllers;

import com.lucy1098.assignments.website.exceptions.ModelNotFoundException;
import com.lucy1098.assignments.website.exceptions.OperationException;
import com.lucy1098.assignments.website.exceptions.ParamException;
import com.lucy1098.assignments.website.exceptions.PermissionException;
import com.lucy1098.assignments.website.models.Account;
import com.lucy1098.assignments.website.operation.ControllerOperations;
import com.lucy1098.assignments.website.operation.OperationFlag;
import com.lucy1098.assignments.website.operation.RenderAccountPageResult;
import com.lucy1098.assignments.website.operation.RequestParams;
import com.lucy1098.assignments.website.operation.transfer.TransferData;
import com.lucy1098.assignments.website.operation.transfer.TransferOperation;
import com.lucy1098.assignments.website.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
