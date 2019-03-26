package com.les.controller;

import com.les.model.Signal;
import com.les.model.dto.Complex;
import com.les.model.dto.Point;
import com.les.service.FftService;
import com.les.util.MathUtils;
import com.les.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.les.util.MathUtils.range;

@RestController
@RequestMapping("/api/v1/lab4")
public class Lab4Controller implements LabController {

    private final Signal s = new Signal(n, Wmax, Amax);

    private final FftService fftService;

    @Autowired
    public Lab4Controller(FftService fftService) {
        this.fftService = fftService;
    }

    @GetMapping("/fft/chart")
    public Complex[] getFFT(int N) {

        List<Double> tValues = range(0, 1, 1. / N);

        Double[] x = tValues.parallelStream()
                .map(t -> s.value(t, harmonicFunction))
                .toArray(Double[]::new);


        return MathUtils.FFT(x);
    }

    @GetMapping("/time/chart")
    public Point[] time (int count) {
        Point[] time = new Point[count];

        int N = 8;
        for (int i = 0; i < time.length; i++) {
            List<Double> tValues = range(0, 1, 1. / N);

            Double[] x = tValues.parallelStream()
                    .map(t -> s.value(t, harmonicFunction))
                    .toArray(Double[]::new);

            long dftTime = TimeUtil.time(() -> MathUtils.DFT(x));
            long fftTime = TimeUtil.time(() -> fftService.FFT(x));

            time[i] = new Point<>(N, fftTime / dftTime);
            N *= 2;
        }
        return time;
    }

}
