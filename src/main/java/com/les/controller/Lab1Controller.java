package com.les.controller;

import com.les.model.Harmonic;
import com.les.model.Signal;
import com.les.model.dto.Point;
import com.les.util.MathUtils;
import com.les.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.*;

@RestController
@RequestMapping("/api/v1/lab1")
public class Lab1Controller {

    public static final int n = 8;

    public static final int Wmax = 1200;

    public static final int N = 1024;

    public static final int Amax = 1;

    private final BiFunction<Harmonic, Double, Double> harmonicFunction = (h, t) -> h.A() * Math.sin(h.W() * t + h.phi());

    @GetMapping("/x/chart")
    public Map<String, Object> xChart(@RequestParam(defaultValue = "0") double from,
                                      @RequestParam(defaultValue = "1") double to) {
        HashMap<String, Object> response = new HashMap<>();
        Signal s = new Signal(n, Wmax, Amax);

        List<Point> list = range(0, N + 1)
                .mapToDouble(k -> from + k * (to - from) / N)
                .mapToObj(t -> new Point(t, s.value(t, harmonicFunction)))
                .collect(Collectors.toList());

        response.put("chart", list);
        response.put("ev", MathUtils.ev(list.stream().map(Point::getY).collect(Collectors.toList())));
        response.put("variance", MathUtils.variance(list.stream().map(Point::getY).collect(Collectors.toList())));

        return response;
    }

    @GetMapping("/time/chart")
    public List<Point> timeChart(@RequestParam(defaultValue = "20") int count,
                                 @RequestParam(defaultValue = "10") int step) {
        Signal s = new Signal(n, Wmax, Amax);

        List<Point> response = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            final double div = i * step;

            long time = 0;
            for (int f = 0; f < 5; f++) {
                time += TimeUtil.time(
                        () -> range(0, N + 1)
                                .mapToDouble(k -> k / div)
                                .forEach(t -> s.value(t, harmonicFunction))
                );
            }

            time /= 5;

            response.add(
                    new Point(
                            div,
                            time
                    )
            );
        }

        return response;
    }
}
