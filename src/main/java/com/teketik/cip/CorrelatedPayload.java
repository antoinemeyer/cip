package com.teketik.cip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Output of an iteration of a {@link CorrelatedIterable}.<br/>
 * Contains the common {@link #key} shared by all correlated entries and all the entries stored as a map in {@link #payload}.<br/>
 */
public class CorrelatedPayload<K extends Comparable> {

    final K key;

    final Map<Class<?>, List<Object>> payload;

    public CorrelatedPayload(K key) {
        super();
        this.key = key;
        this.payload = new HashMap<>();
    }

    public CorrelatedPayload(K key, Map<Class<?>, List<Object>> payload) {
        super();
        this.key = key;
        this.payload = payload;
    }

    void add(Class<?> type, Object object) {
        List<Object> list = payload.get(type);
        if (list == null) {
            payload.put(type, list = new ArrayList<>());
        }
        list.add(object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, payload);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CorrelatedPayload other = (CorrelatedPayload) obj;
        return Objects.equals(key, other.key) && Objects.equals(payload, other.payload);
    }

    public K getKey() {
        return key;
    }

    public Map<Class<?>, List<Object>> getPayload() {
        return payload;
    }

}
