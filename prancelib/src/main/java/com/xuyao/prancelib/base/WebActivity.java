package com.xuyao.prancelib.base;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuyao.prancelib.R;
import com.xuyao.prancelib.interfaces.JsInterface;
import com.xuyao.prancelib.util.ScreenUtils;
import com.xuyao.prancelib.view.MyLoadHtmlWebView;

/**
 * 网页的加载
 *
 * @author king
 */
public class WebActivity extends BasePranceActivity {

    private View mLoadingView;
    protected MyLoadHtmlWebView mWebView;
    private String mTitle; // title
    private String mHtmlUrl; // load html url
    private boolean mIsFromLocale = false; // is load html from locale or network

    public static final String title = "title";//头部的显示文字
    public static final String htmlUrl = "htmlUrl";//url地址
    public static final String isFromLocale = "isFromLocale";//是否是本地的代码
    private WebSettings webSettings;

    TextView  title_center_text;
    ImageView title_left_img,title_right_img;



    @Override
    public int layoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initInData() {
        Bundle data = this.getIntent().getExtras();
        mTitle = data.getString(title);
        mHtmlUrl = data.getString(htmlUrl);
        mIsFromLocale = data.getBoolean(isFromLocale);
    }

    /**
     * 跳转
     *
     * @param context
     * @param title
     * @param htmlUrl
     * @param mIsFromLocale
     */
    public static void startWebActivity(Context context, String title, String htmlUrl, boolean mIsFromLocale) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(isFromLocale, mIsFromLocale);
        bundle.putString(WebActivity.title, title);
        bundle.putString(WebActivity.htmlUrl, htmlUrl);
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        mLoadingView = findViewById(R.id.baseweb_loading_indicator);
        mWebView = fv(R.id.baseweb_webview);
        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JsCallBack(), "JsBean");

        title_left_img=fv(R.id.title_left_img);
        title_center_text=fv(R.id.title_center_text);
        title_center_text.setText(mTitle);

        title_right_img=fv(R.id.title_right_img);

    }


    private void loadHtml() {
        String url = "file:///android_asset/" + mHtmlUrl;
        if (mWebView == null)
            return;
        if (mIsFromLocale)
            mWebView.loadUrl(url);
        else
            mWebView.loadUrl(mHtmlUrl);
    }


    @Override
    public void initEvent() {

        title_left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                actionKey(KeyEvent.KEYCODE_BACK);
            }
        });

        title_right_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webSettings.setBuiltInZoomControls(true);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgress();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissProgress();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //网页的头部回调
                title_center_text.setText(title);
            }
        });
    }

    @Override
    public void initEndData() {
        loadHtml();
    }

    private void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        mLoadingView.setVisibility(View.GONE);
    }


    /**
     * 一些js回到方法
     */

    class JsCallBack extends JsInterface{
        //可以自己定义一些js回调的方法
    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public void actionKey(final int keyCode) {
        new Thread () {
            public void run () {
                try {
                    Instrumentation inst=new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
