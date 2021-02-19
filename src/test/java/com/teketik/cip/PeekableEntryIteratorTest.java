package com.teketik.cip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.function.Function;

public class PeekableEntryIteratorTest {

    @Test
    public void testOriginal() {
        final String[] source = new String[] {"1", "2", "3"};
        final PeekableEntryIterator<String, String> peekableIterator = new PeekableEntryIterator<>(
                Arrays.asList(source).iterator(),
                Function.identity()
        );

        Assertions.assertTrue(peekableIterator.hasNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("1", "1"), peekableIterator.next());
        Assertions.assertTrue(peekableIterator.hasNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("2", "2"), peekableIterator.next());
        Assertions.assertTrue(peekableIterator.hasNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("3", "3"), peekableIterator.next());
        Assertions.assertFalse(peekableIterator.hasNext());
    }

    @Test
    public void testPeek() {
        final String[] source = new String[] {"1", "2", "3"};
        final PeekableEntryIterator<String, String> peekableIterator = new PeekableEntryIterator<>(
                Arrays.asList(source).iterator(),
                Function.identity()
        );

        Assertions.assertTrue(peekableIterator.hasNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("1", "1"), peekableIterator.peekNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("1", "1"), peekableIterator.next());
        Assertions.assertTrue(peekableIterator.hasNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("2", "2"), peekableIterator.peekNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("2", "2"), peekableIterator.peekNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("2", "2"), peekableIterator.next());
        Assertions.assertTrue(peekableIterator.hasNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("3", "3"), peekableIterator.peekNext());
        Assertions.assertTrue(peekableIterator.hasNext());
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("3", "3"), peekableIterator.next());
        Assertions.assertFalse(peekableIterator.hasNext());
    }

}
