package com.dovidio.tsbenchmark;

public class CompressedTimeSeries {

    public final int initialLength;
    public final byte[] compressedValues;

    CompressedTimeSeries(int initialLength, byte[] compressedValues) {
        this.initialLength = initialLength;
        this.compressedValues = compressedValues;
    }
}
