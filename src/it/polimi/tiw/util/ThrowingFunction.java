package it.polimi.tiw.util;

/**
 * Necessario per lambda in selectAll modelQueryExecution 
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    R apply(T t) throws E;
}
