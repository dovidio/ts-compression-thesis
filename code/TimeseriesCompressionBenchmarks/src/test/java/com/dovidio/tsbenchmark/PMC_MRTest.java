package com.dovidio.tsbenchmark;

import org.junit.Test;

public class PMC_MRTest {

    @Test
    public void nonCompressibleTS() {
        PMC_MR poorsManCompression = new PMC_MR(0, System.currentTimeMillis(), 1000);

        for (int i = 0; i < 5; i++) {
            poorsManCompression.addValue(i);
        }

        int i = 0;
        for (DataPoint dataPoint: poorsManCompression) {
            System.out.println(dataPoint.value);
        }
    }

    @Test
    public void entirelyCompressibleTS() {
        PMC_MR poorsManCompression = new PMC_MR(10, System.currentTimeMillis(), 1000);

        for (int i = 1; i < 4; i++) {
            poorsManCompression.addValue(i / 2.0);
        }

        poorsManCompression.finish();


        for (DataPoint dataPoint: poorsManCompression) {
            System.out.println(dataPoint.value);
        }
    }

}