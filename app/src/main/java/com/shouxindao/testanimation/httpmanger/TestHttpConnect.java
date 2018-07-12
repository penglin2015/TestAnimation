package com.shouxindao.testanimation.httpmanger;

import android.content.Context;

import com.xuyao.prancelib.network.NetWorkBase;

public class TestHttpConnect extends NetWorkBase {

    public TestHttpConnect(Context context) {
        super(context);
        setBaseUrl("https://api-m.mtime.cn/");
    }

    public void test(int locationId){
        get("Showtime/LocationMovies.api",new MyParams().put("locationId",locationId));
    }
}
