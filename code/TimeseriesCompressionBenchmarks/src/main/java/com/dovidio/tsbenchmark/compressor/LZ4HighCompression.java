package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4SafeDecompressor;

import java.util.Arrays;
import java.util.List;

public class LZ4HighCompression implements LosslessCompressor<Byte> {

    LZ4Factory factory = LZ4Factory.fastestInstance();

    @Override
    public List<Byte> compress(TimeSeries timeSeries) {
        byte[] data = CompressionUtils.toByteArray(timeSeries);
        int initialDataLength = data.length;

        // Compresses the data
        LZ4Compressor lz4Compressor = factory.highCompressor(17);
        int maxCompressedLength = lz4Compressor.maxCompressedLength(initialDataLength);
        byte[] compressed = new byte[maxCompressedLength];
        int compressedLength = lz4Compressor.compress(data, 0, initialDataLength, compressed, 0, maxCompressedLength);

        byte[] bytes = Arrays.copyOfRange(compressed, 0, compressedLength);
        return toBytesBoxedList(bytes);
    }

    @Override
    @SuppressWarnings("unchecked")
    public TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries) {
        LZ4SafeDecompressor decompressor = factory.safeDecompressor();
        byte[] restored = new byte[compressedTimeSeries.initialLength];
        byte[] compressed = toBytesArray(compressedTimeSeries.compressedValues);
        decompressor.decompress(compressed, restored);

        return CompressionUtils.toTimeSeries(timeSeriesName, restored);
    }
}
