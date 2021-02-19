package com.teketik.cip;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Function;

//TODO check if comparable
class CorrelationKeyProcessor<K extends Comparable<K>, T> implements Function<T, K>{

    private final Field keyField;

    public CorrelationKeyProcessor(Class<?> annotatedClass) {
        this.keyField = findField(annotatedClass, CorrelationKey.class);
    }

    static Field findField(Class<?> classs, Class<? extends Annotation> ann) {
        Field foundField = null;
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    if (foundField != null) {
                        throw new IllegalArgumentException(c + " contains " + ann + " more than once.");
                    }
                    foundField = field;
                }
            }
            c = c.getSuperclass();
        }
        if (foundField == null) {
            throw new IllegalArgumentException(ann + " not found in " + c + ".");
        }
        foundField.setAccessible(true);
        return foundField;
    }

    @Override
    public K apply(T object) {
        final Object keyValue;
        try {
            keyValue = keyField.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
        if (keyValue == null) {
            throw new IllegalArgumentException("Null key value in entry of " + object.getClass() + ".");
        }
        return (K) keyValue;
    }
}
