package com.shouxindao.testanimation.views;

import android.view.animation.Interpolator;

/**
 * 自定义插值器
 */
public class PranceInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return getSquareDown(input);
    }


    //速度慢慢增大，再减小。
    private static float getSin(float input){
        return (float) (Math.sin(input*Math.PI-Math.PI/2)+1)/2;
    }
    //速度持续增大
    private static float getCubeUp(float input){
        return input*input*input;
    }
    //速度慢慢减缓。
    private static float getSquareDown(float input){
        input -= 1;
        return -input*input+1;
    }
    // 非平滑曲线
    private static float getSeparete(float input){
        if(input<0.2){// 会有突变到暂停不变的效果
            return 0.1f;
        }else{
            return (9*input-1)/8;
        }
    }
}
