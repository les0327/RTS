package com.les.controller;

import com.les.model.Signal;
import com.les.model.dto.Complex;
import com.les.util.MathUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.les.util.MathUtils.range;

@RestController
@RequestMapping("/api/v1/lab4")
public class Lab4Controller implements LabController {

    private final Signal s = new Signal(n, Wmax, Amax);

    @GetMapping("/fft/chart")
    public Complex[] getFFT(int N) {

        List<Double> tValues = range(0, 1, 1. / N);

        Double[] x = tValues.parallelStream()
                .map(t -> s.value(t, harmonicFunction))
                .toArray(Double[]::new);


        return MathUtils.FFT(x);
    }
}
