package com.les.controller;

import com.les.model.Signal;
import com.les.model.dto.Complex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.les.util.MathUtils.*;

@RestController
@RequestMapping("/api/v1/lab3")
public class Lab3Controller implements LabController {

    private final Signal s = new Signal(n, Wmax, Amax);

    @GetMapping("/dft/chart")
    public Complex[] getDFT(@RequestParam(defaultValue = "4") int N) {
        final Complex[][] W = getW(N);

        List<Double> tValues = range(0, 1, 1. / N);

        Double[] x = tValues.parallelStream()
                .map(t -> s.value(t, harmonicFunction))
                .toArray(Double[]::new);

        Complex[] F = new Complex[N];

        for (int p = 0; p < N; p++) {

            double real = 0;
            double imagine = 0;

            for (int k = 0; k < N; k++) {
                real += x[k] * W[p][k].getReal();
                imagine -= x[k] * W[p][k].getImagine();
            }

            F[p] = new Complex(real, imagine);
        }

        return F;
    }
}
