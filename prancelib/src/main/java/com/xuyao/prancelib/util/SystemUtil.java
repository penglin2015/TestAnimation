package com.xuyao.prancelib.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kingdost on 2016/11/10.
 */
public class SystemUtil {

    public static void getRunningAppProcessInfo(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            // 进程ID号
            int pid = runningAppProcessInfo.pid;
            // 用户ID
            int uid = runningAppProcessInfo.uid;
            // 进程名
            String processName = runningAppProcessInfo.processName;
            // 占用的内存
            int[] pids = new int[]{pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);
            int memorySize = memoryInfo[0].dalvikPrivateDirty;
            if (memorySize > 150 * 1024) {
                System.gc();
            }
            LogUtils.e("processName=" + processName + ",pid=" + pid + ",uid=" + uid + ",memorySize=" + memorySize + "kb");
        }
    }

    long beforeMillis = 0;
    long cancelMillis = 2000;

    public void twiceTouchCancel(Activity activity) {
        long millis = System.currentTimeMillis();
        if (millis - beforeMillis < cancelMillis) {
            activity.finish();
            System.exit(0);
            return;
        }
        beforeMillis = millis;
        Toast.makeText(activity, "再按一次退出", Toast.LENGTH_SHORT).show();
    }
}
