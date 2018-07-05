package com.xuyao.prancelib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyLoadMapWebView extends WebView {
    public MyLoadMapWebView(Context context) {
        super(context);
        init(context);
    }

    public MyLoadMapWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyLoadMapWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @SuppressLint("WrongConstant")
    void init(Context context) {
        WebSettings webSettings = getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDefaultTextEncodingName("utf-8");// 避免中文乱码
        setScrollBarStyle(0);
        setHorizontalScrollBarEnabled(false);// 水平不显示
        setVerticalScrollBarEnabled(false); // 垂直不显示
        WebSettings webSetting = webSettings;

        webSetting.setJavaScriptEnabled(true);
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSetting.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT | WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSetting.setNeedInitialFocus(false);
        setBackgroundColor(Color.TRANSPARENT);// 设置其背景为透明
        webSetting.setDomStorageEnabled(true); //显示全景的问题


//启用数据库
        webSetting.setDatabaseEnabled(true);


//设置定位的数据库路径  
        String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();


        webSetting.setGeolocationDatabasePath(dir);


//启用地理定位  
        webSetting.setGeolocationEnabled(true);

        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                        if(progressView==null)
                            return;
                        progressView.setVisibility(GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if(progressView==null)
                    return;
                progressView.setVisibility(GONE);
            }
        });
    }

    View progressView;
    public void setProgressView(View progressView){
        this.progressView=progressView;
    }

}
