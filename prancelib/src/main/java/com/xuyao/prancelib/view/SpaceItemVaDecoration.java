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
public class SpaceItemVaDecoration extends RecyclerView.ItemDecoration {

    private int bottomSpace;
    Paint paint;
    int color;

    public SpaceItemVaDecoration(int bottomSpace) {
        this.bottomSpace = bottomSpace;
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        color= Color.parseColor("#dedede");
        paint.setColor(color);
    }



    public SpaceItemVaDecoration setColor(int color) {
        this.color = color;
        paint.setColor(color);
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
//        outRect.left = bottomSpace;
        if(parent.getChildLayoutPosition(view)!=parent.getAdapter().getItemCount()-1){
            outRect.bottom = bottomSpace;
        }
        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.left = 0;
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();

        for ( int i = 0; i < childCount; i++ ) {
            View view = parent.getChildAt(i);
            float dividerTop = view.getTop() - bottomSpace;
            float dividerLeft = parent.getPaddingLeft();
            float dividerBottom = view.getTop();
            float dividerRight = parent.getWidth() - parent.getPaddingRight();
            c.drawRect(dividerLeft,dividerTop,dividerRight,dividerBottom,paint);
        }
    }
}