package com.les.controller;

import com.les.model.Harmonic;

import java.util.function.BiFunction;

public interface LabController {

    int n = 8;

    int Wmax = 1200;

    int N = 1024;

    int Amax = 1;

    BiFunction<Harmonic, Double, Double> harmonicFunction = (h, t) -> h.A() * Math.sin(h.W() * t + h.phi());
}
