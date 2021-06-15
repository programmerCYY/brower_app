package com.example.mywebdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mywebdemo.adblock.AdBlocker;
import com.example.mywebdemo.adblock.NoAdWebviewClient;

import java.util.HashMap;
import java.util.Map;

public class MyWebViewActivity extends AppCompatActivity {
    private WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //初始化浏览器
        initWebView();

        //获取url，这个参数从MainActivity传过来的
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        //访问url
        LoadUrl(url);

    }

    private void LoadUrl(String url) {

        webView.loadUrl(url);
    }



    private void initWebView() {
        webView = (WebView) findViewById(R.id.mywebview);
        WebSettings webSettings = webView.getSettings();

        webView.setVerticalScrollBarEnabled(false);
        //设置缓存
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        //设置JS支持
        //webSettings.setJavaScriptEnabled(true);
        //设置支持缩放变焦
        webSettings.setBuiltInZoomControls(false);
        //设置是否支持缩放
        webSettings.setSupportZoom(false);
        //设置是否允许JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //窗口设置为手机大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // 修复一些机型webview无法点击
        webView.requestFocus(View.FOCUS_DOWN);

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }

        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("http:") || url.startsWith("https:") ) {
                    view.loadUrl(url);
                    return false;
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            }

            //实现页面上的广告屏蔽。

            private Map<String,Boolean> loadedUrls = new HashMap<>();

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                boolean ad;
                if (!loadedUrls.containsKey(url)){
                    ad = AdBlocker.isAd(url);
                    loadedUrls.put(url,ad);
                }else {
                    ad = loadedUrls.get(url);
                }return ad ? AdBlocker.createEmptyResourse() : super.shouldInterceptRequest(view, url);
            }
        });



    }

}
