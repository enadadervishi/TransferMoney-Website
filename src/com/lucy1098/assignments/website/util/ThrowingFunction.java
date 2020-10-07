package com.lucy1098.assignments.website.util;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    R apply(T t) throws E;
}
