package com.les.util;


public final class TimeUtil {

    private TimeUtil() {
    }

    public static long time(Action action) {
        long start = System.nanoTime();
        action.action();

        return System.nanoTime() - start;
    }
}
