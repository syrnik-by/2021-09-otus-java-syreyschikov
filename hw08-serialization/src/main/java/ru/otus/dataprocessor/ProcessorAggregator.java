package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value

        Map<String, Double> map = new TreeMap<>();

        for (Measurement measurement : data
        ) {
            if (map.containsKey(measurement.getName()))
                map.put(measurement.getName(), map.get(measurement.getName()) + measurement.getValue());
            else map.put(measurement.getName(), measurement.getValue());
        }
        return map;
    }
}
