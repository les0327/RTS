package com.les.controller;

import com.les.model.Signal;
import com.les.model.dto.Complex;
import com.les.model.dto.Point;
import com.les.util.MathUtils;
import com.les.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.les.util.MathUtils.*;

@RestController
@RequestMapping("/api/v1/lab3")
public class Lab3Controller implements LabController {

    private final Signal s = new Signal(n, Wmax, Amax);

    @GetMapping("/dft/chart")
    public Complex[] getDFT(int N) {

        List<Double> tValues = range(0, 1, 1. / N);

        Double[] x = tValues.parallelStream()
                .map(t -> s.value(t, harmonicFunction))
                .toArray(Double[]::new);

        return MathUtils.DFT(x);
    }

    @GetMapping("/time/chart")
    public Map<String, Point[]> time(int from, int to, int multiplier) {
        Point[] withTable = new Point[to - from];
        Point[] withoutTable = new Point[to - from];

        for (int i = from; i < to; i++) {

            final int step = i * multiplier;

            List<Double> tValues = range(0, 1, 1. / step);

            Double[] x = tValues.parallelStream()
                    .map(t -> s.value(t, harmonicFunction))
                    .toArray(Double[]::new);

            long rxxTime = TimeUtil.time(() -> MathUtils.DFT(x));
            long rxyTime= TimeUtil.time(() -> MathUtils.DFTWithoutTable(x));

            withTable[i - from] = new Point<>(step, rxxTime);
            withoutTable[i - from] = new Point<>(step, rxyTime);
        }

        Map<String, Point[]> response = new HashMap<>();

        response.put("with", withTable);
        response.put("without", withoutTable);

        return response;
    }
}
