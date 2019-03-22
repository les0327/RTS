package com.les.service;

import com.les.model.Signal;
import com.les.model.dto.Complex;
import com.les.util.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.les.controller.LabController.*;
import static com.les.util.MathUtils.range;

@Slf4j
public class FftServiceTest {

    private final FftService fftService = new FftService();

    @Test
    public void FFT() {
//        int N = 16;
//        final Signal s = new Signal(n, Wmax, Amax);
//
//        List<Double> tValues = range(0, 1, 1. / N);
//
//        Double[] x = tValues.parallelStream()
//                .map(t -> s.value(t, harmonicFunction))
//                .toArray(Double[]::new);

        List<Double> tValues = range(0, 2 * Math.PI, Math.PI / 4);

        Double[] x = tValues.stream()
                .map(t -> Math.sin(t))
                .toArray(Double[]::new);

        Complex[] fftFj = fftService.FFT(x);
        Complex[] fft = fftService.FFT(x);
        Complex[] dft = MathUtils.DFT(x);

        log.debug("FJ fft- {}", Arrays.toString(fftFj));
        log.debug("fft - {}", Arrays.toString(fft));
        log.debug("dft - {}", Arrays.toString(dft));
    }


}