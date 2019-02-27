package com.les.util;

import java.util.List;

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

}
