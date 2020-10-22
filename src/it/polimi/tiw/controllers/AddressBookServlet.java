package it.polimi.tiw.controllers;

import com.google.gson.Gson;
import it.polimi.tiw.models.AddressBook;
import it.polimi.tiw.operation.ControllerOperations;
import it.polimi.tiw.operation.EmptyResult;
import it.polimi.tiw.operation.ErrorResult;
import it.polimi.tiw.operation.JsonResult;
import it.polimi.tiw.operation.OperationFlag;
import it.polimi.tiw.operation.RequestData;
import it.polimi.tiw.operation.RequestParams;
import it.polimi.tiw.operation.RequestPath;
import it.polimi.tiw.operation.model.NewAddressBookData;
import it.polimi.tiw.operation.transfer.TermField;
import it.polimi.tiw.util.Closeables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddressBookServlet", urlPatterns = {"/addressbook"})
public class AddressBookServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private ControllerOperations operations = null;

    public void init() throws ServletException {
        operations = ControllerOperations.createDefault();
    }

    public void destroy() {
        Closeables.closeSilently(operations);
    }

    // GET
    // addressbook
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            long userId = auth.getConnectedUserId().getAsLong();

            RequestPath requestPath = new RequestPath(request);
            if(requestPath.hasParams()) {
                return new ErrorResult(HttpServletResponse.SC_BAD_REQUEST, "wrong path");
            }

            RequestParams requestParams = new RequestParams(request);
            if (requestParams.count() == 2) {
                String fieldStr = requestParams.get("field");
                String term = requestParams.get("term");
                TermField field = TermField.valueOf(fieldStr);

                List<AddressBook> addressBooks = database.getAddressBook().selectByUserIdAndTerm(userId, field, term);
                return new JsonResult(gson, addressBooks);
            }

            List<AddressBook> addressBooks = database.getAddressBook().selectByUserId(userId);
            return new JsonResult(gson, addressBooks);
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }

    // POST
    // addressbook
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        operations.run(request, response, (database, auth)-> {
            RequestPath requestPath = new RequestPath(request);
            if (requestPath.hasParams()) {
                return new ErrorResult(HttpServletResponse.SC_BAD_REQUEST, "wrong path");
            }

            NewAddressBookData data = RequestData.parseInto(gson, request, NewAddressBookData.class);

            long userId = auth.getConnectedUserId().getAsLong();

            AddressBook addressBook = new AddressBook();
            addressBook.setUserId(userId);
            addressBook.setRecipientUserId(data.getRecipientUserId());
            addressBook.setRecipientAccountId(data.getRecipientAccountId());

            database.getAddressBook().add(addressBook);

            return new EmptyResult();
        }, OperationFlag.REQUIRE_LOGGED_IN);
    }
}
