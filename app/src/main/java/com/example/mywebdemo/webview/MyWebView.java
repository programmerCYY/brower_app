package com.example.mywebdemo.webview;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mywebdemo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyWebView {

    //当前页面的现在的url
    private String myurl="";
    //当前页的webview
    private android.webkit.WebView webView;

    public void setMyurl(String url){
        myurl=url;
    }
    public String getMyurl(){return myurl;}
    public void setWebView(android.webkit.WebView webView) {
        this.webView = webView;
    }

    //注册浏览器
    public void initWebView(String url) {

//        LoadUrl(url);
        WebSettings webSettings = webView.getSettings();


        webView.setVerticalScrollBarEnabled(false);
        //窗口设置为手机大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
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


        // 修复一些机型webview无法点击
//        webView.requestFocus(View.FOCUS_DOWN);
//
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                    case MotionEvent.ACTION_UP:
//                        if (!v.hasFocus()) {
//                            v.requestFocus();
//                        }
//                        break;
//                }
//                return false;
//            }
//
//        });

        webView.setWebViewClient(new WebViewClient(){
            boolean if_load;
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if_load=false;
//                return false;
//            }


            //页面完成即加入历史记录
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                myurl=url;

            }

            //页面开始
            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
            }

        });
        LoadUrl(url);
        //拦截跳转后执行
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LoadUrl(url);
//                return true;
//            }
//
//       });
    }

    private void LoadUrl(String url) {
        //Log.d("tag","运行了一次");
        webView.loadUrl(url);
    }
    //判断http
    public static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//对比
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }
    //判断www
    public static boolean isUrl(String urls) {
        boolean isurl = false;
        String regex = "(([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//对比
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }

    //回退功能
    public void goBack(){
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    Log.d("goback","不能回退");
                    //finish();
                }
            }

    //前进
    public void goForward(){

        if (webView.canGoForward()) {
            webView.goForward();
        }else{
            Log.d("goForward","不能回退");
        }
    }

    //主页
    public void goHome(){
        initWebView("https://m.baidu.com/");
    }

    //刷新
    public void refresh(){
        webView.reload();
    }

    //搜索按钮
    public void start(String str){
        if (str.equals("")){
            str="https://m.baidu.com/";
        }else {
            if(isUrl(str)){
                str="http://"+str;
            }
            if(!isHttpUrl(str)){
//                         str = "http://www.baidu.com/baidu?tn=02049043_69_pg&le=utf-8&word=" + myEditText.getText().toString();
                str = "http://m.baidu.com/s?baiduid=8155C2BBA5E753A5E061F6569491FCEB&tn=baidulocal&le=utf-8&word=" + str+"&pu=sz%401321_480&t_noscript=jump";
            }
        }
        initWebView(str);
    }

          //  String str = myEditText.getText().toString();
}
