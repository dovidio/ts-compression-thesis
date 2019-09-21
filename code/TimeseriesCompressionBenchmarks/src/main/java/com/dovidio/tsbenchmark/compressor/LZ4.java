package com.dovidio.tsbenchmark.compressor;


import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;
import net.jpountz.lz4.*;

import java.util.Arrays;

public class LZ4 implements Compressor {

    LZ4Factory factory = LZ4Factory.fastestInstance();

    @Override
    public byte[] compress(TimeSeries timeSeries) {
        byte[] data = TimeSeries.toStream(timeSeries);
        int initialDataLength = data.length;

        // Compresses the data
        LZ4Compressor lz4Compressor = factory.fastCompressor();
        int maxCompressedLength = lz4Compressor.maxCompressedLength(initialDataLength);
        byte[] compressed = new byte[maxCompressedLength];
        int compressedLength = lz4Compressor.compress(data, 0, initialDataLength, compressed, 0, maxCompressedLength);

        return Arrays.copyOfRange(compressed, 0, compressedLength);
    }

    @Override
    public TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries) {
        LZ4SafeDecompressor decompressor = factory.safeDecompressor();
        byte[] restored = new byte[compressedTimeSeries.initialLength];
        decompressor.decompress(compressedTimeSeries.compressedValues, restored);

        return TimeSeries.toTimeSeries(restored);
    }
}
