package com.teketik.cip;

import java.util.Iterator;

/**
 * Definition containing the related {@link #type} of an {@link Iterator} to be used in {@link CorrelatedIterable}.
 * @param <K> the type of the key
 * @param <T> the type of the values in the {@link #iterator}
 */
public class IteratorDefinition<K extends Comparable<K>, T> {

    final PeekableEntryIterator<K, T> iterator;
    final Class<T> type;

    public IteratorDefinition(Iterator<T> iterator, Class<T> type) {
        super();
        this.iterator = new PeekableEntryIterator<K, T>(iterator, new CorrelationKeyProcessor<K, T>(type));
        this.type = type;
    }

}
