package com.teketik.cip;

import static com.teketik.cip.TestUtils.listOf;
import static com.teketik.cip.TestUtils.mapOf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

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

}
