package it.polimi.tiw.util;

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
