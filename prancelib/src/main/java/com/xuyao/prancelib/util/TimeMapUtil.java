package com.xuyao.prancelib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by king on 2016/9/2.
 */
public class TimeMapUtil {

    /*
    时间戳转时间yyyyHHRRHHMMss
     */
    public static String formatMapToYYHHRRHHMMss(String timeMap){
        long timeStamp=Long.parseLong(timeMap);
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        String d = format.format(timeStamp*1000);
        try {
//            Date date=format.parse(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /*
    时间转时间戳
     */
    public static long foramtTimeToTimemap(String time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*
    时间转时间戳
     */
    public static long foramtTimeYYHHDDHHMMSSToTimemap(String time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 时间戳转时间年月日时分秒
     * @param timestamp
     * @return
     */
    public static String formatTimestampToYYYYMMDDHHmmss(long timestamp){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd HH:mm",Locale.getDefault());
        if(String.valueOf(timestamp).length()==10){
            timestamp=timestamp*1000;
        }
        return simpleDateFormat.format(new Date(timestamp));
    }


    /*
 时间戳转时间yyyyHHRR
  */
    public static String formatMapToYYHHRR(String timeMap){
        long timeStamp=Long.parseLong(timeMap);
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        String d = format.format(timeStamp*1000);
        try {
//            Date date=format.parse(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /*
时间戳转时间yyyyHHRR
*/
    public static String formatTimeStampToYYHHRR(long timeMap){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        String d = format.format(timeMap);
        try {
//            Date date=format.parse(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }


    public static String formatYYYYMMDD_HHMMSSToyyyyMMDD(String yyyyMMddHHmmss){
        long ts=foramtTimeYYHHDDHHMMSSToTimemap(yyyyMMddHHmmss);
        return formatTimeStampToYYHHRR(ts);
    }
}
