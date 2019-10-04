package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.TimeSeries;

import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class CompressionUtils {

    public static byte[] toStream(TimeSeries timeSeries) {
        byte[] stream = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(timeSeries.dataPoints);

            stream = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stream;
    }

    public static TimeSeries toTimeSeries(String timeSeriesName, byte[] stream) {
        TimeSeries timeSeries = new TimeSeries(timeSeriesName);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(stream);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);) {
            timeSeries.dataPoints = (ArrayList) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return timeSeries;
    }
}
