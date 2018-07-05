package com.xuyao.prancelib.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    /**
     * 权限申请
     */
    public final static int REQUEST_CODE=123;

    /**
     * 结果反馈
     * @param activity
     * @param requestCode
     * @param permisions
     * @param grantResults
     * @return
     */
    public static boolean onRequestPermissionResult(Activity activity,int requestCode,String[] permisions,int[] grantResults){
        boolean isResult=true;
        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                isResult=false;
                break;
            }
        }
        return isResult;
    }

    /***
     * 检测权限
     * @param activity
     * @param permissionName
     * @return
     */
    public static boolean checkPermission(Activity activity,String permissionName){
       return ActivityCompat.checkSelfPermission(activity,permissionName)==PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检查权限返回失败权限
     * @param activity
     * @param permissionList
     * @return
     */
    public static String[] checkPermissionList(Activity activity,String permissionList[]){
        List<String> permissionListDine=new ArrayList<>();
        for(String permission:permissionList){
           boolean isGranted= checkPermission(activity,permission);
           if(!isGranted){
               permissionListDine.add(permission);
           }
        }
        return permissionListDine.toArray(new String[permissionListDine.size()]);
    }


    /**
     * 请求权限集合
     * @param activity
     * @param permissionNames
     */
    public static void requestPermissionList(Activity activity,String [] permissionNames){
        ActivityCompat.requestPermissions(activity,permissionNames,REQUEST_CODE);
    }
}
