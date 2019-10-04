package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;
import com.dovidio.tsbenchmark.TimeSeries;

public interface NamedDataPointExtractor {

    NamedDataPoint extract(String string);
}
