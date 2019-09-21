package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;

import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Deflate implements Compressor {

    @Override
    public byte[] compress(TimeSeries timeSeries) {
        byte[] data = TimeSeries.toStream(timeSeries);
        byte[] compressed = new byte[data.length];

        Deflater compressor = new Deflater();
        compressor.setInput(data);
        compressor.finish();
        int compressedLength = compressor.deflate(compressed);

        return Arrays.copyOfRange(compressed, 0, compressedLength);
    }

    @Override
    public TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries) {
        Inflater decompressor = new Inflater();
        decompressor.setInput(compressedTimeSeries.compressedValues, 0, compressedTimeSeries.compressedValues.length);
        byte[] result = new byte[compressedTimeSeries.initialLength];

        try {
            decompressor.inflate(result);
        } catch (DataFormatException e) {
            throw new RuntimeException(e);
        }

        return TimeSeries.toTimeSeries(result);
    }
}
