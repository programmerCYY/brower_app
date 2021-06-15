package com.example.mywebdemo.webview;

import com.example.mywebdemo.constance.fragConst;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.example.mywebdemo.R;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyWebView {

    //当前页面的现在的url
    private String current_url="";
    //当前页面url的名字
    private String current_title="";
    //当前网页的图标
    private Bitmap current_icon;

    //当前页的webview
    private WebView webView;
    private boolean isWindows=false;

    public void setMyurl(String url){
        current_url=url;
    }
    public String getMyurl(){return current_url;}
    public String getCurrent_title(){return current_title; }
    public void setWebView(WebView webView) {
        this.webView = webView;
    }
    public void change_isWindows(){isWindows=!isWindows;}
    public boolean get_isWindows(){return isWindows;}

    //注册浏览器
    public void initWebView(String url) {

//        LoadUrl(url);
        WebSettings webSettings = webView.getSettings();

//        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setVerticalScrollBarEnabled(false);
        //窗口设置为手机大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //设置缓存
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        //设置JS支持
        webSettings.setJavaScriptEnabled(true);
        //这句话必须保留。。否则无法播放优酷视频网页。。其他的可以
        webSettings.setDomStorageEnabled(true);
        //设置支持缩放变焦
        webSettings.setBuiltInZoomControls(false);
        //设置是否支持缩放
        webSettings.setSupportZoom(true);
        //设置是否允许JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setDefaultTextEncodingName("utf-8") ;//这句话去掉也没事。。只是设置了编码格式
        webSettings.setJavaScriptEnabled(true);  //这句话必须保留。。不解释
        webSettings.setDomStorageEnabled(true);//这句话必须保留。。否则无法播放优酷视频网页。。其他的可以


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
                //current_url=url;
                current_url=view.copyBackForwardList().getCurrentItem().getUrl();//获取url
                current_title=view.copyBackForwardList().getCurrentItem().getTitle();//获取标题

                //以下代码是保存图片
//                current_icon = view.copyBackForwardList().getCurrentItem().getFavicon();//获取网页图标
//                File file=new File(view.getContext().getFilesDir(),fragConst.history_icon.size()+".jpg");
//                fragConst.history_icon.add(file.getPath());
//                //Log.d("path",""+file.getPath());
//                Thread thread=new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(!file.exists()) {
//                            try {
//                                FileOutputStream bos = new FileOutputStream(file);
//                                current_icon.compress(Bitmap.CompressFormat.JPEG, 1, bos);
//                                bos.flush();
//                                bos.close();
//                                Log.d("pic", "success");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }else{
//                            Log.d("pic2","unsuccess");
//                        }
//                    }
//                });
//                thread.start();


                if(if_load && !current_title.equals(" ")) {
                    fragConst.history_name.add(current_title);
                    fragConst.history_url.add(current_url);
                    fragConst.history_name=removeDuplicate(fragConst.history_name);
                    fragConst.history_url=removeDuplicate(fragConst.history_url);
                    //Log.d("array", fragConst.history_url.toString());
                    if_load=false;
                }

                //Log.d("url", url);
            }

            //页面开始
            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
                if_load=true;
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
    //去重
    public static ArrayList removeDuplicate(ArrayList list){
        ArrayList tempList = new ArrayList(list.size());
        for(int i=0;i<list.size();i++){
            if(!tempList.contains(list.get(i)))
                tempList.add(list.get(i));
        }
        return tempList;
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
        if(!isWindows) {
            initWebView("https://m.baidu.com/");
        }else {
            initWebView("http://www.baidu.com/");

        }
    }


    //刷新
    public void refresh(){
        webView.reload();
    }

    //搜索按钮
    public void start(String str){
        if (str.equals("")){
            if(!isWindows) {
                str = "https://m.baidu.com/";
            }else {
                str="https://www.baidu.com/";
            }
        }else {
            if(isUrl(str)){
                str="http://"+str;
            }
            if(!isHttpUrl(str)){
                if(isWindows) {
                    str = "http://www.baidu.com/baidu?tn=02049043_69_pg&le=utf-8&word=" + str;

                }else {
                    str = "http://m.baidu.com/s?baiduid=8155C2BBA5E753A5E061F6569491FCEB&tn=baidulocal&le=utf-8&word=" + str+"&pu=sz%401321_480&t_noscript=jump";

                }
            }
        }
        initWebView(str);
    }

    public void goUrl(String url) {
        setMyurl(url);
        initWebView(url);
    }

    //  String str = myEditText.getText().toString();
}
