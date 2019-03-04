package com.les.controller;

import com.les.model.Signal;
import com.les.model.dto.Point;
import com.les.util.MathUtils;
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

    public Map<String, Object> compute(@RequestParam(defaultValue = "0") double from,
                                       @RequestParam(defaultValue = "1") double to) {

        HashMap<String, Object> response = new HashMap<>();

        List<Double> corrXValues = new ArrayList<>();
        List<Double> corrYValues = new ArrayList<>();
        List<Double> corrXYValues = new ArrayList<>();

        Signal s = new Signal(n, Wmax, Amax);

        List<Double> tValues = range(from, to, (to - from) / N);

        List<Point> xChart = getChart(tValues, s);
        List<Point> yChart = getChart(tValues, s);

        List<Double> xValues = xChart.stream().map(Point::getY).collect(Collectors.toList());
        List<Double> yValues = yChart.stream().map(Point::getY).collect(Collectors.toList());

        for (int tau = 0; tau    < N / 2; tau++) {
            getCorrelation(corrXValues, xValues, xValues, tau);
            getCorrelation(corrYValues, yValues, yValues, tau);
            getCorrelation(corrXYValues, xValues, yValues, tau);
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

    private void getCorrelation(List<Double> corrXYValues, List<Double> aValues, List<Double> bValues, int tau) {
        corrXYValues.add(correlation(aValues.subList(0, aValues.size() / 2), bValues, tau));
    }

    private List<Point> mapToChart(List<Double> tValues, List<Double> values) {
        List<Point> chart = new ArrayList<>();
        for (int i = 0 ; i < tValues.size(); i++)
            chart.add(new Point(tValues.get(i), values.get(i)));

        return chart;
    }

    private List<Point> getChart(List<Double> tValues, Signal s) {
        return tValues.stream()
                .map(t -> new Point(t, s.value(t, harmonicFunction)))
                .collect(Collectors.toList());
    }
}
