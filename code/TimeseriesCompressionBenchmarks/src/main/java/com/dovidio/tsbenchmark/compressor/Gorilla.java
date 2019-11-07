package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.DataPoint;
import com.dovidio.tsbenchmark.TimeSeries;
import fi.iki.yak.ts.compression.gorilla.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Gorilla implements LosslessCompressor<Long> {

    @Override
    public List<Long> compress(TimeSeries timeSeries) {

        LongArrayOutput bitOutput = new LongArrayOutput();
        GorillaCompressor gorillaCompressor = new GorillaCompressor(timeSeries.dataPoints.get(0).timestamp, bitOutput);
        for (DataPoint dataPoint : timeSeries.dataPoints)
            gorillaCompressor.addValue(dataPoint.timestamp, dataPoint.value);
        gorillaCompressor.close();

        return Arrays.stream(bitOutput.getLongArray()).boxed().collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries) {
        long[] longs = compressedTimeSeries.compressedValues.stream()
                .mapToLong(o -> ((Long) o))
                .toArray();
        LongArrayInput bitInput = new LongArrayInput(longs);
        GorillaDecompressor gorillaDecompressor = new GorillaDecompressor(bitInput);

        ArrayList<DataPoint> dataPoints = new ArrayList<>();
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
