package it.polimi.tiw.util;

/**
 * Necessario per lambda
 */
@FunctionalInterface
public interface ThrowingRunnable<E extends Exception, E2 extends Exception> {

    void run() throws E, E2;
}
