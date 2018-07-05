package com.xuyao.prancelib.interfaces;

import android.os.Bundle;

/**
 * Created by T470 on 2018/1/27.
 */

public interface BaseCallBack {
    int layoutId();
    void init(Bundle... bundle);
    void initInData();
    void initView();
    void initEvent();
    void initEndData();
}
