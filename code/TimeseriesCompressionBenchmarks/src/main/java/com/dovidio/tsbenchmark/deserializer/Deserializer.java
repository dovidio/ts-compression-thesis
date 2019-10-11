package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;
import com.dovidio.tsbenchmark.TimeSeries;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deserializer {

    private final NamedDataPointExtractor extractor;

    public Deserializer(NamedDataPointExtractor extractor) {
        this.extractor = extractor;
    }

    public Map<String, TimeSeries> deserialize(BufferedReader reader) {
        Map<String, TimeSeries> timeSeriesHashMap = new HashMap<>();
        reader.lines().forEach(line -> {
            List<NamedDataPoint> namedDataPoints = extractor.extract(line);

            for (NamedDataPoint dataPoint : namedDataPoints) {
                if (dataPoint != null) {
                    timeSeriesHashMap.computeIfAbsent(dataPoint.name, TimeSeries::new).append(dataPoint.timestamp, dataPoint.value);
                }
            }
        });

        return timeSeriesHashMap;
    }
}
