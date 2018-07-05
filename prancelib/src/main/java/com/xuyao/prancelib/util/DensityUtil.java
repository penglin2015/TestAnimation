package com.xuyao.prancelib.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }

    /**
     * 获得屏幕的高度
     *
     * @param c
     * @return
     */
    public static int getDisPlayHeight(Context c) {
        return c.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得屏幕的宽度
     *
     * @param c
     * @return
     */
    public static int getDisPlayWidth(Context c) {
        return c.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取顶部状态栏高度
     *
     * @param mActivity
     * @return
     */
    public static int getStatusBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        LogUtils.v("dbw", "Status height:" + height);
        return height;
    }

    /**
     * 获取底部状态的高度
     *
     * @param mActivity
     * @return
     */
    public static int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        LogUtils.v("dbw", "Navi height:" + height);
        return height;
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 获得父容器的试图
     *
     * @param c
     * @return
     */
    public static View getRootView(Activity c) {
        return c.getWindow().getDecorView().findViewById(android.R.id.content);
    }


    /**
     * dp  转  px
     *
     * @param context
     * @param dp
     * @return
     */
    public static float dpToPx(Context context, float dp) {

        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }


//	TypedValue.applyDimension()方法的功能就是把非标准尺寸转换成标准尺寸, 如:
//	dp->px:  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
//	in->px: TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 20, context.getResources().getDisplayMetrics());
//	mm->px: TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 20, context.getResources().getDisplayMetrics());
//	pt->px: TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 20, context.getResources().getDisplayMetrics());
//	sp->px: TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics());

    public static float pxToDp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    /**
     * 获得对应的dp宽度
     * @param context
     * @param ratio
     * @return
     */
    public static float getWidthForRatioWidthDp(Context context,float ratio){
        float dpWidth=pxToDp(context,getDisPlayWidth(context));
        float returnDpWidth=dpWidth*ratio;
        return returnDpWidth;
    }

    /**
     * 获得对应比例的px宽度
     * @param context
     * @param ratio
     * @return
     */
    public static float getWidthForRatioWidthPx(Context context,float ratio){
        float pxWidth=getDisPlayWidth(context);
        float returnDpWidth=pxWidth*ratio;
        return returnDpWidth;
    }


    /**
     * 获得对应比例的高度
     * @param context
     * @param ratio
     * @return
     */
    public static float getHeightForRatioHeightPx(Context context,float ratio){
        int pxHeight=getDisPlayHeight(context);
        return pxHeight*ratio;
    }

    /**
     * 获得对应比例的高度
     * @param context
     * @param ratio
     * @return
     */
    public static float getHeightForRatioHeightDp(Context context,float ratio){
        float dpHeight=pxToDp(context,getDisPlayHeight(context));
        return dpHeight*ratio;
    }

}