package com.example.mywebdemo.webview;

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.constance.fragConst;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


import com.example.mywebdemo.R;
import com.example.mywebdemo.httputils.HttpUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.DOWNLOAD_SERVICE;

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
    public Bitmap getCurrent_icon(){return current_icon;}
    public void setWebView(WebView webView) {
        this.webView = webView;
    }
    public void change_isWindows(){isWindows=!isWindows;}
    public boolean get_isWindows(){return isWindows;}

    //注册浏览器
    public void initWebView(String url) {
//        webView.restoreState();
//        webView.saveState()
//        LoadUrl(url);
        webView.addJavascriptInterface(new JavascriptInterface(webView.getContext()), "imagelistener");
        Log.d("webView.getContext()",""+webView.getContext());
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
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(view, url);

                webView.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        " var array=new Array(); " +
                        " for(var j=0;j<objs.length;j++){ array[j]=objs[j].src; }"+
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "    objs[i].onclick=function()  " +
                        "    {  "
//                        +" console.log('picture_____')"
                        + "        imagelistener.openImage(this.src,array);  " +
                        "    }  " +
                        "}" +
                        "})()");



                //current_url=url;
                current_url=view.copyBackForwardList().getCurrentItem().getUrl();//获取url
                current_title=view.copyBackForwardList().getCurrentItem().getTitle();//获取标题

                //获取页面图标
                webView.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                        Log.d("onConsoleMessage: " ,consoleMessage.message());
                        return super.onConsoleMessage(consoleMessage);

                    }

                    @Override
                    public void onReceivedIcon(WebView view, Bitmap icon) {
                        super.onReceivedIcon(view, icon);
                        current_icon=icon;
                        //fragConst.history_icon.add(icon);

//                        Log.d("bitmap",""+current_icon);
//                        Log.d("bitmaplist",""+fragConst.history_icon);


                       if (fragConst.user_account!="") {
                           //Log.d("if","success");
                           HttpUtils httpUtils = new HttpUtils();
                           //上传图片拿到url
                           File f=httpUtils.bitmapChangeFile(current_icon);
                           try {
                               httpUtils.UploadPic(f);
                           } catch (FileNotFoundException e) {
                               e.printStackTrace();
                           }

                           try {
                               Thread.sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                           //添加到数据库文件
                           httpUtils.AddHistory(current_url, current_title, httpUtils.appendUrl(fragConst.icon_temp_string));
                           try {
                               Thread.sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                           fragConst.icon_temp_string="";
                          // Log.d("icon_temp_string",""+fragConst.icon_temp_string);
//                           Log.d("history_add", "" + fragConst.http_msg);
//                           if (fragConst.http_msg != "succ") {
//                               Log.d("unsuccess", "" + fragConst.http_msg);
//                               fragConst.http_msg = "";
//                           }
                       }else {
                           fragConst.history_url.add(current_url);
                           int sizeBefore=fragConst.history_url.size();
                           fragConst.history_url=removeDuplicate(fragConst.history_url);
                           int sizeAfter=fragConst.history_url.size();
                           if(sizeBefore==sizeAfter) {
                               fragConst.history_icon.add(current_icon);
                               fragConst.history_name.add(current_title);
                           }
                       }

                    }

                });



//                if(if_load && !current_title.equals(" ")) {
//                    fragConst.history_name.add(current_title);
//                    fragConst.history_url.add(current_url);
//                    fragConst.history_name=removeDuplicate(fragConst.history_name);
//                    fragConst.history_url=removeDuplicate(fragConst.history_url);
//                    Log.d("current_icon",""+current_icon);
//                    //Log.d("array", fragConst.history_url.toString());
//                    if_load=false;
//                }

//                if (fragConst.user_account!="") {
 //                   HttpUtils httpUtils = new HttpUtils();
                   //Log.d("bitmap",""+current_icon);
//                }

                //Log.d("url", url);
            }

            //页面开始
            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);

//                String jsStr="javascript:(function(){" +
//                        "var objs = document.getElementsByTagName(\"img\"); " +
//                        " var array=new Array(); " +
//                        " for(var j=0;j<objs.length;j++){ array[j]=objs[j].src; }"+
//                        "for(var i=0;i<objs.length;i++)  " +
//                        "{"
//                        + "    objs[i].onclick=function()  " +
//                        "    {  "
//                        + "        window.imagelistener.openImage(this.src,array);  " +
//                        "    }  " +
//                        "}" +
//                        "})()";

//                if (Build.VERSION.SDK_INT >= 19) {
//                webView.evaluateJavascript(jsStr, new ValueCallback<String>() {
//                    @Override public void onReceiveValue(String value) {//js与native交互的回调函数
//                        Log.d("TAG", "value=" + value);
//                    }
//                });
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

        //增加下载功能，调用系统的下载管理器
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                downloadBySystem(url,contentDisposition,mimetype);
            }
        });
    }

    //调用系统下载管理器的函数
    public void downloadBySystem(String url, String contentDisposition, String mimeType) {

// 指定下载地址

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

// 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库

        request.allowScanningByMediaScanner();

// 设置通知的显示类型，下载进行时和完成后显示通知

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

// 设置通知栏的标题，如果不设置，默认使用文件名

// request.setTitle("This is title");

// 设置通知栏的描述

// request.setDescription("This is description");

// 允许在计费流量下下载

        request.setAllowedOverMetered(false);

// 允许该记录在下载管理界面可见

        request.setVisibleInDownloadsUi(false);

// 允许漫游时下载

        request.setAllowedOverRoaming(true);

// 允许下载的网路类型

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

// 设置下载文件保存的路径和文件名

        String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);

        System.out.println("fileName:{}"+ fileName);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

// 另外可选一下方法，自定义下载路径

// request.setDestinationUri()

// request.setDestinationInExternalFilesDir()

        final DownloadManager downloadManager = (DownloadManager) webView.getContext().getSystemService(DOWNLOAD_SERVICE);

// 添加一个下载任务

        long downloadId = downloadManager.enqueue(request);

        System.out.println("downloadId:{}"+ downloadId);

    }









//判断是否有重复
    public static boolean isDuplicate(ArrayList <String>list){
        HashSet<String> temp = new HashSet<String>();
        for(int i=0;i<list.size();i++){
            Log.d("index:"+i,list.get(i)+"");
            temp.add(list.get(i));
        }
        if(temp.size()==list.size()){
            return false;
        }else {
            return true;
        }


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
//    public String bitmapToString(Bitmap bitmap){
//        //将Bitmap转换成字符串
//        String string=null;
//        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,10,bStream);
//        byte[]bytes=bStream.toByteArray();
//        string= Base64.encodeToString(bytes, Base64.DEFAULT);
//        return string;
//    }

    //  String str = myEditText.getText().toString();
}
