package com.woodys.demos.sorts.model;

import java.util.List;

/**
 * Created by woodys on 2017/11/2.
 */

public class Group<T> {
    //差异性数据
    public List<Step<T>> swapItems;
    //每一步排序的数据
    public List<Step<T>> items;
}
