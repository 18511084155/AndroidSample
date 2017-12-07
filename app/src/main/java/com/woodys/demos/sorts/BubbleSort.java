package com.woodys.demos.sorts;

/**
 * Created by woodys on 2017/11/2.
 */


public class BubbleSort<T extends Comparable<T>> extends ISort {

    public BubbleSort(T[] items) {
        super(items);
    }

    private void swap(T[] arrs, int i, int j) {
        T tmp = arrs[i];
        arrs[i] = arrs[j];
        arrs[j] = tmp;
    }

    @Override
    public T[] sort() {
        for (int i = items.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if(items[j].compareTo(items[j+1])>0){
                    swap((T[]) items, j, j + 1);
                }
            }

        }
        return (T[]) items;
    }

    @Override
    public String getName() {
        return "冒泡排序";
    }
}