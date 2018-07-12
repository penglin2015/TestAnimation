package com.shouxindao.testanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shouxindao.testanimation.httpmanger.TestHttpConnect;
import com.shouxindao.testanimation.mode.Test;
import com.xuyao.prancelib.network.NetWorkBase;
import com.xuyao.prancelib.util.LogUtils;

public class WeatherAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_animation);

        final TestHttpConnect testHttpConnect=new TestHttpConnect(this);
        testHttpConnect.setResultCallbackListener(new NetWorkBase.ResultCallbackListener() {
            @Override
            public void success(Object data) {
                Test test=testHttpConnect.getBaseData(Test.class);

                LogUtils.e("data"+data);
            }

            @Override
            public void fail(Throwable t, int errorNo, String msg) {
                LogUtils.e("msg="+msg);
            }
        });
//        testHttpConnect.test(291);
    }
}
