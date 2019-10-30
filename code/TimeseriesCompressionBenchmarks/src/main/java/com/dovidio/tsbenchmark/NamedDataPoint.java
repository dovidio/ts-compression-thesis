package com.dovidio.tsbenchmark;

/**
 * Data point with a name. Used by classes implementing {@link com.dovidio.tsbenchmark.deserializer.NamedDataPointExtractor}.
 * The name is used to append this data point to the correct time series.
 */
public class NamedDataPoint {

    public final String name;
    public final long timestamp;
    public final double value;

    public NamedDataPoint(String name, long timestamp, double value) {
        this.name = name;
        this.timestamp = timestamp;
        this.value = value;
    }
}
