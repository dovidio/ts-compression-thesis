package com.dovidio.tsbenchmark;

import java.util.List;

public class CompressedTimeSeries<T> {

    public final int initialLength;
    public final List<T> compressedValues;

    public CompressedTimeSeries(int initialLength, List<T> compressedValues) {
        this.initialLength = initialLength;
        this.compressedValues = compressedValues;
    }

    public int byteSize() {
        int multiplier = compressedValues.get(0) instanceof Long ? 8 : 1;
        return compressedValues.size() * multiplier;
    }
}
