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
import com.xuyao.prancelib.view.MyLoadHtmlWebView;
import com.xuyao.prancelib.view.MyLoadMapWebView;

/**
 * 网页的加载
 *
 * @author king
 */
public class WebLoadMapActivity extends BasePranceActivity {

    private View mLoadingView;
    protected MyLoadMapWebView mWebView;
    private String mTitle; // title
    private String mHtmlUrl; // load html url

    public static final String title = "title";//头部的显示文字
    public static final String htmlUrl = "htmlUrl";//url地址

    TextView title_center_text;
    ImageView title_left_img, title_right_img;


    @Override
    public int layoutId() {
        return R.layout.activity_web_load_map;
    }

    @Override
    public void initInData() {
        Bundle data = this.getIntent().getExtras();
        mTitle = data.getString(title);
        mHtmlUrl = data.getString(htmlUrl);
    }

    /**
     * 跳转
     *
     * @param context
     * @param title
     * @param htmlUrl
     */
    public static void startWebLoadMapActivity(Context context, String title, String htmlUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(WebLoadMapActivity.title, title);
        bundle.putString(WebLoadMapActivity.htmlUrl, htmlUrl);
        Intent intent = new Intent(context, WebLoadMapActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        mLoadingView = findViewById(R.id.baseweb_loading_indicator);
        mWebView = fv(R.id.baseweb_webview);

        title_left_img = fv(R.id.title_left_img);
        title_center_text = fv(R.id.title_center_text);
        title_center_text.setText(mTitle);

        title_right_img = fv(R.id.title_right_img);
        mWebView.setProgressView(mLoadingView);
    }


    private void loadHtml() {
        if (mWebView == null)
            return;
        showProgress();
        mWebView.loadUrl(mHtmlUrl);
    }


    @Override
    public void initEvent() {
        title_left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_right_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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


}
