package com.xuyao.prancelib.view;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Administrator on 2017年5月17日 0017.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    int[] span;

    public SpaceItemDecoration(int space, int... span) {
        this.space = space;
        this.span = span;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int index = parent.getChildAdapterPosition(view);
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int or = gridLayoutManager.getOrientation();
            if (or == LinearLayoutManager.VERTICAL) {
                if (index / span[0] != 0) {
                    outRect.top = space;
                }
                if (index % span[0] != 0) {
                    outRect.left = space;
                }
            } else if (or == LinearLayoutManager.HORIZONTAL) {
                if (index / span[0] != 0) {
                    outRect.left = space;
                }
                if (index % span[0] != 0) {
                    outRect.top = space;
                }
            }
            outRect.bottom = 0;
            outRect.right = 0;
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int or = linearLayoutManager.getOrientation();
            if (or == LinearLayoutManager.VERTICAL) {
                outRect.bottom = 0;
                outRect.left = 0;
                outRect.right = 0;
                if (index != 0) {
                    outRect.top = space;
                }
            } else if (or == LinearLayoutManager.HORIZONTAL) {
                outRect.bottom = 0;
                if (index != 0) {
                    outRect.left = space;
                }
                outRect.right = 0;
                outRect.top = 0;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            //暂时忽略

            int or = staggeredGridLayoutManager.getOrientation();
            if (or == LinearLayoutManager.VERTICAL) {
                outRect.bottom = 0;
                outRect.left = 0;
                outRect.right = 0;
                if (index != 0) {
                    outRect.top = space;
                }
            } else if (or == LinearLayoutManager.HORIZONTAL) {
                outRect.bottom = 0;
                if (index != 0) {
                    outRect.left = space;
                }
                outRect.right = 0;
                outRect.top = 0;
            }
        }
    }
}