package it.polimi.tiw.operation.transfer;

public class TransferResult {

    private final double newBalance;
    private final boolean isRecipientInAddressBook;

    public TransferResult(double newBalance, boolean isRecipientInAddressBook) {
        this.newBalance = newBalance;
        this.isRecipientInAddressBook = isRecipientInAddressBook;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public boolean isRecipientInAddressBook() {
        return isRecipientInAddressBook;
    }
}
