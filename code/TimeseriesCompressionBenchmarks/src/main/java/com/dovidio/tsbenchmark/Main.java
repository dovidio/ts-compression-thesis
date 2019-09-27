/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.dovidio.tsbenchmark;

import com.dovidio.tsbenchmark.compressor.*;
import com.dovidio.tsbenchmark.deserializer.Deserializer;
import com.dovidio.tsbenchmark.deserializer.DevOpsNamedDataPointExtractor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@SuppressWarnings("unchecked")
public class Main {

    static Map<String, TimeSeries> timeSeriesHashMap;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: TsBenchmark gorilla/lz4/deflate/zstandard");
            System.exit(-1);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // parse file
        Deserializer deserializer = new Deserializer(new DevOpsNamedDataPointExtractor());
        timeSeriesHashMap = deserializer.deserialize(reader);

        // compress
        String compressionType = args[0].toLowerCase();
        com.dovidio.tsbenchmark.compressor.Compressor compressor;
        switch (compressionType) {
            case "gorilla":
                compressor = new Gorilla();
                break;
            case "lz4":
                compressor = new LZ4();
                break;
            case "deflate":
                compressor = new Deflate();
                break;
            case "zstandard":
                compressor = new ZStandard();
                break;
            default:
                String errorMessage = String.format("Unknown compression method: %s. Allowed compression methods are Gorilla, LZ4, Deflate, ZStandard.\n", compressionType);
                throw new RuntimeException(errorMessage);
        }

        compress(compressor);
    }

    static void compress(Compressor compressor) {
        int initialSizeInBytes = 0;
        long compressedSizeInBytes = 0;
        HashMap<String, CompressedTimeSeries> compressedTimeSeriesMap = new HashMap<>(timeSeriesHashMap.size());

        for (TimeSeries timeSeries : timeSeriesHashMap.values()) {
            initialSizeInBytes += CompressionUtils.toStream(timeSeries).length;
            List<Object> compressedValues = compressor.compress(timeSeries);
            CompressedTimeSeries compressedTimeSeries = new CompressedTimeSeries(CompressionUtils.toStream(timeSeries).length, compressedValues);
            compressedTimeSeriesMap.put(timeSeries.name, compressedTimeSeries);
            compressedSizeInBytes += compressedTimeSeries.byteSize();
        }

        for (Map.Entry<String, CompressedTimeSeries> t : compressedTimeSeriesMap.entrySet()) {
            TimeSeries restored = compressor.restore(t.getKey(), t.getValue());
            if (!Objects.equals(timeSeriesHashMap.get(restored.name), restored)) {
                throw new RuntimeException(String.format("Could not restore compressed value of timeseries %s with compressor %s", restored.name, compressor.getClass().getSimpleName()));
            }
        }

        System.out.printf(Locale.US, "%d,%d,%e", initialSizeInBytes, compressedSizeInBytes, (initialSizeInBytes / (double) compressedSizeInBytes));
    }
}
