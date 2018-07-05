package com.xuyao.prancelib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by admin on 2017/6/22.
 * 加载代码片段的webView
 */

public class MyLoadHtmlWebView extends WebView{

    public MyLoadHtmlWebView(Context context) {
        super(context);
        setWebSetting(context);
    }

    public MyLoadHtmlWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWebSetting(context);
    }

    /**
     * 处理代码片段
     * @param htmltext
     * @return
     */
     private String getNewContent(String htmltext){
        Document doc= Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }
        Log.d("VACK", doc.toString());
        return doc.toString();
    }

    /**
     * 加载url
     * @param html
     */
    public final static String contentType="text/html";
    public final static String enconding="utf-8";
    public void loadHtml(String html){
        loadDataWithBaseURL(null,getNewContent(html),contentType,enconding,null);
    }

    WebSettings webSettings;
    @SuppressLint("SetJavaScriptEnabled")
    private void setWebSetting(Context context){
        // User settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enableSlowWholeDocumentDraw();
        }
        webSettings=getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//是否自适应屏幕
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDefaultTextEncodingName("utf-8");// 避免中文乱码
//启用数据库
        webSettings.setDatabaseEnabled(true);


//设置定位的数据库路径
        String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();


        webSettings.setGeolocationDatabasePath(dir);


//启用地理定位
        webSettings.setGeolocationEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

    }

    /**
     * 添加js回调方法
     * @param parent
     * @param jsParentName
     */
    @SuppressLint("JavascriptInterface")
    public void addJsMethod(Object parent, String jsParentName){
       this.addJavascriptInterface(parent,jsParentName);
    }

    public void setOnTitleChangeListener(final TitleChangeListener titleChangeListener){
        this.titleChangeListener=titleChangeListener;
        setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                titleChangeListener.webTitleChange(title);
            }
        });
    }

    TitleChangeListener titleChangeListener;
    public interface TitleChangeListener{
        void webTitleChange(String title);
    }

}
