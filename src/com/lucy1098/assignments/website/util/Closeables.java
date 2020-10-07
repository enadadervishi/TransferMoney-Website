package com.lucy1098.assignments.website.util;

public class Closeables {

    private Closeables() {}

    public static void closeSilently(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }
}
