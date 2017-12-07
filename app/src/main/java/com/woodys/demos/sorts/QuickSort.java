package com.woodys.demos.sorts;

/**
 * Created by woodys on 2017/11/01.
 */

public class QuickSort<T extends Comparable<T>> extends ISort {

    public QuickSort(T[] items) {
        super(items);
    }

    public int getMiddle(T[] list, int low, int high) {
        //数组的第一个作为中轴
        T tmp = list[low];
        while (low < high) {
            while (low < high && list[high].compareTo(tmp) > 0) {
                high --;
            }
            //比中轴小的记录移到低端
            list[low] = list[high];
            while (low < high && list[low].compareTo(tmp) < 0) {
                low ++;
            }
            //比中轴大的记录移到高端
            list[high] = list[low];
        }
        //中轴记录到尾
        list[low] = tmp;
        //返回中轴的位置
        return low;
    }

    public void _quickSort(T[] list, int low, int high) {
        if (low < high) {
            //将list数组进行一分为二
            int middle = getMiddle(list, low, high);
            //对低字表进行递归排序
            _quickSort(list, low, middle - 1);
            //对高字表进行递归排序
            _quickSort(list, middle + 1, high);
        }
    }


    @Override
    public T[] sort() {
        //查看数组是否为空
        if (items.length > 0) {
            _quickSort((T[]) items, 0, items.length - 1);
        }
        return (T[]) items;
    }

    @Override
    public String getName() {
        return "快速排序";
    }
}