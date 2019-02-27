package com.les.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Harmonic {
    private final double Amax;
    private final double Wmax;

    public double A() {
        return this.Amax * Math.random();
    }

    public double W() {
        return this.Wmax * Math.random();
    }

    public double phi() {
        return (Math.PI * 2) * Math.random();
    }
}
