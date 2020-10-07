package com.lucy1098.assignments.website.models;

public enum Table {
    USER("users", 3, User.class),
    ACCOUNT("accounts", 1, Account.class),
    TRANSFER("transfers", 2, Transfer.class);

    private final String name;
    private final long currentVersion;
    private final Class<? extends Model> modelClass;

    Table(String name, long currentVersion, Class<? extends Model> modelClass) {
        this.name = name;
        this.currentVersion = currentVersion;
        this.modelClass = modelClass;
    }

    public String tableName() {
        return name;
    }

    public long currentVersion() {
        return currentVersion;
    }

    public Class<? extends Model> modelClass() {
        return modelClass;
    }
}
