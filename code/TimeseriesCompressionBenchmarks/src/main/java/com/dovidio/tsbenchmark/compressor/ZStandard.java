package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;
import com.github.luben.zstd.Zstd;

import java.util.List;

public class ZStandard implements LosslessCompressor<Byte> {
    Zstd zstd = new Zstd();

    @Override
    public List<Byte> compress(TimeSeries timeSeries) {
        byte[] data = CompressionUtils.toByteArray(timeSeries);

        // Compresses the data
        return toBytesBoxedList(zstd.compress(data));
    }

    @Override
    @SuppressWarnings("unchecked")
    public TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries) {
        byte[] restored = new byte[compressedTimeSeries.initialLength];
        byte[] compressed = toBytesArray(compressedTimeSeries.compressedValues);
        zstd.decompress(restored, compressed);

        return CompressionUtils.toTimeSeries(timeSeriesName, restored);
    }
}
