package com.xuyao.prancelib.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class StringFormatUtil {

    private SpannableStringBuilder builder;
    private Context context;
    private String allstr, str;

    public String getAllstr() {
        return allstr;
    }

    public void setAllstr(String allstr) {
        this.allstr = allstr;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;
    private int start = 0, end = 0;

    public StringFormatUtil(Context context) {
        this.context = context;
    }

    public SpannableStringBuilder fillColor() {
        if (!allstr.equals("")) {
            if (!str.equals("")) {
                if (allstr.contains(str)) {
                    start = allstr.indexOf(str);
                    end = start + str.length();
                    builder = new SpannableStringBuilder(allstr);
                    color = context.getResources().getColor(color);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                    builder.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return builder;
                } else {
                    return null;
                }
            } else {
                str = 0 + "";
                allstr = 0 + allstr;
                if (allstr.contains(str)) {
                    start = allstr.indexOf(str);
                    end = start + str.length();
                    builder = new SpannableStringBuilder(allstr);
                    color = context.getResources().getColor(color);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                    builder.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return builder;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * 设置多种风格
     *
     * @param color
     * @return
     */
    ForegroundColorSpan[] foregroundColorSpans=new ForegroundColorSpan[0];

    public StringFormatUtil setDifferentColor(int... colors) {
        foregroundColorSpans = new ForegroundColorSpan[colors.length];
        for (int i = 0; i < colors.length; i++) {
            int setColor=context.getResources().getColor(colors[i]);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(setColor);
            foregroundColorSpans[i] = foregroundColorSpan;
        }
        return this;
    }

    /**
     * 设置多种字体大小
     *
     * @param color
     * @return
     */
    AbsoluteSizeSpan[] textSizeSpans=new AbsoluteSizeSpan[0];

    public StringFormatUtil setDifferentFontSize(int... sizes) {
        textSizeSpans = new AbsoluteSizeSpan[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(sizes[i]);
            textSizeSpans[i] = absoluteSizeSpan;
        }
        return this;
    }

    /**
     * 设置风格内容
     *
     * @param fullStr
     * @param changeTextStr
     * @return
     */
    SpannableStringBuilder setStyleTextSpanStr;

    public StringFormatUtil setDifferentTextSpan(TextView setSpanTv,String fullStr, String... changeTextStr) {
        setStyleTextSpanStr = new SpannableStringBuilder(fullStr);
        for (int i = 0; i < changeTextStr.length; i++) {
            String changeStr = changeTextStr[i];
            if (fullStr.contains(changeStr)) {
                int startIndex = fullStr.indexOf(changeStr);
                int endIndex = startIndex + changeStr.length();
                if (foregroundColorSpans.length != 0 && foregroundColorSpans.length == changeTextStr.length) {
                    setStyleTextSpanStr.setSpan(foregroundColorSpans[i],startIndex,endIndex,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if(textSizeSpans.length!=0&&textSizeSpans.length==changeTextStr.length){
                    setStyleTextSpanStr.setSpan(textSizeSpans[i],startIndex,endIndex,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        setSpanTv.setText(setStyleTextSpanStr);
        return this;
    }


    /**
     * 转通配的
     *
     * @param str
     * @return
     */
    public static String isNullOrStringNullFormat(String str) {
        if (str == null || str.equalsIgnoreCase("null")) {
            return "";
        }
        return str;
    }
}
