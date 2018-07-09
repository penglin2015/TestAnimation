package com.shouxindao.testanimation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

public class UtilSlideMenuView extends FrameLayout implements View.OnTouchListener{
    public UtilSlideMenuView(@NonNull Context context) {
        super(context);
    }

    public UtilSlideMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UtilSlideMenuView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 子界面必须有两个 第一界面  第二菜单
     *
     */

    View contentView;
    View menuView;
    boolean isOpen;//默认为没有打开
    private void init(){
        int count=getChildCount();
        if(count!=2){
            IndexOutOfBoundsException indexOutOfBoundsException=new IndexOutOfBoundsException();
                indexOutOfBoundsException.printStackTrace();
            throw indexOutOfBoundsException;
        }
        contentView=getChildAt(0);
        menuView=getChildAt(1);
        isOpen=false;
        if(menuWidth==0){
            menuWidth=menuView.getMeasuredWidth();
        }
        setMenuWidth(menuWidth);
        ViewHelper.setTranslationX(menuView,-menuView.getMeasuredWidth());
        setOnTouchListener(this);
    }

    float menuWidth=0;
    public void setMenuWidth(float width){
        menuWidth=width;

        if(menuView!=null){
            ViewGroup.LayoutParams vlp=menuView.getLayoutParams();
            vlp.width= (int) width;
            menuView.setLayoutParams(vlp);
        }
    }


    public void open(){
        openAnimation();
    }

    public void close(){
        closeAnimation();
    }

    public boolean isOpen() {
        return isOpen;
    }

    float dx,dy,mx,my,ux,uy;
    float mtx=0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!isTouch){
            return false;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mtx=menuView.getTranslationX();
                dx=event.getRawX();
                dy=event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mx=event.getRawX()-dx;
                my=event.getRawY()-dy;
                float tx=mtx+mx;
                if(tx>=0){
                    tx=0;
                }else if(tx<=-menuWidth){
                    tx=-menuWidth;
                }
                ViewHelper.setTranslationX(menuView,tx);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(mx>0){
                    //向右的手势
                    if(!isOpen){
                        //触发滑动
                        openAnimation();
                    }
                }else if(mx<0){
                    //向左的手势
                    if(isOpen){
                        //触发关闭手势
                        closeAnimation();
                    }
                }
                break;

        }

        return true;
    }

    boolean isTouch=true;
    private void openAnimation(){
        ValueAnimator open=ValueAnimator.ofFloat(menuView.getTranslationX(),0);
        open.setDuration(300);
        open.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v= (float) animation.getAnimatedValue();
                ViewHelper.setTranslationX(menuView,v);
            }
        });

        open.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTouch=false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen=true;
                isTouch=true;
            }
        });
        open.start();
    }


    private void closeAnimation(){
        ValueAnimator close=ValueAnimator.ofFloat(menuView.getTranslationX(),-menuView.getMeasuredWidth());
        close.setDuration(300);
        close.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v= (float) animation.getAnimatedValue();
                ViewHelper.setTranslationX(menuView,v);
            }
        });
        close.addListener(new AnimatorListenerAdapter() {


            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTouch=false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen=false;
                isTouch=true;
            }
        });
        close.start();
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        init();
    }
}
