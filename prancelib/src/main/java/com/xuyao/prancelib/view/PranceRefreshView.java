package com.xuyao.prancelib.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import com.andview.refreshview.XRefreshView;


/**
 * Created by T470 on 2018/3/8.
 */

public class PranceRefreshView extends XRefreshView {


    public PranceRefreshView(Context context) {
        super(context);
    }

    public PranceRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setOnRefreshListener(SimpleXRefreshListener simpleXRefreshListener,boolean isRefresh,boolean isLoadMore){
        setPullRefreshEnable(isRefresh);
        setPullLoadEnable(isLoadMore);
        setXRefreshViewListener(simpleXRefreshListener);
    }


    public void resetRefresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefresh();
            }
        },1000);
    }


    public void resetLoadMore(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopLoadMore();
            }
        },1000);
    }


    public void release(){
        resetRefresh();
        resetLoadMore();
    }
}
