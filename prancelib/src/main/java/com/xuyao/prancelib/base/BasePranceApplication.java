package com.xuyao.prancelib.base;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;

/**
 * Created by T470 on 2018/3/8.
 */

public abstract class BasePranceApplication extends MultiDexApplication {

    protected abstract void baseOnCreate();
    protected abstract void baseOnLowMemory();
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        baseOnCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        BitmapAjaxCallback.clearCache();
        queryCleanMemory();
       baseOnLowMemory();
    }


    private void queryCleanMemory() {
        //clean the file cache with advance option
        long triggerSize = 3000000; //大于3M时候开始清除
        long targetSize = 2000000;   //直到少于2M
        AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
    }
}
