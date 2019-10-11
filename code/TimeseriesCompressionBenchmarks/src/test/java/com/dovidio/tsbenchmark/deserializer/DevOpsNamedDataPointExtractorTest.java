package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;
import org.junit.Assert;
import org.junit.Test;

public class DevOpsNamedDataPointExtractorTest {

    DevOpsNamedDataPointExtractor target = new DevOpsNamedDataPointExtractor();

    @Test
    public void extract() {
        // given
        String input = "series_bigint,disk,hostname=host_0,region=us-east-1,datacenter=us-east-1e,rack=30,os=Ubuntu16.04LTS,arch=x64,team=LON,service=19,service_version=0,service_environment=test,path=/dev/sda0,fstype=btrfs,inodes_total,2019-01-01,1546300800000000000,268435456";

        // when
        NamedDataPoint namedDataPoint = target.extract(input).get(0);

        // then
        Assert.assertEquals("disk,hostname=host_0,region=us-east-1,datacenter=us-east-1e,rack=30,os=Ubuntu16.04LTS,arch=x64,team=LON,service=19,service_version=0,service_environment=test,path=/dev/sda0,fstype=btrfs,inodes_total", namedDataPoint.name);
        Assert.assertEquals(1546300800000000L, namedDataPoint.timestamp);
        Assert.assertEquals(268435456, namedDataPoint.value, 0.1);
    }
}