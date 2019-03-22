package com.les.util;

import com.les.model.dto.Complex;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.les.util.MathUtils.range;

@Slf4j
public class MathUtilsTest {

    @Test
    public void getW() {
        Complex[][] W = MathUtils.getW(2);

        for (Complex[] cof : W) {
            log.info("{}", Arrays.toString(cof));
        }
    }

    @Test
    public void testDft() {
        List<Double> tValues = range(0, 2 * Math.PI, Math.PI / 4);

        Double[] x = tValues.stream()
                .map(t -> Math.sin(t) + Math.sin(2*t))
                .toArray(Double[]::new);

        log.info("{}", Arrays.toString(x));
        log.info("{}", Arrays.toString(MathUtils.DFT(x)));
    }

    @Test
    public void testFft() {
        List<Double> tValues = range(0, 2 * Math.PI, Math.PI / 4);

        Double[] x = tValues.stream()
                .map(t -> Math.sin(t))
                .toArray(Double[]::new);

        log.info("{}", Arrays.toString(MathUtils.DFT(x)));
        log.info("{}", Arrays.toString(MathUtils.FFT(x)));
    }
}