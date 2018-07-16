package com.shouxindao.testanimation.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;

public class KuaiView extends View {
    public KuaiView(Context context) {
        super(context);
        init();
    }

    public KuaiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KuaiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    Paint p;
    int w, h;

    private void init() {
        p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);
        p.setDither(true);

        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(5);
        w = ScreenUtils.getScreenWidth(getContext());
        h = ScreenUtils.getScreenHeight(getContext());

        geBean=new GeBean();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGe(canvas);
        drawKuai(canvas);
        postInvalidate();
    }

    GeBean geBean;
    /**
     * 绘制格子
     * @param canvas
     */
    private void drawGe(Canvas canvas) {
        geBean.draw(canvas);
    }

    /**
     * 绘制移动方快
     * @param canvas
     */
    private void drawKuai(Canvas canvas) {


    }

    class GeBean implements Serializable{
        Paint gePaint;
        float lw=0;
        float maxHeight=0;
        public GeBean(){
            gePaint=new Paint(p);
            gePaint.setStrokeWidth(1f);
            gePaint.setColor(ColorUtil.randomColor());
            maxHeight=h-200f;
            lw=w/20f;
        }

        public void draw(Canvas canvas){
            for(int i=1;i<=w/lw;i++){
                canvas.drawLine(i*lw,maxHeight,i*lw,-5,gePaint);
            }

            for(int j=0;j<=maxHeight/lw;j++){
                canvas.drawLine(0,maxHeight-(j)*lw,w,maxHeight-(j)*lw,gePaint);
            }
        }
    }


    class KuaiBean implements Serializable{
        float x,y,kw,kh;
        Paint kuaiPaint;
        public KuaiBean(){
            kuaiPaint=new Paint(p);

        }
    }
}
