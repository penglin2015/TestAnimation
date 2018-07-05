package com.xuyao.prancelib.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Toast;

import com.xuyao.prancelib.base.BaseUtilLiveRecyclerViewAdapter;
import com.xuyao.prancelib.base.BaseUtilRecyclerViewAdapter;
import com.xuyao.prancelib.interfaces.BaseUtilRecyclerOnItemClickListener;

/**
 * Created by T470 on 2018/3/7.
 */

public class BaseRecyclerView extends RecyclerView {
    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    Adapter adapter;

    public BaseRecyclerView setUtilAdapter(Adapter adapter) {
        this.adapter = adapter;
        setAdapter(adapter);
        setFocusable(false);
        return this;
    }

    /**
     * 设置点击事件、
     * 必须先设置适配器然后才能设置点击事件
     *
     * @param baseUtilRecyclerOnItemClickListener
     */
    public void setOnItemClickListener(BaseUtilRecyclerOnItemClickListener baseUtilRecyclerOnItemClickListener) {
        if (adapter instanceof BaseUtilLiveRecyclerViewAdapter) {
            ((BaseUtilLiveRecyclerViewAdapter) adapter).setOnItemClickListener(baseUtilRecyclerOnItemClickListener);
            return;
        }
        if (adapter instanceof BaseUtilRecyclerViewAdapter) {
            ((BaseUtilRecyclerViewAdapter) adapter).setOnItemClickListener(baseUtilRecyclerOnItemClickListener);
            return;
        }
        Toast.makeText(getContext(), "适配器不是自定义适配器，设置点击事件失败", Toast.LENGTH_SHORT).show();

    }

    int dliverColor=0;
    public void setDliverColor(int color){
        this.dliverColor=color;
    }
    /**
     *
     * @param span 每排多少个
     * @param or  横竖的风格
     */
    public void setRecyclerViewStyle(int span,int or,int dliverHeightPx){
        setLayoutManager(new GridLayoutManager(getContext(),span,or,false));
        setItemAnimator(null);

        if(span==1){
            if(or== LinearLayoutManager.VERTICAL){
                SpaceItemVaDecoration spaceItemVaDecoration=new SpaceItemVaDecoration(dliverHeightPx);
                if(dliverColor!=0){
                    spaceItemVaDecoration.setColor(dliverColor);
                }
                addItemDecoration(spaceItemVaDecoration);
            }else{
                SpaceItemHDecoration spaceItemHDecoration=new SpaceItemHDecoration(dliverHeightPx);
                spaceItemHDecoration.setDliverColor(dliverColor);
                addItemDecoration(spaceItemHDecoration);
            }
        }else{
            addItemDecoration(new SpaceItemDecoration(dliverHeightPx,span));
        }
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, isScroll?heightMeasureSpec:expandSpec);
    }

    boolean isScroll=true;

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
