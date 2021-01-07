package it.polimi.tiw.exceptions;

/**
 * Problemi di operazione -> errori  su transazione
 *
 */
public class OperationException extends Exception {

    public OperationException(String message) {
        super(message);
    }
}
