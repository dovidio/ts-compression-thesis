package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;

import java.util.List;

public interface NamedDataPointExtractor {

    List<NamedDataPoint> extract(String string);
}
