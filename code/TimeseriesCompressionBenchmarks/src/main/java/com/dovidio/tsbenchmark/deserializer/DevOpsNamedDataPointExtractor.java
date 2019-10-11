package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DevOpsNamedDataPointExtractor implements NamedDataPointExtractor {
    @Override
    public List<NamedDataPoint> extract(String string) {
        String[] parts = string.split(",");
        // ignore table name and time bucket
        String timeSeriesKey = String.join(",", Arrays.copyOfRange(parts, 1, parts.length - 3));
        long timestamp = Long.parseLong(parts[parts.length - 2]) / 1000;
        double value = Double.parseDouble(parts[parts.length - 1]);
        return Collections.singletonList(new NamedDataPoint(timeSeriesKey, timestamp, value));
    }
}
