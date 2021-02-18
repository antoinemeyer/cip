package com.teketik.cip;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.function.Function;

class PeekableEntryIterator<T> implements Iterator<SimpleEntry<Comparable, T>> {

    private final Iterator<T> iterator;
    private final Function<T, ? extends Comparable> keyExtractor;

    private SimpleEntry<Comparable, T> last;

    public PeekableEntryIterator(Iterator<T> iterator, Function<T, ? extends Comparable> keyExtractor) {
        super();
        this.iterator = iterator;
        this.keyExtractor = keyExtractor;
    }

    @Override
    public boolean hasNext() {
        return last != null || iterator.hasNext();
    }

    public SimpleEntry<Comparable, T> peekNext() {
        if (last == null) {
            last = make(iterator.next());
        }
        return last;
    }

    private SimpleEntry<Comparable, T> make(T next) {
        return new SimpleEntry<>(keyExtractor.apply(next), next);
    }

    @Override
    public SimpleEntry<Comparable, T> next() {
        if (last != null) {
            SimpleEntry<Comparable, T> lastTmp = last;
            last = null;
            return lastTmp;
        } else {
            return make(iterator.next());
        }
    }

}
