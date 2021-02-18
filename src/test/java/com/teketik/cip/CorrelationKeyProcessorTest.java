package com.teketik.cip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CorrelationKeyProcessorTest {

    private class NoAnnotation {
    }

    @Test
    public void testNoAnnotation() {
        try {
            new CorrelationKeyProcessor(NoAnnotation.class);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
            Assertions.assertEquals("interface com.teketik.cip.CorrelationKey not found in null.", e.getMessage());
        }
    }

    private class MultipleAnnotation {
        @CorrelationKey String key1;
        @CorrelationKey String key2;
    }

    @Test
    public void testMultipleAnnotations() {
        try {
            new CorrelationKeyProcessor(MultipleAnnotation.class);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
            Assertions.assertEquals("class com.teketik.cip.CorrelationKeyProcessorTest$MultipleAnnotation contains interface com.teketik.cip.CorrelationKey more than once.", e.getMessage());
        }
    }

    @Test
    public void testNullKey() {
        final CorrelationKeyProcessor correlationKeyProcessor = new CorrelationKeyProcessor(TestEntryA.class);
        try {
            correlationKeyProcessor.apply(new TestEntryA(null));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
            Assertions.assertEquals("Null key value in entry of class com.teketik.cip.TestEntryA.", e.getMessage());
        }
    }

    @Test
    public void testOk() {
        final CorrelationKeyProcessor correlationKeyProcessor = new CorrelationKeyProcessor(TestEntryA.class);
        Assertions.assertEquals("key", correlationKeyProcessor.apply(new TestEntryA("key")));
    }

}
