package com.xuyao.prancelib.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by T470 on 2018/3/21.
 */

public class ViewDensityUtil {


    /**
     * 设置控件的宽高
     *
     * @param view
     * @param wpx
     * @param hpx
     */
    public static void setViewWh(View view, int wpx, int hpx) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        vlp.width = wpx;
        vlp.height = hpx;
        view.setLayoutParams(vlp);
    }

    /**
     * 设置宽高1:1
     *
     * @param view
     * @param px
     */
    public static void setViewW1x1H(View view, int px) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        vlp.width = px;
        vlp.height = px;
        view.setLayoutParams(vlp);
    }

    /**
     * 设置控件的宽
     *
     * @param view
     * @param widthPx
     */
    public static void setViewWidth(View view, int widthPx) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        vlp.width = widthPx;
        view.setLayoutParams(vlp);
    }


    /**
     * 设置控件的高
     *
     * @param view
     * @param heightPx
     */
    public static void setViewHeight(View view, int heightPx) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        vlp.height = heightPx;
        view.setLayoutParams(vlp);
    }


    /**
     * 根据一个标准1080屏幕来设定控件的宽度
     *
     * @param view
     * @param dp1080Width
     */
    public static void setFor1080PDensityWidth(View view, float dp1080Width) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        float px = DensityUtil.dpToPx(view.getContext(), dp1080Width);
        float pxRatio = px / 1080.f;//比例
        float reallyWidth = pxRatio * ScreenUtils.getScreenWidth(view.getContext());
        vlp.width = (int) reallyWidth;
        view.setLayoutParams(vlp);
    }


    /**
     * 根据1080P换算出占的高度
     *
     * @param view
     * @param dp1080Height
     */
    public static void setFor1080PDensityHeight(View view, float dp1080Height) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        float px = DensityUtil.dpToPx(view.getContext(), dp1080Height);
        float pxRatio = px / 1080.f;//比例
        float reallyHeight = pxRatio * DensityUtil.getDisPlayWidth(view.getContext());
        vlp.height = (int) reallyHeight;
        view.setLayoutParams(vlp);
    }

    /**
     * 已经知道了需要给控件多大的空间，但是需要适配各个屏幕
     *
     * @param view
     * @param dp1080Width
     * @param dp1080Height
     */
    public static void setFor1080PWh(View view, int dp1080Width, int dp1080Height) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        Context context = view.getContext();
        float pxRatio = DensityUtil.dip2px(context, dp1080Width) / 1080f;
        float viewDpRatio = (float) dp1080Width / (float) dp1080Height;
        float reallyWidth = pxRatio * DensityUtil.getDisPlayWidth(context);
        float reallyHeight = reallyWidth / viewDpRatio;
        vlp.width = (int) reallyWidth;
        vlp.height = (int) reallyHeight;
        view.setLayoutParams(vlp);


    }
}
