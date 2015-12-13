package com.lverzosa.filtering.filterlogic;

/**
 * Created by lorenz on 12/13/15.
 */
public interface FilterMatchFunction<T> {
    boolean isMatch(T matchValue, T columnValue);
}
