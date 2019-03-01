package com.les.util;

import java.time.Instant;

public final class TimeUtil {

    private TimeUtil() {
    }

    public static long time(Action action) {
        Instant start = Instant.now();
        action.action();
        Instant end = Instant.now();

        return end.toEpochMilli() - start.toEpochMilli();
    }
}
