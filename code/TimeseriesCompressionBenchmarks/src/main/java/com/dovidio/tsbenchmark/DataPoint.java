package com.dovidio.tsbenchmark;

import fi.iki.yak.ts.compression.gorilla.Pair;

import java.io.Serializable;

public class DataPoint implements Serializable {

    private static final long serialVersionUID = 212312266622630L;
    public final long timestamp;
    public final double value;

    public DataPoint(long timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public static DataPoint fromPair(Pair pair) {
        return new DataPoint(pair.getTimestamp(), pair.getDoubleValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPoint dataPoint = (DataPoint) o;
        if (timestamp != dataPoint.timestamp) {
            System.err.println(timestamp + " is different from " + dataPoint.timestamp);
        }
        return timestamp == dataPoint.timestamp &&
                Double.compare(dataPoint.value, value) == 0;
    }
}
