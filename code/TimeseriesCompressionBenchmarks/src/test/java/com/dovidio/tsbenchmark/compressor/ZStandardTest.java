package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ZStandardTest {

    ZStandard target = new ZStandard();

    @Test
    public void compress() {
        TimeSeries timeSeries = new TimeSeries("name");
        timeSeries.append(1L, 1);
        timeSeries.append(2L, 2);
        timeSeries.append(3L, 3);

        List<Byte> compress = target.compress(timeSeries);

        CompressedTimeSeries<Byte> byteCompressedTimeSeries = new CompressedTimeSeries<>(CompressionUtils.toStream(timeSeries).length, compress);

        TimeSeries name = target.restore("name", byteCompressedTimeSeries);

        Assert.assertEquals(timeSeries, name);
    }

}