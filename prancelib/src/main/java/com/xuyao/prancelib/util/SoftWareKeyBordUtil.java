package com.xuyao.prancelib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by admin on 2017/8/1.
 */

public class SoftWareKeyBordUtil {

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public static void controlKeyboardLayout(final View root,
                                             final View scrollToView, final View isNeedGoneView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                        // 若不可视区域高度大于100，则键盘显示
                        if (rootInvisibleHeight > 100) {
                            int[] location = new int[2];
                            // 获取scrollToView在窗体的坐标
                            scrollToView.getLocationInWindow(location);
                            // 计算root滚动高度，使scrollToView在可见区域
                            final int scrollHeight = (location[1] + scrollToView
                                    .getHeight()) - rect.bottom;
                            postScroll(root,0,scrollHeight);
                            if (isNeedGoneView != null) {
                                isNeedGoneView.setVisibility(View.GONE);
                            }
                        } else {
                            // 键盘隐藏
                            postScroll(root,0,0);
                            if (isNeedGoneView != null) {
                                isNeedGoneView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    /**
     * 滚动指定位置
     *
     * @param v
     * @param x
     * @param y
     */
    private static void postScroll(final View v, final int x, final int y) {
        v.post(new Runnable() {
            @Override
            public void run() {
                v.scrollTo(x, y);
            }
        });
    }

    /**
     * @param context
     * @param v
     */
    public static void hindWindowSoft(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }

    /**
     * 禁止输入法自动弹出
     *
     * @param context
     * @param v
     */
    public static void closeInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0,
            // InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 强制显示键盘
     *
     * @param context
     * @param v
     */
    public static void showInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive(v);    //isOpen若返回true，则表示输入法打开
        if (!isOpen) {
            return;
        }
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 根据点击区域去设置键盘的状态
     *
     * @param activity
     * @param ev
     * @param superTouchStatus
     * @return
     */
    public static boolean dispatchMyTouchEventHindWindowSoftWareKeyBord(Activity activity, MotionEvent ev, boolean superTouchStatus) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = activity.getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hindWindowSoft(activity, v);
            }
            return superTouchStatus;
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (activity.getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return activity.onTouchEvent(ev);
    }

    /**
     * 判断键盘位置
     *
     * @param v
     * @param event
     * @return
     */
    private static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 默认隐藏键盘
     * @param activity
     */
    public static void isDefaultNoKeyBord(Activity activity){
        activity.getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
