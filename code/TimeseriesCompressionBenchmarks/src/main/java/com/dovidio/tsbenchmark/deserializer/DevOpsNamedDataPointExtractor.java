package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DevOpsNamedDataPointExtractor implements NamedDataPointExtractor {
    @Override
    public List<NamedDataPoint> extract(String string) {
        // the DevOps format is the following:
        // series_bigint,cpu,hostname=host_0,region=ap-southeast-1,datacenter=ap-southeast-1a,rack=14,os=Ubuntu16.04LTS,arch=x64,team=LON,service=0,service_version=1,service_environment=production,usage_user,2019-01-01,1546300800000000000,21
        // for the purpose of this benchmark we are not really interested in all the properties listed here
        // so we can join all the values until the data together to form the string of the data point
        // the last two values are the timestamp and the value of the datapoint

        String[] parts = string.split(",");
        String timeSeriesKey = String.join(",", Arrays.copyOfRange(parts, 1, parts.length - 3));
        long timestamp = Long.parseLong(parts[parts.length - 2]) / 1000;
        double value = Double.parseDouble(parts[parts.length - 1]);
        return Collections.singletonList(new NamedDataPoint(timeSeriesKey, timestamp, value));
    }
}
