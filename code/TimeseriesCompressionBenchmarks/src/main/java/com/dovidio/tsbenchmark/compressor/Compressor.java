package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.TimeSeries;

import java.util.List;

public interface Compressor<T> {

    List<T> compress(TimeSeries timeSeries);
}
