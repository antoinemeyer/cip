package com.teketik.cip;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TestUtils {

    private TestUtils() {
    }

    static <T, U> Map<T, U> mapOf(T t1, U u1) {
        return Collections.singletonMap(t1, u1);
    }

    static <T, U> Map<T, U> mapOf(T t1, U u1, T t2, U u2) {
        final HashMap<T, U> map = new HashMap<T, U>();
        map.put(t1, u1);
        map.put(t2, u2);
        return map;
    }

    static <T, U> Map<T, U> mapOf(T t1, U u1, T t2, U u2, T t3, U u3) {
        final HashMap<T, U> map = new HashMap<T, U>();
        map.put(t1, u1);
        map.put(t2, u2);
        map.put(t3, u3);
        return map;
    }

    static <T, U> Map<T, U> mapOf(T t1, U u1, T t2, U u2, T t3, U u3, T t4, U u4) {
        final HashMap<T, U> map = new HashMap<T, U>();
        map.put(t1, u1);
        map.put(t2, u2);
        map.put(t3, u3);
        map.put(t4, u4);
        return map;
    }

    static <T> List<T> listOf(T... ts) {
        return Arrays.asList(ts);
    }

}