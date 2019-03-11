package com.les.controller;

import com.les.model.Signal;
import com.les.model.dto.Point;
import com.les.util.MathUtils;
import com.les.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/lab1")
public class Lab1Controller implements LabController {

    private final Signal s = new Signal(n, Wmax, Amax);

    @GetMapping("/x/chart")
    public Map<String, Object> xChart(@RequestParam(defaultValue = "0") double from,
                                      @RequestParam(defaultValue = "1") double to) {
        HashMap<String, Object> response = new HashMap<>();

        List<Point<Double, Double>> list = MathUtils.range(from, to, (to - from) / N)
                .stream()
                .map(t -> new Point<>(t, s.value(t, harmonicFunction)))
                .collect(Collectors.toList());

        List<Double> collect = list.parallelStream().map(Point::getY).collect(Collectors.toList());
        response.put("chart", list);
        response.put("ev", MathUtils.ev(collect));
        response.put("variance", MathUtils.variance(collect));

        return response;
    }

    @GetMapping("/time/chart")
    public Point[] timeChart(@RequestParam(defaultValue = "20") int count,
                             @RequestParam(defaultValue = "10") int step) {
        Point[] response = new Point[count];

        for (int i = 0; i < count; i++) {

            final double div = (i + 1) * step;

            long time = 0;

            for (int f = 0; f < 3; f++) {
                time += TimeUtil.time(
                        () -> MathUtils.range(0, 1, 1 / div).forEach(t -> s.value(t, harmonicFunction))
                );
            }

            time /= 3;

            response[i] = new Point<>(div, time);
        }

        return response;
    }

    @GetMapping("/D/chart")
    public Point[] DChart(@RequestParam(defaultValue = "2") int from,
                          @RequestParam(defaultValue = "1024") int to) {

        Point[] response = new Point[to - from];

        for (int i = from; i < to; i++) {

            List<Double> values = MathUtils.range(0, 1, 1. / i)
                    .parallelStream()
                    .map(t -> s.value(t, harmonicFunction))
                    .collect(Collectors.toList());

            response[i - from] = new Point<>(i, MathUtils.variance(values));
        }

        return response;
    }
}
