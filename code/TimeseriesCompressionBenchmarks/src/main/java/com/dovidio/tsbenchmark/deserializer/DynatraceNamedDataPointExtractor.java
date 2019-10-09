package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;

public class DynatraceNamedDataPointExtractor implements NamedDataPointExtractor {

    @Override
    public NamedDataPoint extract(String string) {
        String[] parts = string.split(",");
        // Dynatrace return datapoint with null values for non existing values
        if (parts[2].equals("null")) {
            return null;
        }
        long timestamp = Long.parseLong(parts[1]);
        double value = Double.parseDouble(parts[2]);

        return new NamedDataPoint(parts[0], timestamp, value);
    }
}
