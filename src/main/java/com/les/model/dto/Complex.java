package com.les.model.dto;

import lombok.Data;

@Data
public class Complex {
    private final double real;
    private final double imagine;

    public Complex add(Complex number) {
        return new Complex(real + number.getReal(), imagine + number.getImagine());
    }

    public Complex sub(Complex number) {
        return new Complex(real - number.getReal(), imagine - number.getImagine());
    }

    public Complex mul(Complex number) {
        return new Complex(real * number.getReal() - imagine * number.getImagine(),
                real * number.getImagine() + imagine * number.getReal());
    }

    @Override
    public String toString() {
        return String.format("%f + %fi", real, imagine);
    }
}
