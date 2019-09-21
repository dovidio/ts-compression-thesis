package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;

public interface Compressor {

    byte[] compress(TimeSeries timeSeries);

    TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries);
}
