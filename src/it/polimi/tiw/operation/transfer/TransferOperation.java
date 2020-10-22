package it.polimi.tiw.operation.transfer;

import it.polimi.tiw.dao.Database;
import it.polimi.tiw.exceptions.ModelDuplicateException;
import it.polimi.tiw.exceptions.ModelNotFoundException;
import it.polimi.tiw.exceptions.OperationException;
import it.polimi.tiw.models.Account;
import it.polimi.tiw.models.Transfer;
import it.polimi.tiw.util.ThrowingRunnable;

import java.sql.SQLException;
import java.sql.Timestamp;

public class TransferOperation {

    private final Database database;

    public TransferOperation(Database database) {
        this.database = database;
    }

    public void perform(TransferData data, Account usedAccount) throws OperationException, SQLException {
        try {
            Account destAccount = database.getAccounts().selectById(data.getDestinationAccountId());

            if (destAccount.getUserId() != data.getDestinationUserId()) {
                throw new OperationException("Recipient account does not belong to user");
            }
            if (destAccount.getId() == usedAccount.getId()) {
                throw new OperationException("Cannot transfer to same account");
            }
            if (usedAccount.getBalance() < data.getAmount()) {
                throw new OperationException("Balance not enough for transfer");
            }

            usedAccount.setBalance(usedAccount.getBalance() - data.getAmount());
            destAccount.setBalance(destAccount.getBalance() + data.getAmount());

            Transfer transfer = new Transfer();
            transfer.setTimestamp(new Timestamp(System.currentTimeMillis()));
            transfer.setAmount(data.getAmount());
            transfer.setSourceAccountId(usedAccount.getId());
            transfer.setDestinationAccountId(data.getDestinationAccountId());

            database.doTransaction((ThrowingRunnable<SQLException, ModelDuplicateException>)()-> {
                database.getAccounts().update(usedAccount);
                database.getAccounts().update(destAccount);
                database.getTransfers().add(transfer);
            });
        } catch (ModelNotFoundException e) {
            throw new OperationException("dest account does not exist");
        } catch (ModelDuplicateException e) { // shouldn't occur
            throw new AssertionError(e);
        }
    }
}
