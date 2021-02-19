package com.teketik.cip;

import java.util.Iterator;

/**
 * {@link Iterable} that allows sequential iteration of multiple sorted {@link Iterator}s using a common {@link CorrelationKey}.<br>
 * @see CorrelatedIterables for convenient wrappers.
 */
public class CorrelatedIterable<K extends Comparable<K>> implements Iterable<CorrelatedPayload<K>> {

    private final IteratorDefinition<?>[] iterators;

    public CorrelatedIterable(IteratorDefinition<?>... iterators) {
        this.iterators = iterators;
    }

    @Override
    public Iterator<CorrelatedPayload<K>> iterator() {
        return new CorrelatedIterator();
    }

    private class CorrelatedIterator implements Iterator<CorrelatedPayload<K>> {

        @Override
        public boolean hasNext() {
            for (IteratorDefinition<?> iteratorDefinition : iterators) {
                if (iteratorDefinition.iterator.hasNext()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public CorrelatedPayload next() {
            final Comparable nextProcessKey = determineNextProcessKey();
            return buildPayload(nextProcessKey);
        }

        private Comparable determineNextProcessKey() {
            Comparable lowestNextKey = null;
            for (IteratorDefinition<?> iteratorDefinition : iterators) {
                if (iteratorDefinition.iterator.hasNext()) {
                    final Comparable nextItemKey = iteratorDefinition.iterator.peekNext().getKey();
                    if (lowestNextKey == null || nextItemKey.compareTo(lowestNextKey) < 0) {
                        lowestNextKey = nextItemKey;
                    }
                }
            }
            return lowestNextKey;
        }

        private CorrelatedPayload buildPayload(final Comparable nextProcessKey) {
            final CorrelatedPayload correlatedPayload = new CorrelatedPayload(nextProcessKey);
            for (IteratorDefinition<?> iteratorDefinition : iterators) {
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
