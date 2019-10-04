package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;

import java.util.Arrays;

public class DevOpsNamedDataPointExtractor implements NamedDataPointExtractor {
    @Override
    public NamedDataPoint extract(String string) {
        String[] parts = string.split(",");
        // ignore table name and time bucket
        String timeSeriesKey = String.join(",", Arrays.copyOfRange(parts, 1, parts.length - 3));
        long timestamp = Long.parseLong(parts[parts.length - 2]) / 1000;
        double value = Double.parseDouble(parts[parts.length - 1]);
        return new NamedDataPoint(timeSeriesKey, timestamp, value);
    }
}
