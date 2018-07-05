package com.xuyao.prancelib.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class UmPlusUtil {

    Context mContext;

    public UmPlusUtil(Context context) {
        this.mContext = context;
    }

    String appkey = "5ac32e2db27b0a2ead000192";
    String channel="zq";
    public void init() {
        UMConfigure.init(mContext,appkey,channel,UMConfigure.DEVICE_TYPE_PHONE,null);
        MobclickAgent.setScenarioType(mContext,MobclickAgent.EScenarioType.E_UM_NORMAL);
        UMConfigure.setEncryptEnabled(true);
    }

    public void onR(){
        MobclickAgent.onResume(mContext);
    }

    public void onP(){
        MobclickAgent.onPause(mContext);
    }
}
