package com.les.util;

public final class Utils {

    private Utils() {
    }

    public static Double[] divideArr(Double[] arr, boolean even) {
        Double[] newArr = new Double[arr.length / 2];

        int index = even ? 0 : 1;

        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[index];
            index += 2;
        }

        return newArr;
    }
}
