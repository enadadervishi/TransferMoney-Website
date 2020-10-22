package it.polimi.tiw.models;

public enum Table {
    USER("users", 3, new User()),
    ACCOUNT("accounts", 1, new Account()),
    TRANSFER("transfers", 2, new Transfer()),
    ADDRESS_BOOK("addressBook", 1, new AddressBook())
    ;

    private final String name;
    private final long currentVersion;
    private final Model modelInstance;

    Table(String name, long currentVersion, Model modelInstance) {
        this.name = name;
        this.currentVersion = currentVersion;
        this.modelInstance = modelInstance;
    }

    public String tableName() {
        return name;
    }

    public long currentVersion() {
        return currentVersion;
    }

    public Model modelInstance() {
        return modelInstance;
    }
}
