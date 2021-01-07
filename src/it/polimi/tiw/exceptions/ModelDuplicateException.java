package it.polimi.tiw.exceptions;

/**
 * Registrazione di doppio username
 *
 */
public class ModelDuplicateException extends ModelException {

    public ModelDuplicateException(Throwable cause) {
        super(cause);
    }
}
