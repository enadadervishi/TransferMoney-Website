package it.polimi.tiw.util;

@FunctionalInterface
public interface ThrowingRunnable<E extends Exception, E2 extends Exception> {

    void run() throws E, E2;
}
