package it.polimi.tiw.operation.transfer;

public class TransferData {

    private final long destinationUserId;
    private final long destinationAccountId;
    private final double amount;

    public TransferData(long destinationUserId, long destinationAccountId, double amount) {
        this.destinationUserId = destinationUserId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
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
