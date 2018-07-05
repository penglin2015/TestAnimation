package com.xuyao.prancelib.interfaces;

import android.view.View;

/**
 * Created by T470 on 2018/3/26.
 */

public interface TitleInterface {

    int setLeftImgRes();
    String setTitle();
    String rightName();
    int setRightImgRes();


    void onClickListener(View v,TitleMode titleMode);

    public enum TitleMode{
        leftimg,rightimg,rightnme,default_
    }
}
