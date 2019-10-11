package com.dovidio.tsbenchmark.deserializer;

import com.dovidio.tsbenchmark.NamedDataPoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DynatraceNamedDataPointExtractorTest {

    DynatraceNamedDataPointExtractor target = new DynatraceNamedDataPointExtractor();

    @Test
    public void extractNullDataPoint() {
        NamedDataPoint actual = target.extract("HOST-1010001,1L,null").get(0);

        assertNull(actual);
    }

    @Test
    public void extract() {
        NamedDataPoint actual = target.extract("HOST-1010001,1,0.5").get(0);

        assertEquals(actual.name, "HOST-1010001");
        assertEquals(actual.timestamp, 1L);
        assertEquals(actual.value, 0.5, Double.MIN_VALUE);
    }
}