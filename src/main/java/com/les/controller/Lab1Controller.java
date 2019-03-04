package com.les.controller;

import com.les.model.Signal;
import com.les.model.dto.Point;
import com.les.util.MathUtils;
import com.les.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/lab1")
public class Lab1Controller implements LabController {

    @GetMapping("/x/chart")
    public Map<String, Object> xChart(@RequestParam(defaultValue = "0") double from,
                                      @RequestParam(defaultValue = "1") double to) {
        HashMap<String, Object> response = new HashMap<>();
        Signal s = new Signal(n, Wmax, Amax);

        List<Point> list = MathUtils.range(from, to, (to - from) / N)
                .stream()
                .map(t -> new Point(t, s.value(t, harmonicFunction)))
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

            for (int f = 0; f < 3; f++) {
                time += TimeUtil.time(
                        () -> MathUtils.range(0, 1, 1 / div).forEach(t -> s.value(t, harmonicFunction))
                );
            }

            time /= 3;

            response.add(new Point(div, time));
        }

        return response;
    }

    @GetMapping("/n/chart")
    public List<Point> nChart(@RequestParam(defaultValue = "20") int count,
                              @RequestParam(defaultValue = "10") int step) {
        List<Point> response = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            response
                    .add(
                            new Point(
                                    (double) (i * step),
                                    MathUtils.range(0, 1, 1 / ((double) (i * step))).size()
                            )
                    );
        }

        return response;
    }
}
