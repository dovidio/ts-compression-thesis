package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;
import com.github.luben.zstd.Zstd;

public class ZStandard implements Compressor {
    Zstd zstd = new Zstd();

    @Override
    public byte[] compress(TimeSeries timeSeries) {
        byte[] data = TimeSeries.toStream(timeSeries);

        // Compresses the data
        return zstd.compress(data);
    }

    @Override
    public TimeSeries restore(String timeSeriesName, CompressedTimeSeries compressedTimeSeries) {
        byte[] restored = new byte[compressedTimeSeries.initialLength];
        zstd.decompress(restored, compressedTimeSeries.compressedValues);

        return TimeSeries.toTimeSeries(restored);
    }
}
