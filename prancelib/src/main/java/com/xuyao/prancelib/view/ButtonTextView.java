package com.xuyao.prancelib.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.xuyao.prancelib.util.DensityUtil;

public class ButtonTextView extends AppCompatTextView {

    public ButtonTextView(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
    }

    public ButtonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
    }

    public ButtonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
    }

    /**
     * 设置上下的padding值
     * @param lr
     * @param tb
     */
    public void setTopBottomLeftRightPadding(int lr,int tb,boolean isDbValue){

        if(isDbValue){
            int lrpxValue= (int) DensityUtil.dip2px(getContext(),lr);
            int tbpxValue=(int) DensityUtil.dip2px(getContext(),tb);
            setPadding(lrpxValue,tbpxValue,lrpxValue,tbpxValue);
            return;
        }
        setPadding(lr,tb,lr,tb);
    }
}
