package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.CompressedTimeSeries;
import com.dovidio.tsbenchmark.TimeSeries;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@SuppressWarnings("unchecked")
public class GorillaTest {

    Gorilla target = new Gorilla();

    @Test
    public void compress() {
        TimeSeries timeSeries = new TimeSeries("name");
        timeSeries.append(1L, 1);
        timeSeries.append(2L, 2);
        timeSeries.append(3L, 3);

        List<Long> compress = target.compress(timeSeries);

        TimeSeries actual = target.restore("name", new CompressedTimeSeries(CompressionUtils.toByteArray(timeSeries).length, compress));
        Assert.assertEquals(timeSeries, actual);
    }


}