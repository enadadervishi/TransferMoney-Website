package it.polimi.tiw.operation.transfer;

public enum TermField {
    USER_ID("recipientUserId"),
    ACCOUNT_ID("recipientAccountId")
    ;

    private final String tableName;

    TermField(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
