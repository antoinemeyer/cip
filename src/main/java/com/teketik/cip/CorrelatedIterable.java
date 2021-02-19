package com.teketik.cip;

import java.util.Iterator;

/**
 * {@link Iterable} that allows sequential iteration of multiple sorted {@link Iterator}s using a common {@link CorrelationKey}.<br>
 * @see CorrelatedIterables for convenient wrappers.
 * @param <K> the type of the key
 */
public class CorrelatedIterable<K extends Comparable<K>> implements Iterable<CorrelatedPayload<K>> {

    private final IteratorDefinition<K, ?>[] iterators;

    @SafeVarargs
    public CorrelatedIterable(IteratorDefinition<K, ?>... iterators) {
        this.iterators = iterators;
    }

    @Override
    public Iterator<CorrelatedPayload<K>> iterator() {
        return new CorrelatedIterator();
    }

    private class CorrelatedIterator implements Iterator<CorrelatedPayload<K>> {

        @Override
        public boolean hasNext() {
            for (IteratorDefinition<K, ?> iteratorDefinition : iterators) {
                if (iteratorDefinition.iterator.hasNext()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public CorrelatedPayload<K> next() {
            final K nextProcessKey = determineNextProcessKey();
            return buildPayload(nextProcessKey);
        }

        private K determineNextProcessKey() {
            K lowestNextKey = null;
            for (IteratorDefinition<K, ?> iteratorDefinition : iterators) {
                if (iteratorDefinition.iterator.hasNext()) {
                    final K nextItemKey = iteratorDefinition.iterator.peekNext().getKey();
                    if (lowestNextKey == null || nextItemKey.compareTo(lowestNextKey) < 0) {
                        lowestNextKey = nextItemKey;
                    }
                }
            }
            return lowestNextKey;
        }

        private CorrelatedPayload<K> buildPayload(final K nextProcessKey) {
            final CorrelatedPayload<K> correlatedPayload = new CorrelatedPayload<>(nextProcessKey);
            for (IteratorDefinition<K, ?> iteratorDefinition : iterators) {
                while (
                        iteratorDefinition.iterator.hasNext()
                        &&
                        iteratorDefinition.iterator.peekNext().getKey().equals(nextProcessKey)
                ) {
                    correlatedPayload.add(iteratorDefinition.type, iteratorDefinition.iterator.next().getValue());
                }
            }
            return correlatedPayload;
        }

    }

}
