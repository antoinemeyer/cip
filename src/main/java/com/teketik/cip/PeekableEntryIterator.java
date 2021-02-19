package com.teketik.cip;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.function.Function;

class PeekableEntryIterator<K extends Comparable<K>, T> implements Iterator<SimpleEntry<K, T>> {

    private final Iterator<T> iterator;
    private final Function<T, K> keyExtractor;

    private SimpleEntry<K, T> last;

    public PeekableEntryIterator(Iterator<T> iterator, Function<T, K> keyExtractor) {
        super();
        this.iterator = iterator;
        this.keyExtractor = keyExtractor;
    }

    @Override
    public boolean hasNext() {
        return last != null || iterator.hasNext();
    }

    public SimpleEntry<K, T> peekNext() {
        if (last == null) {
            last = make(iterator.next());
        }
        return last;
    }

    private SimpleEntry<K, T> make(T next) {
        return new SimpleEntry<>(keyExtractor.apply(next), next);
    }

    @Override
    public SimpleEntry<K, T> next() {
        if (last != null) {
            final SimpleEntry<K, T> lastTmp = last;
            last = null;
            return lastTmp;
        } else {
            return make(iterator.next());
        }
    }

}
