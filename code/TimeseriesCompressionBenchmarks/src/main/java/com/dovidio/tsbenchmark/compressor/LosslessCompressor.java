package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;

import java.util.ArrayList;
import java.util.List;

public interface LosslessCompressor<T> extends Compressor<T> {

    TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries);

    default List<Byte> toBytesBoxedList(byte[] bytes) {
        List<Byte> list = new ArrayList<>(bytes.length);

        for (byte aByte : bytes) {
            list.add(aByte);
        }

        return list;
    }

    default byte[] toBytesArray(List<Object> objects) {
        byte[] byteArray = new byte[objects.size()];

        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (Byte) objects.get(i);
        }

        return byteArray;
    }
}
