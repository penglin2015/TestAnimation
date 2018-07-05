package com.xuyao.prancelib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */

public class ChatUtil {


    public static final String ENTERPRISEQQ = "800824028";//企业QQ

    /**
     * 微信是否安装
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
// 获取 packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * QQ是否安装
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 构建QQ临时会话
     *
     * @param context
     * @param qq
     */
    public static void gotoQQChat(Context context, String ...qq) {
        String str = "";
        //判断QQ是否安装（“*”是需要联系QQ号）
        if (isQQClientAvailable(context)) {
            //安装了QQ会直接调用QQ，打开手机QQ进行会话
            str = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq[0] + "&version=1&src_type=web&web_src=oicqzone.com";
        } else {
            //没有安装QQ会展示网页
            str = "http://wpa.qq.com/msgrd?v=3&uin=" + qq[0] + "&site=qq&menu=yes";
        }
        Uri uri = Uri.parse(str);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }


    /**
     * 拨打电话
     * @param context
     * @param phoneNum
     */
    public static void callPhoneNum(Context context, String phoneNum) {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            ToastUtil.toast(context,"请设置打电话权限");
            PermissionUtil.requestPermissionList((Activity) context,new String[]{Manifest.permission.CALL_PHONE});
            return;
        }
        context.startActivity(intent);
    }


    /**
     * 跳转拨号面板
     * @param context
     * @param phoneNum
     */
    public static void gotoCall(Context context, String phoneNum){
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
