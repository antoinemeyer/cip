package com.teketik.cip;

import java.util.Iterator;

/**
 * Definition containing the related {@link #type} of an {@link Iterator} to be used in {@link CorrelatedIterable}.
 * @param <T>
 */
public class IteratorDefinition<T> {

    final PeekableEntryIterator<T> iterator;
    final Class<T> type;

    public IteratorDefinition(Iterator<T> iterator, Class<T> type) {
        super();
        this.iterator = new PeekableEntryIterator<T>(iterator, new CorrelationKeyProcessor<T>(type));
        this.type = type;
    }

}
