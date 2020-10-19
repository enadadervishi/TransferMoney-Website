package it.polimi.tiw.operation.transfer;

import it.polimi.tiw.exceptions.ParamException;
import it.polimi.tiw.operation.RequestParams;

public class TransferData {

    private final long destinationUserId;
    private final long destinationAccountId;
    private final double amount;

    public TransferData(long destinationUserId, long destinationAccountId, double amount) {
        this.destinationUserId = destinationUserId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
    }

    public static TransferData fromRequest(RequestParams params) throws ParamException {
        return new TransferData(
                params.getLong("destinationUserId"),
                params.getLong("destinationAccountId"),
                params.getDouble("amount")
        );
    }

    public long getDestinationUserId() {
        return destinationUserId;
    }

    public long getDestinationAccountId() {
        return destinationAccountId;
    }

    public double getAmount() {
        return amount;
    }
}
