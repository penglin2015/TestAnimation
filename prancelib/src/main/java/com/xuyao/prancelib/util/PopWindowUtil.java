package com.xuyao.prancelib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import butterknife.ButterKnife;

public abstract class PopWindowUtil<V extends PopWindowUtil.PopBaseViewHolder> {

    @Deprecated
    public void actuatingLogic(View popView) {

    }//执行视图逻辑

    public void actuatingLogic(V vh) {

    }//执行ViewHolder逻辑

    public V getPopViewHolder() {
        return null;
    }

    Activity context;
    PopupWindow popupWindow;
    int pop_layout_id;
    int w;
    int h;

    View popV;

    public PopWindowUtil(Activity context, int pop_layout_id, int w, int h) {
        this.context = context;
        this.pop_layout_id = pop_layout_id;
        this.w = w;
        this.h = h;
        initPop();
    }

    public PopWindowUtil(Activity context, int w, int h) {
        this.context = context;
        this.w = w;
        this.h = h;
        initPop();
    }

    /**
     * 初始化pop各参数
     */
    private void initPop() {
        dismissPop();
        V popViewHolder = getPopViewHolder();
        popV = popViewHolder != null ? popViewHolder.getView() : inflatePopView(pop_layout_id);
        if (popViewHolder != null) {
            actuatingLogic(popViewHolder);
        }
        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(popV);
        popupWindow.setWidth(w);
        popupWindow.setHeight(h);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismissPop();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ScreenUtils.backgroundAlpha(1f, context);
                dismissPop();
            }
        });
        actuatingLogic(popV);
    }

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public PopupWindow getPopupWindow() {
        initPop();
        if(resetAnimation()!=0){
            popupWindow.setAnimationStyle(resetAnimation());
        }
        return popupWindow;
    }

    public int resetAnimation(){
        return 0;
    }

    public void showPop(boolean isWindowAlpha, int gravity) {
        if (isWindowAlpha) {
            ScreenUtils.backgroundAlpha(0.6f, context);
        }
        getPopupWindow().showAtLocation(context.getWindow().getDecorView(), gravity, 0, 0);
    }

    public void dismissPop() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            popupWindow = null;
        }
    }

    /**
     * 获得对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T popfindViewById(int id) {
        return popV.findViewById(id);
    }

    public View inflatePopView(int layId) {
        return LayoutInflater.from(context).inflate(layId, null);
    }


    public static abstract class PopBaseViewHolder {


        Context context;

        public PopBaseViewHolder(Context context) {
            this.context = context;

            view = LayoutInflater.from(context).inflate(layIdForView(), null);
            ButterKnife.bind(this, (View) view);
        }

        View view;

        public PopBaseViewHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }

        public int layIdForView() {
            return 0;
        }

        public View getView() {
            return view;
        }
    }
}
