package com.les.util;

import com.les.model.dto.Complex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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

        for (double i = from; i < to; i += step) {
            list.add(i);
        }

        return list;
    }

    public static double correlation(List<Double> aValues, int tauIndex) {
        List<Double> aList = subEv(aValues);

        double res = 0;

        int i;
        int size = aList.size();
        for (i = 0; i + tauIndex < size; i++) {
            res += aList.get(i) * aList.get(i + tauIndex);
        }

        return res / (i - 1);
    }

    public static double correlation(List<Double> aValues, List<Double> bValues, int tauIndex) {
        List<Double> aList = subEv(aValues);
        List<Double> bList = subEv(bValues);
        double res = 0;
        int i;
        for (i = 0; i + tauIndex < bList.size(); i++) {
            res += aList.get(i) * bList.get(i + tauIndex);
        }
        return res / (i - 1);
    }

    private static List<Double> subEv(List<Double> aValues) {
        double aEv = ev(aValues);
        aValues = aValues.parallelStream().map(x -> x - aEv).collect(Collectors.toList());
        return aValues;
    }

    public static Complex[][] getW(int N) {
        Complex[][] matrix = new Complex[N][N];

        for (int k = 0; k < N; k++) {
           matrix[k] = getW(N, k);
        }

        return matrix;
    }

    public static Complex[] getW(int N, int k) {
        Complex[] w = new Complex[N];

        double cof = 2 * Math.PI / N;

        for (int p = 0; p < N; p++) {
            double f = cof * p * k;
            w[p] = new Complex(Math.cos(f), Math.sin(f));
        }

        return w;
    }

    public static Complex[] DFT(Double[] x) {
        int N = x.length;

        Complex[][] w = getW(N);

        Complex[] F = new Complex[N];


        for (int p = 0; p < N; p++) {

            double real = 0;
            double imagine = 0;

            for (int k = 0; k < N; k++) {
                real += x[k] * w[p][k].getReal();
                imagine -= x[k] * w[p][k].getImagine();
            }

            F[p] = new Complex(real, imagine);
        }
        return F;
    }

    public static Complex[] FFT(Double[] x) {
        int N = x.length;

        Complex[][] halfW = getW(N / 2);
        Complex[] W = getW(N, 1);


        CompletableFuture<Complex[]> evenFuture = CompletableFuture.supplyAsync(() -> {
            Complex[] evenF = new Complex[N / 2];

            for (int p = 0; p < N / 2; p++) {

                double real = 0;
                double imagine = 0;

                for (int k = 0; k < N / 2; k++) {

                    real += x[2 * k] * halfW[p][k].getReal();
                    imagine -= x[2 * k] * halfW[p][k].getImagine();
                }

                evenF[p] = new Complex(real, imagine);
            }
            return evenF;
        });

        CompletableFuture<Complex[]> oddFuture = CompletableFuture.supplyAsync(() -> {
            Complex[] oddF = new Complex[N / 2];

            for (int p = 0; p < N / 2; p++) {

                double real = 0;
                double imagine = 0;

                for (int k = 0; k < N / 2; k++) {

                    real += x[2 * k + 1] * halfW[p][k].getReal();
                    imagine -= x[2 * k + 1] * halfW[p][k].getImagine();
                }

                oddF[p] = new Complex(real, imagine);
            }

            return oddF;
        });

        Complex[] evenF = evenFuture.join();
        Complex[] oddF = oddFuture.join();

        Complex[] F = new Complex[x.length];

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            for (int p = 0; p < N / 2; p++) {
                F[p] = evenF[p].add(W[p].mul(oddF[p]));
            }
        });

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            for (int p = 0; p < N / 2; p++) {
                F[p + N / 2] = evenF[p].sub(W[p].mul(oddF[p]));
            }
        });

        future1.join();
        future2.join();

        return F;
    }
}
