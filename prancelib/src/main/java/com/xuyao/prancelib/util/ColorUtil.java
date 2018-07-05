package com.xuyao.prancelib.util;

import android.graphics.Color;

public class ColorUtil {


    static int colorValue=16777216;
    public static int randomColor(){
        int num= -(int) (Math.random()* colorValue+1);
        String n=Integer.toHexString(num);
        return Color.parseColor("#"+n);
    }
}
