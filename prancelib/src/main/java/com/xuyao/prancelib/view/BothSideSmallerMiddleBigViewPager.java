package com.xuyao.prancelib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.xuyao.prancelib.R;
import com.xuyao.prancelib.util.LogUtils;

/**
 * 中间加载查看两边预查看
 */
public class BothSideSmallerMiddleBigViewPager extends ViewPager {

    public BothSideSmallerMiddleBigViewPager(@NonNull Context context) {
        this(context,null);
    }

    public BothSideSmallerMiddleBigViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray t=context.obtainStyledAttributes(attrs,R.styleable.BothSideSmallerMiddleBigViewPager,0,0);
        boolean isCli=t.getBoolean(R.styleable.BothSideSmallerMiddleBigViewPager_isSmallerBoth,false);
        int cliw=t.getInteger(R.styleable.BothSideSmallerMiddleBigViewPager_clipPaddingValue,0);
        setBothSideWidthPading(cliw);
        setClipPading(isCli);
        init();
        t.recycle();
    }


    private void init(){
       setClipToPadding(!isClipPading);
       setPadding(bothSideWidthPading,0,bothSideWidthPading,0);
       setPageTransformer(true,new MyPagerTransfer());
       setOffscreenPageLimit(3);
    }

    boolean isClipPading=true;
    int bothSideWidthPading=0;

    public void setClipPading(boolean clipPading) {
        isClipPading = clipPading;
    }

    public void setBothSideWidthPading(int bothSideWidthPading) {
        this.bothSideWidthPading = bothSideWidthPading;
    }

    /**
     * 实现动画的对象接口
     */
    public final static float MINSC=0.5f;
    class MyPagerTransfer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View page, float position) {
            int index=indexOfChild(page);
            LogUtils.e("index="+index+",position="+position);
            float ratioSc=0.19f;//计算得出得差值
            if(position<-1f||position>1f){
//                    ViewHelper.setScaleX(page,MINSC);
                ViewHelper.setScaleY(page,MINSC);
            }else{
                if(position<0){
                    float p=1f+position-ratioSc;
                    float sc=MINSC + p * (1f - MINSC);
//                        ViewHelper.setScaleX(page,sc);
                    ViewHelper.setScaleY(page,sc);
                }else if(position>0){
                    float p=1f-position;
                    float sc=MINSC + p * (1f - MINSC);
//                        ViewHelper.setScaleX(page,sc);
                    ViewHelper.setScaleY(page,sc);
                }
            }
        }
    }
}
