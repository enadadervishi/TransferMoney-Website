package com.lucy1098.assignments.website.models;

public enum Table {
    USER("users", new User()),
    ACCOUNT("accounts", new Account()),
    TRANSFER("transfers", new Transfer());

    private final String name;
    private final Model modelInstance;

    Table(String name, Model modelInstance) {
        this.name = name;
        this.modelInstance = modelInstance;
    }

    public String tableName() {
        return name;
    }

    public Model modelInstance() {
        return modelInstance;
    }
}
