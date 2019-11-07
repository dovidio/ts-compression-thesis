package com.dovidio.tsbenchmark;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PMC_MRTest {

    @Test
    public void nonCompressibleTS() {
        long interval = 1000;
        long initialTimestamp = System.currentTimeMillis();
        PMC_MR poorsManCompression = new PMC_MR(0, initialTimestamp, interval);
        List<DataPoint> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            poorsManCompression.addValue(i);
            DataPoint d = new DataPoint(initialTimestamp + i * interval, i);
            list.add(d);
        }
        poorsManCompression.finish();
        List<DataPoint> actual = poorsManCompression.toDatapointList();

        Assert.assertEquals(actual, list);
    }

    @Test
    public void entirelyCompressibleTS() {
        PMC_MR poorsManCompression = new PMC_MR(10, System.currentTimeMillis(), 1000);
        List<Integer> integers = Arrays.asList(100, 101, 102, 103, 104);
        for (Integer integer : integers) {
            poorsManCompression.addValue(integer);
        }
        poorsManCompression.finish();

        Assert.assertEquals(poorsManCompression.segmentList.size(), 1);
    }

}