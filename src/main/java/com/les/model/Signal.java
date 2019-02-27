package com.les.model;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Signal {

    private final List<Harmonic> harmonics;

    public Signal(int n, double Wmax, double Amax) {
        this.harmonics = Collections.unmodifiableList(
                IntStream
                        .range(0, n)
                        .mapToDouble(i -> Wmax / n * (i + 1))
                        .mapToObj(Wp -> new Harmonic(Amax, Wp))
                        .collect(Collectors.toList())
        );
    }

    public double value(double t, BiFunction<Harmonic, Double, Double> harmonicFunction) {
        return harmonics.parallelStream().mapToDouble(harmonic -> harmonicFunction.apply(harmonic, t)).sum();
    }
}
