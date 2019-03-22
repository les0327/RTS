package com.les.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void divideArr() {
        Double[] arr = {0., 1., 2., 3.};

        assertArrayEquals(new Double[]{0., 2.}, Utils.divideArr(arr, true));
        assertArrayEquals(new Double[]{1., 3.}, Utils.divideArr(arr, false));
    }
}