package com.xuyao.prancelib.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


/**
 * Created by T470 on 2018/3/8.
 */

public class IntentUtil {

    /**
     * 简单的跳转
     *
     * @param context
     * @param clz
     */
    public static void startSiampleActivity(Context context, Class<?> clz) {
        context.startActivity(new Intent(context, clz));
    }

    /**
     * 普通带参数跳转
     *
     * @param context
     * @param clz
     */
    public static void startPutExtraActivity(Context context, Class<?> clz, TreeMap<String, ? extends Object>... datas) {
        Intent intent = new Intent(context, clz);
        if (datas != null && datas.length > 0) {
            for (TreeMap<String, ? extends Object> tr : datas) {
                if (tr.size() > 0) {
                    Set<String> keySet = tr.keySet();
                    Iterator<String> it = keySet.iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        Object value = tr.get(key);
                        if (value instanceof CharSequence) {
                            intent.putExtra(key, (CharSequence) value);
                        } else if (value instanceof String) {
                            intent.putExtra(key, (String) value);
                        } else if (value instanceof Integer) {
                            intent.putExtra(key, (Integer) value);
                        } else if (value instanceof Float) {
                            intent.putExtra(key, (Float) value);
                        } else if (value instanceof Long) {
                            intent.putExtra(key, (Long) value);
                        } else if (value instanceof Boolean) {
                            intent.putExtra(key, (Boolean) value);
                        } else if (value instanceof Object[]) {
                            intent.putExtra(key, (Object[]) value);
                        } else {
                        }
                    }
                }
            }
        }
        context.startActivity(intent);
    }

    /**
     * 带bundle参数跳转
     *
     * @param context
     * @param clz
     * @param bundle
     */
    public static void startBundleActivity(Context context, Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 需要得到页面回馈的跳转
     *
     * @param context
     * @param clz
     * @param requestCode
     */
    public static void startForResultActivity(Activity context, Class<?> clz, int requestCode) {
        Intent intent = new Intent(context, clz);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 带参数需要得到页面回馈的跳转
     *
     * @param context
     * @param clz
     * @param requestCode
     */
    public static void startBundleForResultActivity(Activity context, Class<?> clz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }


    /**
     * 跳转浏览器
     *
     * @param context
     * @param url
     */
    public static void gotoWebKit(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    static LinkedList<Activity> activities = new LinkedList<>();//统计活动页面的对象
    static LinkedList<String> acNames = new LinkedList<>();//统计activity的名称

    public static void addActivities(Activity activity) {
        String clazName = activity.getClass().getSimpleName();
        activities.add(activity);
        acNames.add(clazName);
    }

    /**
     * 结束单个
     *
     * @param isNeedRemoveActivity
     */
    public static void removeSingleActivity(Activity isNeedRemoveActivity) {
        activities.remove(isNeedRemoveActivity);
        acNames.remove(isNeedRemoveActivity.getClass().getSimpleName());
        if (isNeedRemoveActivity!= null) {
            isNeedRemoveActivity.finish();
        }
    }


    public static void liveSinglePageRemoveAllActivity(Class<?> isNeedLiveActivityClass) {
        LinkedList<Activity> isNeedRemoveActivitys = new LinkedList<>();
        LinkedList<String> isNeedRemoveAcNames = new LinkedList<>();
        for (Activity activity : activities) {
            String itemClasz = activity.getClass().getSimpleName();
            if (isNeedLiveActivityClass.getSimpleName().equals(itemClasz)) {
                continue;
            }
            activity.finish();
            isNeedRemoveActivitys.add(activity);
            isNeedRemoveAcNames.add(itemClasz);
        }
        activities.removeAll(isNeedRemoveActivitys);
        acNames.removeAll(isNeedRemoveAcNames);
    }

    /**
     * 开启新线程访问活动页面
     * @param mContext
     * @param claz
     */
    public static void startFlagNewTaskActivity(Context mContext,Class<?> claz){
        Intent intent=new Intent(mContext,claz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }



    public static class TopActivityInfo {
        public String packageName = "";
        public String topActivityName = "";
    }

    /**
     * 获得最顶层的活动页面
     * @param context
     * @return
     */
    private static TopActivityInfo getTopActivityInfo(Context context) {
        ActivityManager manager = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE));
        TopActivityInfo info=new TopActivityInfo();
        if (Build.VERSION.SDK_INT >= 21) {
            List<ActivityManager.RunningAppProcessInfo> pis = manager.getRunningAppProcesses();
            ActivityManager.RunningAppProcessInfo topAppProcess = pis.get(0);
            if (topAppProcess != null && topAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                info.packageName = topAppProcess.processName;
                info.topActivityName = "";
            }
        } else {
            //getRunningTasks() is deprecated since API Level 21 (Android 5.0)
            List localList = manager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo localRunningTaskInfo = (ActivityManager.RunningTaskInfo)localList.get(0);
            info.packageName = localRunningTaskInfo.topActivity.getPackageName();
            info.topActivityName = localRunningTaskInfo.topActivity.getClassName();
        }
        return info;
    }
}
