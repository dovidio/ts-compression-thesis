package com.dovidio.tsbenchmark;

import com.google.common.base.Objects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimeSeries implements Serializable {

    private static final long serialVersionUID = 523054992266622630L;

    final String name;
    public List<DataPoint> dataPoints;

    public TimeSeries(String name) {
        this.name = name;
        this.dataPoints = new ArrayList<>(1024);
    }

    void append(long timestamp, double value) {
        this.dataPoints.add(new DataPoint(timestamp, value));
    }

    public static byte[] toStream(TimeSeries timeSeries) {
        byte[] stream = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(timeSeries);
            stream = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stream;
    }

    public static TimeSeries toTimeSeries(byte[] stream) {
        TimeSeries timeSeries;

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(stream);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);) {
            timeSeries = (TimeSeries) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return timeSeries;
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
