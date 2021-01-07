package it.polimi.tiw.exceptions;

/**
 * Errore nelle servlet: viene mandato un mesaggio allo user di "not found"
 */
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
