package it.polimi.tiw.operation.model;

public class NewAddressBookData {

    private final long recipientUserId;
    private final long recipientAccountId;

    public NewAddressBookData(long recipientUserId, long recipientAccountId) {
        this.recipientUserId = recipientUserId;
        this.recipientAccountId = recipientAccountId;
    }

    public long getRecipientUserId() {
        return recipientUserId;
    }

    public long getRecipientAccountId() {
        return recipientAccountId;
    }
}
