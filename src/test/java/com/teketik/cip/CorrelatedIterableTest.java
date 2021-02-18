package com.teketik.cip;

import com.teketik.cip.CorrelatedIterable;
import com.teketik.cip.CorrelatedPayload;
import com.teketik.cip.IteratorDefinition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CorrelatedIterableTest {

    @Test
    public void testEmpty() {
        final CorrelatedIterable correlatedIterable = new CorrelatedIterable();
        final Iterator<CorrelatedPayload> iterator = correlatedIterable.iterator();
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void testMulti() {
        final List<TestEntryA> testEntriesA = listOf(new TestEntryA("1"), new TestEntryA("1"), new TestEntryA("2"), new TestEntryA("2"));
        final List<TestEntryB> testEntriesB = listOf(new TestEntryB("1"), new TestEntryB("1"), new TestEntryB("2"));
        final List<TestEntryC> testEntriesC = listOf(new TestEntryC("1"), new TestEntryC("2"), new TestEntryC("2"));
        final CorrelatedIterable correlatedIterable = new CorrelatedIterable(
                new IteratorDefinition<TestEntryA>(testEntriesA.iterator(), TestEntryA.class),
                new IteratorDefinition<TestEntryB>(testEntriesB.iterator(), TestEntryB.class),
                new IteratorDefinition<TestEntryC>(testEntriesC.iterator(), TestEntryC.class)
        );
        final Iterator<CorrelatedPayload> iterator = correlatedIterable.iterator();
        Assertions.assertEquals(
                new CorrelatedPayload("1", mapOf(
                        TestEntryA.class, listOf(testEntriesA.get(0), testEntriesA.get(1)),
                        TestEntryB.class, listOf(testEntriesB.get(0), testEntriesB.get(1)),
                        TestEntryC.class, listOf(testEntriesC.get(0))
                )),
                iterator.next()
        );
        Assertions.assertEquals(
                new CorrelatedPayload("2", mapOf(
                        TestEntryA.class, listOf(testEntriesA.get(2), testEntriesA.get(3)),
                        TestEntryB.class, listOf(testEntriesB.get(2)),
                        TestEntryC.class, listOf(testEntriesC.get(1), testEntriesC.get(2))
                )),
                iterator.next()
        );
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void testWithoutFirstPresence() {
        final List<TestEntryA> testEntriesA = listOf(new TestEntryA("1"), new TestEntryA("2"), new TestEntryA("3"));
        final List<TestEntryB> testEntriesB = listOf(new TestEntryB("0"), new TestEntryB("1"), new TestEntryB("3"), new TestEntryB("4"));
        final CorrelatedIterable correlatedIterable = new CorrelatedIterable(
                new IteratorDefinition<TestEntryA>(testEntriesA.iterator(), TestEntryA.class),
                new IteratorDefinition<TestEntryB>(testEntriesB.iterator(), TestEntryB.class)
        );
        final Iterator<CorrelatedPayload> iterator = correlatedIterable.iterator();
        Assertions.assertEquals(
                new CorrelatedPayload("0", mapOf(
                        TestEntryB.class, listOf(testEntriesB.get(0))
                )),
                iterator.next()
        );
        Assertions.assertEquals(
                new CorrelatedPayload("1", mapOf(
                        TestEntryA.class, listOf(testEntriesA.get(0)),
                        TestEntryB.class, listOf(testEntriesB.get(1))
                )),
                iterator.next()
        );
        Assertions.assertEquals(
                new CorrelatedPayload("2", mapOf(
                        TestEntryA.class, listOf(testEntriesA.get(1))
                )),
                iterator.next()
        );
        Assertions.assertEquals(
                new CorrelatedPayload("3", mapOf(
                        TestEntryA.class, listOf(testEntriesA.get(2)),
                        TestEntryB.class, listOf(testEntriesB.get(2))
                )),
                iterator.next()
        );
        Assertions.assertEquals(
                new CorrelatedPayload("4", mapOf(
                        TestEntryB.class, listOf(testEntriesB.get(3))
                )),
                iterator.next()
        );
        Assertions.assertFalse(iterator.hasNext());

    }

    //TODO move to utils
    static <T, U> Map<T, U> mapOf(T t1, U u1) {
        return Collections.singletonMap(t1, u1);
    }

    static <T, U> Map<T, U> mapOf(T t1, U u1, T t2, U u2) {
        final HashMap<T, U> map = new HashMap<T, U>();
        map.put(t1, u1);
        map.put(t2, u2);
        return map;
    }

    static <T, U> Map<T, U> mapOf(T t1, U u1, T t2, U u2, T t3, U u3) {
        final HashMap<T, U> map = new HashMap<T, U>();
        map.put(t1, u1);
        map.put(t2, u2);
        map.put(t3, u3);
        return map;
    }

    static <T, U> Map<T, U> mapOf(T t1, U u1, T t2, U u2, T t3, U u3, T t4, U u4) {
        final HashMap<T, U> map = new HashMap<T, U>();
        map.put(t1, u1);
        map.put(t2, u2);
        map.put(t3, u3);
        map.put(t4, u4);
        return map;
    }

    static <T> List<T> listOf(T... ts) {
        return Arrays.asList(ts);
    }
}
