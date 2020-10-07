package com.lucy1098.assignments.website.exceptions;

public class HandleException extends Exception {

    private final int errorCode;

    public HandleException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
