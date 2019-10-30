package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;

import java.util.List;

/**
 * The responsibility of classes implementing this interface is to extract a series of data points from a string.
 * This string can have different formats. For example, in the "devops" format
 * (data generated with the time series benchmark series with the cassandra option), the typical string looks something
 * like the following:
 * series_bigint,cpu,hostname=host_0,region=ap-southeast-1,datacenter=ap-southeast-1a,rack=14,os=Ubuntu16.04LTS,arch=x64,team=LON,service=0,service_version=1,service_environment=production,usage_user,2019-01-01,1546300800000000000,21series_bigint,cpu,hostname=host_0,region=ap-southeast-1,datacenter=ap-southeast-1a,rack=14,os=Ubuntu16.04LTS,arch=x64,team=LON,service=0,service_version=1,service_environment=production,usage_user,2019-01-01,1546300800000000000,21
 */
public interface NamedDataPointExtractor {

    /**
     * This method is called by {@link Deserializer} for each line of the time series file.
     * Implementing this method should depend on the format y
     * @param string a line of a time series file
     * @return a list of named data points. The list usually contains only one element,
     * except for {@link TaxiNamedDataPointExtractor}
     */
    List<NamedDataPoint> extract(String string);
}
