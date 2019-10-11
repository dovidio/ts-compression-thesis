package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaxiNamedDataPointExtractor implements NamedDataPointExtractor {

    private static DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");

    @Override
    public List<NamedDataPoint> extract(String string) {
        List<NamedDataPoint> dataPoints = new ArrayList<>();
        String[] parts = string.split(",");

        try {
            int VendorID = Integer.parseInt(parts[0]);
            long tpep_pickup_datetime = dateFormat.parse(parts[1]).getTime();
            long tpep_dropoff_datetime = dateFormat.parse(parts[2]).getTime();
            int passenger_count = Integer.parseInt(parts[3]);
            double trip_distance = Double.parseDouble(parts[4]);
            int RatecodeID = Integer.parseInt(parts[5]);
            int PULocationID = Integer.parseInt(parts[6]);
            int DOLocationID = Integer.parseInt(parts[7]);
            int payment_type = Integer.parseInt(parts[8]);
            double fare_amount = Double.parseDouble(parts[9]);
            double extra = Double.parseDouble(parts[10]);
            double mta_tax = Double.parseDouble(parts[11]);
            double tip_amount = Double.parseDouble(parts[12]);
            double tolls_amount = Double.parseDouble(parts[13]);
            double improvement_surcharge = Double.parseDouble(parts[14]);
            double total_amount = Double.parseDouble(parts[15]);

            dataPoints.add(new NamedDataPoint(VendorID + "tpep_dropoff_datetime", tpep_pickup_datetime, tpep_dropoff_datetime));
            dataPoints.add(new NamedDataPoint(VendorID + "passenger_count", tpep_pickup_datetime, passenger_count));
            dataPoints.add(new NamedDataPoint(VendorID + "trip_distance", tpep_pickup_datetime, trip_distance));
            dataPoints.add(new NamedDataPoint(VendorID + "RatecodeID", tpep_pickup_datetime, RatecodeID));
            dataPoints.add(new NamedDataPoint(VendorID + "PULocationID", tpep_pickup_datetime, PULocationID));
            dataPoints.add(new NamedDataPoint(VendorID + "DOLocationID", tpep_pickup_datetime, DOLocationID));
            dataPoints.add(new NamedDataPoint(VendorID + "payment_type", tpep_pickup_datetime, payment_type));
            dataPoints.add(new NamedDataPoint(VendorID + "fare_amount", tpep_pickup_datetime, fare_amount));
            dataPoints.add(new NamedDataPoint(VendorID + "extra", tpep_pickup_datetime, extra));
            dataPoints.add(new NamedDataPoint(VendorID + "mta_tax", tpep_pickup_datetime, mta_tax));
            dataPoints.add(new NamedDataPoint(VendorID + "tip_amount", tpep_pickup_datetime, tip_amount));
            dataPoints.add(new NamedDataPoint(VendorID + "tolls_amount", tpep_pickup_datetime, tolls_amount));
            dataPoints.add(new NamedDataPoint(VendorID + "improvement_surcharge", tpep_pickup_datetime, improvement_surcharge));
            dataPoints.add(new NamedDataPoint(VendorID + "total_amount", tpep_pickup_datetime, total_amount));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataPoints;
    }
}
