package it.polimi.tiw.exceptions;

public class ModelException extends Exception {

    public ModelException(Throwable cause) {
        super(cause);
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException() {

    }
}
