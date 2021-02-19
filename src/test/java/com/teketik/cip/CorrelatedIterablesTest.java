package com.teketik.cip;

import static com.teketik.cip.TestUtils.listOf;
import static com.teketik.cip.TestUtils.mapOf;

import com.teketik.cip.CorrelatedIterables.CorrelationDoubleStreamConsumer;
import com.teketik.cip.CorrelatedIterables.CorrelationQuadrupleStreamConsumer;
import com.teketik.cip.CorrelatedIterables.CorrelationQuintupleStreamConsumer;
import com.teketik.cip.CorrelatedIterables.CorrelationTripleStreamConsumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorrelatedIterablesTest {

    @Test
    public void testDouble() {
        final Map<String, List<Object>> resultMap = new HashMap<>();
        final List<TestEntryA> testEntriesA = listOf(new TestEntryA("1"), new TestEntryA("2"));
        final List<TestEntryB> testEntriesB = listOf(new TestEntryB("2"), new TestEntryB("3"));
        CorrelatedIterables.correlate(
                testEntriesA.iterator(), TestEntryA.class,
                testEntriesB.iterator(), TestEntryB.class,
                new CorrelationDoubleStreamConsumer<String, TestEntryA, TestEntryB>() {

                    @Override
                    public void consume(String key, List<TestEntryA> aElements, List<TestEntryB> bElements) {
                        final List<Object> arrayList = new ArrayList<>();
                        arrayList.addAll(aElements);
                        arrayList.addAll(bElements);
                        resultMap.put(key, arrayList);
                    }
                }
        );
        Assertions.assertEquals(
                mapOf(
                        "1", listOf(testEntriesA.get(0)),
                        "2", listOf(testEntriesA.get(1), testEntriesB.get(0)),
                        "3", listOf(testEntriesB.get(1))
                ),
                resultMap
        );
    }

    @Test
    public void testTriple() {
        final Map<String, List<Object>> resultMap = new HashMap<>();
        final List<TestEntryA> testEntriesA = listOf(new TestEntryA("1"), new TestEntryA("2"));
        final List<TestEntryB> testEntriesB = listOf(new TestEntryB("2"), new TestEntryB("3"));
        final List<TestEntryC> testEntriesC = listOf(new TestEntryC("0"), new TestEntryC("2"));
        CorrelatedIterables.correlate(
                testEntriesA.iterator(), TestEntryA.class,
                testEntriesB.iterator(), TestEntryB.class,
                testEntriesC.iterator(), TestEntryC.class,
                new CorrelationTripleStreamConsumer<String, TestEntryA, TestEntryB, TestEntryC>() {
                    @Override
                    public void consume(String key, List<TestEntryA> aElements, List<TestEntryB> bElements, List<TestEntryC> cElements) {
                        final List<Object> arrayList = new ArrayList<>();
                        arrayList.addAll(aElements);
                        arrayList.addAll(bElements);
                        arrayList.addAll(cElements);
                        resultMap.put(key, arrayList);
                    }
                }
        );
        Assertions.assertEquals(
                mapOf(
                        "0", listOf(testEntriesC.get(0)),
                        "1", listOf(testEntriesA.get(0)),
                        "2", listOf(testEntriesA.get(1), testEntriesB.get(0), testEntriesC.get(1)),
                        "3", listOf(testEntriesB.get(1))
                ),
                resultMap
        );
    }

    @Test
    public void testQuadruple() {
        final Map<String, List<Object>> resultMap = new HashMap<>();
        final List<TestEntryA> testEntriesA = listOf(new TestEntryA("1"), new TestEntryA("2"));
        final List<TestEntryB> testEntriesB = listOf(new TestEntryB("2"), new TestEntryB("3"));
        final List<TestEntryC> testEntriesC = listOf(new TestEntryC("0"), new TestEntryC("2"));
        final List<TestEntryD> testEntriesD = listOf(new TestEntryD("1"), new TestEntryD("2"));
        CorrelatedIterables.correlate(
                testEntriesA.iterator(), TestEntryA.class,
                testEntriesB.iterator(), TestEntryB.class,
                testEntriesC.iterator(), TestEntryC.class,
                testEntriesD.iterator(), TestEntryD.class,
                new CorrelationQuadrupleStreamConsumer<String, TestEntryA, TestEntryB, TestEntryC, TestEntryD>() {
                    @Override
                    public void consume(String key, List<TestEntryA> aElements, List<TestEntryB> bElements, List<TestEntryC> cElements, List<TestEntryD> dElements) {
                        final List<Object> arrayList = new ArrayList<>();
                        arrayList.addAll(aElements);
                        arrayList.addAll(bElements);
                        arrayList.addAll(cElements);
                        arrayList.addAll(dElements);
                        resultMap.put(key, arrayList);
                    }
                }
        );
        Assertions.assertEquals(
                mapOf(
                        "0", listOf(testEntriesC.get(0)),
                        "1", listOf(testEntriesA.get(0), testEntriesD.get(0)),
                        "2", listOf(testEntriesA.get(1), testEntriesB.get(0), testEntriesC.get(1), testEntriesD.get(1)),
                        "3", listOf(testEntriesB.get(1))
                ),
                resultMap
        );
    }

    @Test
    public void testQuintuple() {
        final Map<String, List<Object>> resultMap = new HashMap<>();
        final List<TestEntryA> testEntriesA = listOf(new TestEntryA("1"), new TestEntryA("2"));
        final List<TestEntryB> testEntriesB = listOf(new TestEntryB("2"), new TestEntryB("3"));
        final List<TestEntryC> testEntriesC = listOf(new TestEntryC("0"), new TestEntryC("2"));
        final List<TestEntryD> testEntriesD = listOf(new TestEntryD("1"), new TestEntryD("2"));
        final List<TestEntryE> testEntriesE = listOf(new TestEntryE("3"), new TestEntryE("3"));
        CorrelatedIterables.correlate(
                testEntriesA.iterator(), TestEntryA.class,
                testEntriesB.iterator(), TestEntryB.class,
                testEntriesC.iterator(), TestEntryC.class,
                testEntriesD.iterator(), TestEntryD.class,
                testEntriesE.iterator(), TestEntryE.class,
                new CorrelationQuintupleStreamConsumer<String, TestEntryA, TestEntryB, TestEntryC, TestEntryD, TestEntryE>() {
                    @Override
                    public void consume(String key, List<TestEntryA> aElements, List<TestEntryB> bElements, List<TestEntryC> cElements, List<TestEntryD> dElements, List<TestEntryE> eElements) {
                        final List<Object> arrayList = new ArrayList<>();
                        arrayList.addAll(aElements);
                        arrayList.addAll(bElements);
                        arrayList.addAll(cElements);
                        arrayList.addAll(dElements);
                        arrayList.addAll(eElements);
                        resultMap.put(key, arrayList);
                    }
                }
        );
        Assertions.assertEquals(
                mapOf(
                        "0", listOf(testEntriesC.get(0)),
                        "1", listOf(testEntriesA.get(0), testEntriesD.get(0)),
                        "2", listOf(testEntriesA.get(1), testEntriesB.get(0), testEntriesC.get(1), testEntriesD.get(1)),
                        "3", listOf(testEntriesB.get(1), testEntriesE.get(0), testEntriesE.get(1))
                ),
                resultMap
        );
    }
}
