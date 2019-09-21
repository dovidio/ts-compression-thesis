package com.dovidio.tsbenchmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PMC_MR implements Iterable<DataPoint> {

    private final float error;
    private final List<PMC_MRSegment> segmentList;
    private final long interval;
    private final long firstTimestamp;
    private double max;
    private double min;
    private int repetitions;
    private int totalValues;

    PMC_MR(float error, long firstTimestamp, long interval) {
        this.error = error;
        this.max = Double.NEGATIVE_INFINITY;
        this.min = Double.POSITIVE_INFINITY;
        this.interval = interval;
        this.firstTimestamp = firstTimestamp;
        repetitions = 0;
        totalValues = 0;
        segmentList = new ArrayList<>(256);
    }

    void addValue(double value) {
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

    void finish() {
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
                return index < totalValues - 1;
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

    private static class PMC_MRSegment {
        private final double value;
        private final int repetitions;

        PMC_MRSegment(double value, int repetitions) {
            this.value = value;
            this.repetitions = repetitions;
        }
    }
}
