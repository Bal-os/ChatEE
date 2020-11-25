package com.gmail.stels.sbal;

public class Utils {
    private static final String URL = "http://127.0.0.1";
    private static final int PORT = 8082;

    public static String getURL() {
        final String s = URL + ":" + PORT;
        return s;
    }
}
