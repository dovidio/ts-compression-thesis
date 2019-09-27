package com.dovidio.tsbenchmark;

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
