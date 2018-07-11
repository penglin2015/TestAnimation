package com.shouxindao.testanimation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.shouxindao.testanimation.util.BzUtil;
import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.LogUtils;
import com.xuyao.prancelib.util.RandomUti;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasinView extends View implements View.OnTouchListener{

    public BasinView(Context context) {
        super(context);
        init();
    }

    public BasinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint p;
    int w,h;
    private void init(){
        p=new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(5);
        w=ScreenUtils.getScreenWidth(getContext());
        h=ScreenUtils.getScreenHeight(getContext());
        c=h/2;
        setOnTouchListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPoint(canvas);
        drawBasin(canvas);
        postInvalidate();
    }

    int maxCount=20;//喷泉数
    List<BasinBean> basinBeanList=new ArrayList<>();
    private void drawBasin(Canvas canvas) {
        if(basinBeanList.size()<maxCount){
            for(int i=0;i<maxCount-basinBeanList.size();i++){
                basinBeanList.add(new BasinBean());
            }
        }

        List<BasinBean> isMoveBasinBean=new ArrayList<>();
        for(BasinBean basinBean:basinBeanList){
            if(basinBean.isOver) {
                isMoveBasinBean.add(basinBean);
            }else{
                basinBean.draw(canvas);
            }
        }
        if(isMoveBasinBean.size()>0){
            basinBeanList.removeAll(isMoveBasinBean);
        }
    }

    private void drawPoint(Canvas canvas) {
        List<PointF> points=livePoints();
        for(PointF point:points){
            canvas.drawPoint(point.x,point.y,p);
        }


//        List<PointF> bzs=liveBzLine(new PointF(w/2,h),new PointF(w/2,h-500),new PointF(w/8,h));
//        for(PointF pf:bzs){
//            canvas.drawPoint(pf.x,pf.y,p);
//        }
    }


    class BasinBean implements Serializable{
        List<PointF> pointFList;
        Paint basinPaint;
        long sec=0;
        public BasinBean(){
            basinPaint=new Paint(p);
            basinPaint.setColor(ColorUtil.randomColor());

            float changeH= h-2200f+RandomUti.getInstance().getRandom().nextFloat()*600f;
            float changeW=RandomUti.getInstance().getRandom().nextFloat()*(w-400)+200;
            pointFList=BzUtil.twoStepsCurveListPoint(new PointF(w/2,h),new PointF(w/2,changeH),new PointF(changeW,h));
            sec=RandomUti.getInstance().getRandom().nextInt(5)+3;
            rx=RandomUti.getInstance().getRandom().nextFloat()*50+5;
            start();
        }
        //绘制
        private void draw(Canvas canvas){
            canvas.drawCircle(pointFList.get(index).x,pointFList.get(index).y,rx,basinPaint);
        }

        boolean isOver=false;
        float rx=0;
        int index=0;
        private void start(){
            ValueAnimator valueAnimator=ValueAnimator.ofInt(0,pointFList.size()-1);
            valueAnimator.setDuration(sec*1000);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value= (int) animation.getAnimatedValue();
                    index=value;
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isOver=true;
                }
            });
            valueAnimator.start();
        }


    }


    //ax平方+bx+c=y  x=根号-2py
    float a=0f,b=0,c=0;
    private List<PointF> livePoints(){
        b=0f;
        List<PointF> pointList=new ArrayList<>();
        for(float i=0;i<w;i++){
            float x=i-w/2;
            float y=a*x*x+b*x+c;
            PointF point=new PointF(x+w/2f,y);
            pointList.add(point);
        }
        return pointList;
    }

    float dx,dy,mx,my,ux,uy,distancex,distancey;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x=event.getRawX();
        float y=event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                dx=x;
                dy=y;
                break;
            case MotionEvent.ACTION_MOVE:
                mx=x;
                my=y;
                distancex=mx-dx;
                distancey=my-dy;
                c=h/2f+distancey;
                a=-c/1000f;
                LogUtils.e("c="+c);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ux=x;
                uy=y;
                distancex=ux-dx;
                distancey=uy-dy;
                backDefault(c,h/2f);
                break;
        }
        return true;
    }

    private void backDefault(float s, float v) {
        ValueAnimator cChangeDefault=ValueAnimator.ofFloat(s,v);
        cChangeDefault.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                c=value;
                LogUtils.e("c="+c);
            }
        });

        cChangeDefault.setDuration(800);
        cChangeDefault.start();

        ValueAnimator aChangeDefault=ValueAnimator.ofFloat(-c/1000,0);
        aChangeDefault.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                a=value;
            }
        });

        aChangeDefault.setDuration(800);
        aChangeDefault.start();
    }


    private List<PointF> liveBzLine(PointF ...p){
        return BzUtil.twoStepsCurveListPoint(p[0],p[1],p[2]);
    }
}
