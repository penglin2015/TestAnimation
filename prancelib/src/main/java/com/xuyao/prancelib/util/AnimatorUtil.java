package com.xuyao.prancelib.util;

import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by PL_Moster on 2017/4/17.
 */

public class AnimatorUtil {

    /**
     * 透明度的改变的动画呈现
     * @param root
     * @param startAlpha
     * @param endAlpha
     * @return
     */
    public static ObjectAnimator setViewAlphaChange(View root,float startAlpha,float endAlpha){
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(root,"alpha",startAlpha,endAlpha);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        return objectAnimator;
    }
}
