package com.xuyao.prancelib.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017年5月17日 0017.
 */
public class SpaceItemHDecoration extends RecyclerView.ItemDecoration {

    private int space;
    int[] span;
    Paint p;
    int dliverColor;

    public SpaceItemHDecoration(int space, int... span) {
        this.space = space;
        this.span = span;
        p=new Paint();
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.FILL);
        dliverColor=Color.parseColor("#dedede");
        p.setColor(dliverColor);
    }

    public SpaceItemHDecoration setDliverColor(int dliverColor) {
        this.dliverColor = dliverColor;
        p.setColor(dliverColor);
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        int index = parent.getChildLayoutPosition(view);
        int childCount = parent.getChildCount();
        if (index != childCount - 1) {
            outRect.right = space;
        }
        outRect.bottom = 0;
        outRect.top = 0;
        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
        if (index % (span != null && span.length > 0 ? span[0] : 2) == 0) {
            outRect.left = 0;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View itemView=parent.getChildAt(i);
            int index=parent.getChildLayoutPosition(itemView);
            if(index==0){
                continue;
            }
            int left=itemView.getLeft()-space;
            int top=itemView.getTop()+parent.getPaddingTop();
            int right=itemView.getLeft();
            int bottom=itemView.getBottom()-parent.getPaddingBottom();
            c.drawRect(left,top,right,bottom,p);
        }
    }
}