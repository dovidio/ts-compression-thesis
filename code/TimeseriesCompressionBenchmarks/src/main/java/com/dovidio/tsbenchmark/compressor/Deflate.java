package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;

import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Deflate implements LosslessCompressor<Byte> {

    @Override
    public List<Byte> compress(TimeSeries timeSeries) {
        byte[] data = CompressionUtils.toByteArray(timeSeries);
        byte[] compressed = new byte[data.length];

        Deflater compressor = new Deflater();
        compressor.setInput(data);
        compressor.finish();
        int compressedLength = compressor.deflate(compressed);
        byte[] bytes = Arrays.copyOfRange(compressed, 0, compressedLength);
        return toBytesBoxedList(bytes);
    }

    @Override
    @SuppressWarnings("unchecked")
    public TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries) {
        Inflater decompressor = new Inflater();
        byte[] compressed = toBytesArray(compressedTimeSeries.compressedValues);
        decompressor.setInput(compressed, 0, compressed.length);
        byte[] restored = new byte[compressedTimeSeries.initialLength];

        try {
            decompressor.inflate(restored);
        } catch (DataFormatException e) {
            throw new RuntimeException(e);
        }

        return CompressionUtils.toTimeSeries(timeSeriesName, restored);
    }
}
