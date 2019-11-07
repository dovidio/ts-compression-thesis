package com.dovidio.tsbenchmark.compressor;

import com.dovidio.tsbenchmark.DataPoint;
import com.dovidio.tsbenchmark.PMC_MR;
import com.dovidio.tsbenchmark.TimeSeries;

import java.util.Collections;
import java.util.List;

public class PMC_MR_Compressor implements Compressor<PMC_MR.PMC_MRSegment> {

    private final double error;
    private final long interval;

    public PMC_MR_Compressor(double error, long interval) {
        this.error = error;
        this.interval = interval;
    }

    @Override
    public List<PMC_MR.PMC_MRSegment> compress(TimeSeries timeSeries) {
        if (timeSeries.dataPoints.size() > 0) {
            PMC_MR poorManCompression = new PMC_MR(error, timeSeries.dataPoints.get(0).timestamp, interval);
            for (DataPoint dataPoint : timeSeries.dataPoints) {
                poorManCompression.addValue(dataPoint.value);
            }
            poorManCompression.finish();
            return poorManCompression.segmentList;
        }

        return Collections.emptyList();
    }
}
