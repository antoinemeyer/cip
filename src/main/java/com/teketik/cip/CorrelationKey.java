package com.teketik.cip;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate the field to be used as correlation key when grouping the iterator entries.<br>
 * <strong>Should be {@link Comparable}</strong>.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CorrelationKey {

}
