package com.les.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class TimeUtilTest {

    @Test
    public void time()  {
        long time = TimeUtil.time(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.info("Time {}", time);
    }
}