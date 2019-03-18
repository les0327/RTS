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

import static com.les.util.MathUtils.*;

@RestController
@RequestMapping("/api/v1/lab2")
public class Lab2Controller implements LabController {

    private final Signal s = new Signal(n, Wmax, Amax);

    @GetMapping("/chart")
    public Map<String, Object> compute(@RequestParam(defaultValue = "0") double from,
                                       @RequestParam(defaultValue = "1") double to) {

        HashMap<String, Object> response = new HashMap<>();

        List<Double> corrXValues = new ArrayList<>(N / 2);
        List<Double> corrYValues = new ArrayList<>(N / 2);
        List<Double> corrXYValues = new ArrayList<>(N / 2);

        List<Double> tValues = range(from, to, (to - from) / N);

        List<Point<Double, Double>> xChart = getChart(tValues, s);
        List<Point<Double, Double>> yChart = getChart(tValues, s);

        List<Double> xValues = xChart.parallelStream().map(Point::getY).collect(Collectors.toList());
        List<Double> yValues = yChart.parallelStream().map(Point::getY).collect(Collectors.toList());

        for (int tau = 0; tau < N / 2; tau++) {
            corrXValues.add(correlation(xValues, tau));
            corrYValues.add(correlation(yValues, tau));
            corrXYValues.add(correlation(xValues, yValues, tau));
        }

        response.put("xChart", xChart);
        response.put("xEv", ev(xValues));
        response.put("xVariance", variance(xValues));

        response.put("yChart", xChart);
        response.put("yEv", ev(yValues));
        response.put("yVariance", variance(yValues));

        response.put("rxx", mapToChart(tValues, corrXValues));
        response.put("ryy", mapToChart(tValues, corrYValues));
        response.put("rxy", mapToChart(tValues, corrXYValues));

        return response;
    }

    @GetMapping("/time/chart")
    public Map<String, Point[]> timeChart(@RequestParam(defaultValue = "2") int from,
                                          @RequestParam(defaultValue = "20") int to,
                                          @RequestParam(defaultValue = "1000") int multiplier) {
        Point[] rxxChart = new Point[to - from];
        Point[] rxyChart = new Point[to - from];

        for (int i = from; i < to; i++) {

            final int step = i * multiplier;

            List<Double> tValues = range(0, 1, 1. / step);

            List<Double> xValues = getValues(tValues, s);
            List<Double> yValues = getValues(tValues, s);

            long rxxTime= TimeUtil.time(() -> MathUtils.correlation(xValues, 2));
            long rxyTime= TimeUtil.time(() -> MathUtils.correlation(xValues, yValues, 2));

            rxxChart[i - from] = new Point<>(step, rxxTime);
            rxyChart[i - from] = new Point<>(step, rxyTime);
        }

        Map<String, Point[]> response = new HashMap<>();

        response.put("rxx", rxxChart);
        response.put("rxy", rxyChart);

        return response;
    }

    private List<Point<Double, Double>> mapToChart(List<Double> tValues, List<Double> values) {
        List<Point<Double, Double>> chart = new ArrayList<>(Math.min(tValues.size(), values.size()));

        for (int i = 0; i < tValues.size() && i < values.size(); i++)
            chart.add(new Point<>(tValues.get(i), values.get(i)));

        return chart;
    }

    private List<Point<Double, Double>> getChart(List<Double> tValues, Signal s) {
        return tValues.parallelStream()
                .map(t -> new Point<>(t, s.value(t, harmonicFunction)))
                .collect(Collectors.toList());
    }

    private List<Double> getValues(List<Double> tValues, Signal s) {
        return tValues.parallelStream()
                .map(t ->  s.value(t, harmonicFunction))
                .collect(Collectors.toList());
    }
}
