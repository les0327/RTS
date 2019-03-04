package com.les.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MathUtils {

    private MathUtils() {
    }

    public static double ev(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).sum() / values.size();
    }

    public static double variance(List<Double> values) {
        double ev = ev(values);
        return values.stream().mapToDouble(x -> (x - ev) * (x - ev)).sum() / (values.size() - 1);
    }

    public static List<Double> range(double from, double to, double step) {
        List<Double> list = new ArrayList<>();

        for (double i = from; i <= to; i+= step) {
            list.add(i);
        }

        return list;
    }

    public static double correlation(List<Double> aValues, List<Double> bValues, int tauIndex) {
        double aEv = ev(aValues);
        double bEv = ev(bValues);
        aValues = aValues.stream().map(x -> x - aEv).collect(Collectors.toList());
        bValues = bValues.stream().map(y -> y - bEv).collect(Collectors.toList());
        double res = 0;
        int i;
        for (i = 0; i < aValues.size() && (i + tauIndex) < bValues.size(); i++) {
            res += aValues.get(i) * bValues.get(i + tauIndex);
        }
        return res / (i - 1);
    }

}
