package com.teketik.cip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CorrelationKeyProcessorTest {

    private class NoAnnotation {
    }

    @Test
    public void testNoAnnotation() {
        try {
            new CorrelationKeyProcessor<>(NoAnnotation.class);
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
            new CorrelationKeyProcessor<>(MultipleAnnotation.class);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
            Assertions.assertEquals("class com.teketik.cip.CorrelationKeyProcessorTest$MultipleAnnotation contains interface com.teketik.cip.CorrelationKey more than once.", e.getMessage());
        }
    }

    @Test
    public void testNullKey() {
        final CorrelationKeyProcessor<String, TestEntryA> correlationKeyProcessor = new CorrelationKeyProcessor<>(TestEntryA.class);
        try {
            correlationKeyProcessor.apply(new TestEntryA(null));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
            Assertions.assertEquals("Null key value in entry of class com.teketik.cip.TestEntryA.", e.getMessage());
        }
    }

    private class NotComparable {
        @CorrelationKey Object key1 = new Object() {
            @Override
            public String toString() {
                return "{not comparable object}";
            }
        };
    }

    @Test
    public void testNotComparable() {
        final CorrelationKeyProcessor<String, NotComparable> correlationKeyProcessor = new CorrelationKeyProcessor<>(NotComparable.class);
        try {
            correlationKeyProcessor.apply(new NotComparable());
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
            Assertions.assertEquals("{not comparable object} in class com.teketik.cip.CorrelationKeyProcessorTest$NotComparable is not Comparable.", e.getMessage());
        }
    }

    @Test
    public void testOk() {
        final CorrelationKeyProcessor<String, TestEntryA> correlationKeyProcessor = new CorrelationKeyProcessor<>(TestEntryA.class);
        Assertions.assertEquals("key", correlationKeyProcessor.apply(new TestEntryA("key")));
    }

}
