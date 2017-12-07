package com.woodys.demos.sorts;

/**
 * Created by woodys on 2017/11/1.
 */

public abstract class ISort <T extends Comparable<T>>{
    protected T[] items;
    public ISort(T[] items) {
        this.items = items;
    }
    protected abstract T[] sort();
    protected abstract String getName();
}
