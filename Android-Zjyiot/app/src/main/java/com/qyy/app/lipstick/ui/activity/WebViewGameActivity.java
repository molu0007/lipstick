package com.qyy.app.lipstick.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;
import com.qyy.app.lipstick.ui.activity.mall.OrderActivity;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-22 17:10
 * @name: WebViewGameActivity
 */
public class WebViewGameActivity extends BaseActivity {
    WebView mWebview;
    WebSettings mWebSettings;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mWebview = (WebView) findViewById(R.id.webView1);
        initWebSetting();
        String url=getIntent().getStringExtra("url");
        mWebview.loadUrl(url);

        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri=Uri.parse(url);
                if (uri.getScheme().equals("sdk")){
                    String value=uri.getQueryParameter("value");
                    if (value.equals("1")){//首页
                        finish();
                    }else if(value.equals("2")){//订单页
                        Intent intent=new Intent(WebViewGameActivity.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view,url);
            }

        });
    }
    /**
     * 初始化网络设置
     */
    private void initWebSetting() {
        //声明WebSettings子类
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 修改ua使得web端正确判断

//        webSettings.setUserAgentString(ua + "; tag=app;account=" + account);  //添加UA,  “app/XXX”：是与h5商量好的标识，h5确认UA为app/XXX就认为该请求的终端为App
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//根据cache-control决定是否从网络上取数据。
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        mWebview.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public int getDouble(int value) {
                return value;
            }

            @JavascriptInterface
            public int triple(int value) {
                return value ;
            }
        }, "sdk");
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
    }

}
