package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.DataPoint;
import com.dovidio.tsbenchmark.TimeSeries;
import fi.iki.yak.ts.compression.gorilla.*;

import java.util.ArrayList;
import java.util.List;

public class Gorilla implements Compressor {

    @Override
    public byte[] compress(TimeSeries timeSeries) {

        ByteBufferBitOutput bitOutput = new ByteBufferBitOutput();
        GorillaCompressor gorillaCompressor = new GorillaCompressor(timeSeries.dataPoints.get(0).timestamp, bitOutput);
        for (DataPoint dataPoint : timeSeries.dataPoints)
            gorillaCompressor.addValue(dataPoint.timestamp, dataPoint.value);
        gorillaCompressor.close();

        bitOutput.getByteBuffer().clear();
        byte[] compressedValues = new byte[bitOutput.getByteBuffer().capacity()];
        bitOutput.getByteBuffer().get(compressedValues, 0, compressedValues.length);

        return compressedValues;
    }

    @Override
    public TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries) {
        ByteBufferBitInput bitInput = new ByteBufferBitInput(compressedTimeSeries.compressedValues);
        GorillaDecompressor gorillaDecompressor = new GorillaDecompressor(bitInput);

        List<DataPoint> dataPoints = new ArrayList<>();
        TimeSeries restored = new TimeSeries(timeSeriesName);
        Pair pair = gorillaDecompressor.readPair();

        do {
            DataPoint dataPoint = DataPoint.fromPair(pair);
            dataPoints.add(dataPoint);
            pair = gorillaDecompressor.readPair();
        } while (pair != null);
        restored.dataPoints = dataPoints;

        return restored;
    }
}
