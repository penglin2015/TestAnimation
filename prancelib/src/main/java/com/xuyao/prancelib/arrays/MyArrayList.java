package com.xuyao.prancelib.arrays;

import java.util.ArrayList;
import java.util.List;

public class MyArrayList<V extends Object> {

    List<V> list;

    public MyArrayList(){
        list=new ArrayList<>();
    }

    public MyArrayList put(V t){
        list.add(t);
        return this;
    }

    public List<V> getList() {
        return list;
    }
}
