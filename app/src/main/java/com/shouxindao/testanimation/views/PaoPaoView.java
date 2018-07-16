package com.shouxindao.testanimation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.shouxindao.testanimation.R;
import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.RandomUti;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaoPaoView extends View {
    public PaoPaoView(Context context) {
        super(context);
        init();
    }

    public PaoPaoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaoPaoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPao(canvas);
        postInvalidate();
    }

    List<PaoBean> paoBeanList=new ArrayList<>();
    List<PaoBean> needMovePaoList=new ArrayList<>();
    long maxPaoCount=200;
    private void drawPao(Canvas canvas) {
        if(paoBeanList.size()<maxPaoCount){
            for(int i=0;i<maxPaoCount-paoBeanList.size();i++){
                paoBeanList.add(new PaoBean());
            }
        }

        for(PaoBean paoBean:paoBeanList){
            if(paoBean.isOver){
                needMovePaoList.add(paoBean);
            }else{
                paoBean.draw(canvas);
            }
        }
        paoBeanList.removeAll(needMovePaoList);
        needMovePaoList.clear();
    }

    class PaoBean implements Serializable{
        float x,y,maxRadus;
        Paint paoPaint;
        Bitmap pb;
        float scale=0.1f,maxScale=1f;
        Matrix m;
        long t;
        float alpha=0.5f;
        RadialGradient radialGradient;
        int color=0;
        float dx,dy;
        public PaoBean(){
            paoPaint=new Paint(p);
            pb= BitmapFactory.decodeResource(getResources(), R.mipmap.icon_pao);
            maxScale= (float) (RandomUti.getInstance().getRandom().nextDouble()+0.8f);
            m=new Matrix();
            t=RandomUti.getInstance().getRandom().nextInt(8)+1;
            m.setScale(scale,scale);
            x=RandomUti.getInstance().getRandom().nextInt(w)+1;
            color=ColorUtil.randomColor();
            dx=(float) (Math.random()*40-20f);
            dy=(float) (Math.random()*40-20f);
            start();
        }

        public void draw(Canvas canvas){
            if(pb==null){
                return;
            }
            m.reset();
            m.setTranslate(x,y);
            m.preScale(scale,scale,pb.getScaledWidth(canvas)/2f,pb.getScaledHeight(canvas)/2f);
            paoPaint.setAlpha((int) (alpha*255f));
            radialGradient=new RadialGradient(x+dx,y+dy,pb.getScaledWidth(canvas),Color.WHITE,color, Shader.TileMode.MIRROR);
            paoPaint.setShader(radialGradient);
            canvas.drawCircle(x+pb.getScaledWidth(canvas)/2f,y+pb.getScaledHeight(canvas)/2f,pb.getWidth()*(scale-0.1f)/2f,paoPaint);
            canvas.drawBitmap(pb,m,paoPaint);
        }

        boolean isOver=false;
        private void start(){
            ValueAnimator valueAnimator=ValueAnimator.ofFloat(scale,maxScale);
            valueAnimator.setDuration(t*1000);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float sc= (float) animation.getAnimatedValue();
                    scale=sc;
                    y=h-h*animation.getAnimatedFraction();
                    alpha=0.5f+0.5f*animation.getAnimatedFraction();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isOver=true;
                    if(!pb.isRecycled()){
                        pb.recycle();
                        pb=null;
                    }
                }
            });
            valueAnimator.start();
        }
    }
}
