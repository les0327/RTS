package com.les.util;

import com.les.model.dto.Complex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MathUtils {

    private MathUtils() {
    }

    public static double ev(List<Double> values) {
        return values.parallelStream().mapToDouble(Double::doubleValue).sum() / values.size();
    }

    public static double variance(List<Double> values) {
        double ev = ev(values);
        return values.parallelStream().mapToDouble(x -> (x - ev) * (x - ev)).sum() / (values.size() - 1);
    }

    public static List<Double> range(double from, double to, double step) {
        List<Double> list = new ArrayList<>((int) Math.round((to - from) * step));

        for (double i = from; i <= to; i+= step) {
            list.add(i);
        }

        return list;
    }

    public static double correlation(List<Double> aValues, int tauIndex) {
        double aEv = ev(aValues);
        aValues = aValues.parallelStream().map(x -> x - aEv).collect(Collectors.toList());

        double res = 0;

        int i;
        int size = aValues.size();
        for (i = 0; i + tauIndex < size; i++) {
            res += aValues.get(i) * aValues.get(i + tauIndex);
        }

        return res / (i - 1);
    }

    public static double correlation(List<Double> aValues, List<Double> bValues, int tauIndex) {
        double aEv = ev(aValues);
        double bEv = ev(bValues);
        aValues = aValues.parallelStream().map(x -> x - aEv).collect(Collectors.toList());
        bValues = bValues.parallelStream().map(y -> y - bEv).collect(Collectors.toList());
        double res = 0;
        int i;
        for (i = 0; i + tauIndex < bValues.size(); i++) {
            res += aValues.get(i) * bValues.get(i + tauIndex);
        }
        return res / (i - 1);
    }

    public static Complex[][] getW(int N) {
        Complex[][] matrix = new Complex[N][N];

        double cof = 2 * Math.PI / N;

        for (int k = 0; k < N; k++) {
            for (int p = 0; p < N; p++) {
                double f = cof * p * k;
                matrix[k][p] = new Complex(Math.cos(f), Math.sin(f));
            }
        }

        return matrix;
    }
}
