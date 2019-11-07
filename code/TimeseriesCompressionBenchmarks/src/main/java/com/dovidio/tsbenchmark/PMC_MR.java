package com.dovidio.tsbenchmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PMC_MR implements Iterable<DataPoint> {

    private final double error;
    public final List<PMC_MRSegment> segmentList;
    private final long interval;
    private final long firstTimestamp;
    private double max;
    private double min;
    private int repetitions;
    private int totalValues;

    /**
     * @param error          error percentage. 1 stands for 100 percent error
     * @param firstTimestamp first timestamp of the series, in milliseconds
     * @param interval       interval between each datapoint, in milliseconds
     */
    public PMC_MR(double error, long firstTimestamp, long interval) {
        this.error = error;
        this.max = Double.NEGATIVE_INFINITY;
        this.min = Double.POSITIVE_INFINITY;
        this.interval = interval;
        this.firstTimestamp = firstTimestamp;
        repetitions = 0;
        totalValues = 0;
        segmentList = new ArrayList<>(256);
    }

    public void addValue(double value) {
        double currentMax = Math.max(value, this.max);
        double currentMin = Math.min(value, this.min);
        double approximation = (currentMax + currentMin) / 2.0;

        if (outsideBound(approximation, value)) {
            approximation = (max + min) / 2.0;
            PMC_MRSegment segment = new PMC_MRSegment(approximation, repetitions);
            segmentList.add(segment);
            min = value;
            max = value;
            repetitions = 1;
        } else {
            repetitions += 1;
            min = currentMin;
            max = currentMax;
        }

        totalValues++;
    }

    public void finish() {
        double approximation = (max + min) / 2;
        PMC_MRSegment segment = new PMC_MRSegment(approximation, repetitions);
        segmentList.add(segment);
    }

    boolean outsideBound(double approximation, double value) {
        double maxBound = value + value * this.error;
        double minBound = value - value * this.error;

        return approximation < minBound || approximation > maxBound;
    }

    @Override
    public Iterator<DataPoint> iterator() {

        return new Iterator<>() {
            int index = 0;
            int segmentIndex = 0;
            int valuesConsumedInTheSegment = 0;

            @Override
            public boolean hasNext() {
                return index < totalValues;
            }

            @Override
            public DataPoint next() {
                PMC_MRSegment segment = segmentList.get(segmentIndex);
                valuesConsumedInTheSegment++;
                if (valuesConsumedInTheSegment >= segment.repetitions) {
                    segmentIndex++;
                    valuesConsumedInTheSegment = 0;
                }
                index++;
                return new DataPoint(firstTimestamp + interval * index, segment.value);
            }
        };
    }

    List<DataPoint> toDatapointList() {
        List<DataPoint> dataPoints = new ArrayList<>();
        int numberOfPreviousDataPoints = 0;

        for (int segmentIndex = 0; segmentIndex < segmentList.size(); segmentIndex++) {
            PMC_MRSegment segment = segmentList.get(segmentIndex);
            for (int i = 0; i < segment.repetitions; i++) {
                long timestamp = firstTimestamp + interval * numberOfPreviousDataPoints++;
                DataPoint d = new DataPoint(timestamp, segment.value);
                dataPoints.add(d);
            }
        }

        return dataPoints;
    }

    public static class PMC_MRSegment {
        private final double value;
        private final int repetitions;

        PMC_MRSegment(double value, int repetitions) {
            this.value = value;
            this.repetitions = repetitions;
        }
    }
}
