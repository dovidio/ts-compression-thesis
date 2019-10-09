package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;
import com.dovidio.tsbenchmark.TimeSeries;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Deserializer {

    private final NamedDataPointExtractor extractor;

    public Deserializer(NamedDataPointExtractor extractor) {
        this.extractor = extractor;
    }

    public Map<String, TimeSeries> deserialize(BufferedReader reader) {
        Map<String, TimeSeries> timeSeriesHashMap = new HashMap<>();
        reader.lines().forEach(line -> {
            NamedDataPoint namedDataPoint = extractor.extract(line);
            if (namedDataPoint != null) {
                timeSeriesHashMap.computeIfAbsent(namedDataPoint.name, TimeSeries::new).append(namedDataPoint.timestamp, namedDataPoint.value);
            }
        });

        return timeSeriesHashMap;
    }
}
