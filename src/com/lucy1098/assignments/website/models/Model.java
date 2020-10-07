package com.lucy1098.assignments.website.models;

public interface Model {
    String[] getFieldDescriptors();
    String[] getFieldNames();
    Object[] getFieldValues();

    long getId();
    void setId(long id);
}
