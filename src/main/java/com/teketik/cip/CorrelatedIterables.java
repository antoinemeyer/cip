package com.teketik.cip;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Collection of convenient {@link CorrelatedIterable} wrappers.
 */
public abstract class CorrelatedIterables {

    private CorrelatedIterables() {
    }

    public interface CorrelationDoubleStreamConsumer<K extends Comparable, A, B> {
        public void consume(K key, List<A> aElements, List<B> bElements);
    }

    public static <K extends Comparable<K>, A, B> void correlate(
            Iterator<A> iteratorA, Class<A> typeA,
            Iterator<B> iteratorB, Class<B> typeB,
            CorrelationDoubleStreamConsumer<K, A, B> streamConsumer
    ) {
        stream(
                new CorrelatedIterable(
                        new IteratorDefinition<K, A>(iteratorA, typeA),
                        new IteratorDefinition<K, B>(iteratorB, typeB)
                )
        ).forEach(payload -> {
            streamConsumer.consume(
                    (K) payload.getKey(),
                    (List<A>) payload.getPayload().getOrDefault(typeA, Collections.emptyList()),
                    (List<B>) payload.getPayload().getOrDefault(typeB, Collections.emptyList())
            );
        });
    }

    public interface CorrelationTripleStreamConsumer<K extends Comparable, A, B, C> {
        public void consume(K key, List<A> aElements, List<B> bElements, List<C> cElements);
    }

    public static <K extends Comparable<K>, A, B, C> void correlate(
            Iterator<A> iteratorA, Class<A> typeA,
            Iterator<B> iteratorB, Class<B> typeB,
            Iterator<C> iteratorC, Class<C> typeC,
            CorrelationTripleStreamConsumer<K, A, B, C> streamConsumer
    ) {
        stream(
                new CorrelatedIterable(
                        new IteratorDefinition<K, A>(iteratorA, typeA),
                        new IteratorDefinition<K, B>(iteratorB, typeB),
                        new IteratorDefinition<K, C>(iteratorC, typeC)
                )
        ).forEach(payload -> {
            streamConsumer.consume(
                    (K) payload.getKey(),
                    (List<A>) payload.getPayload().getOrDefault(typeA, Collections.emptyList()),
                    (List<B>) payload.getPayload().getOrDefault(typeB, Collections.emptyList()),
                    (List<C>) payload.getPayload().getOrDefault(typeC, Collections.emptyList())
            );
        });
    }

    public interface CorrelationQuadrupleStreamConsumer<K extends Comparable, A, B, C, D> {
        public void consume(K key, List<A> aElements, List<B> bElements, List<C> cElements, List<D> dElements);
    }

    public static <K extends Comparable<K>, A, B, C, D> void correlate(
            Iterator<A> iteratorA, Class<A> typeA,
            Iterator<B> iteratorB, Class<B> typeB,
            Iterator<C> iteratorC, Class<C> typeC,
            Iterator<D> iteratorD, Class<D> typeD,
            CorrelationQuadrupleStreamConsumer<K, A, B, C, D> streamConsumer
    ) {
        stream(
                new CorrelatedIterable(
                        new IteratorDefinition<K, A>(iteratorA, typeA),
                        new IteratorDefinition<K, B>(iteratorB, typeB),
                        new IteratorDefinition<K, C>(iteratorC, typeC),
                        new IteratorDefinition<K, D>(iteratorD, typeD)
                )
        ).forEach(payload -> {
            streamConsumer.consume(
                    (K) payload.getKey(),
                    (List<A>) payload.getPayload().getOrDefault(typeA, Collections.emptyList()),
                    (List<B>) payload.getPayload().getOrDefault(typeB, Collections.emptyList()),
                    (List<C>) payload.getPayload().getOrDefault(typeC, Collections.emptyList()),
                    (List<D>) payload.getPayload().getOrDefault(typeD, Collections.emptyList())
            );
        });
    }

    public interface CorrelationQuintupleStreamConsumer<K extends Comparable, A, B, C, D, E> {
        public void consume(K key, List<A> aElements, List<B> bElements, List<C> cElements, List<D> dElements, List<E> eElements);
    }

    public static <K extends Comparable<K>, A, B, C, D, E> void correlate(
            Iterator<A> iteratorA, Class<A> typeA,
            Iterator<B> iteratorB, Class<B> typeB,
            Iterator<C> iteratorC, Class<C> typeC,
            Iterator<D> iteratorD, Class<D> typeD,
            Iterator<E> iteratorE, Class<E> typeE,
            CorrelationQuintupleStreamConsumer<K, A, B, C, D, E> streamConsumer
    ) {
        stream(
                new CorrelatedIterable(
                        new IteratorDefinition<K, A>(iteratorA, typeA),
                        new IteratorDefinition<K, B>(iteratorB, typeB),
                        new IteratorDefinition<K, C>(iteratorC, typeC),
                        new IteratorDefinition<K, D>(iteratorD, typeD),
                        new IteratorDefinition<K, E>(iteratorE, typeE)
                )
        ).forEach(payload -> {
            streamConsumer.consume(
                    (K) payload.getKey(),
                    (List<A>) payload.getPayload().getOrDefault(typeA, Collections.emptyList()),
                    (List<B>) payload.getPayload().getOrDefault(typeB, Collections.emptyList()),
                    (List<C>) payload.getPayload().getOrDefault(typeC, Collections.emptyList()),
                    (List<D>) payload.getPayload().getOrDefault(typeD, Collections.emptyList()),
                    (List<E>) payload.getPayload().getOrDefault(typeE, Collections.emptyList())
            );
        });
    }

    private static Stream<CorrelatedPayload> stream(final CorrelatedIterable correlatedIterables) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        correlatedIterables.iterator(),
                        Spliterator.ORDERED
                ),
                false
        );
    }

}
