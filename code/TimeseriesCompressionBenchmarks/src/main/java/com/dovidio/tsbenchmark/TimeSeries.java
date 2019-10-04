package com.dovidio.tsbenchmark;

import com.google.common.base.Objects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimeSeries implements Serializable {

    private static final long serialVersionUID = 523054992266622630L;

    public final String name;
    public ArrayList<DataPoint> dataPoints;

    public TimeSeries(String name) {
        this.name = name;
        this.dataPoints = new ArrayList<>(1024);
    }

    public void append(long timestamp, double value) {
        this.dataPoints.add(new DataPoint(timestamp, value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeries that = (TimeSeries) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(dataPoints, that.dataPoints);
    }
}
