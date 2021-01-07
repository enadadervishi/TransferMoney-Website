package it.polimi.tiw.models;

/**
 * It's the configuration of each table in database
 *
 */
public interface Model {
    String[] getFieldDescriptors();
    String[] getFieldNames();
    Object[] getFieldValues();

    long getId();
    void setId(long id);
}
