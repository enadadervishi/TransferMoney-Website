package it.polimi.tiw.models;

public interface Model {
    String[] getFieldDescriptors();
    String[] getFieldNames();
    Object[] getFieldValues();

    long getId();
    void setId(long id);
}
