package com.example.mywebdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mywebdemo.adblock.AdSorting;
import com.example.mywebdemo.adblock.AndroidToJs;
import com.example.mywebdemo.adblock.NoAdWebviewClient;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    public ArrayList<String> urlList = new ArrayList<String>();
    public ArrayList<String> nameList = new ArrayList<String>();
    public ArrayList<String> flagList = new ArrayList<String>();
    public String currenturl="";
    private BridgeWebView webView ;
    public String jsUrl;



    //注册浏览器

    @SuppressLint("JavascriptInterface")
    private void initWebView(String url) {

        webView = (BridgeWebView) findViewById(R.id.mywebview);
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
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
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
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if(if_load) {
                    nameList.add(view.copyBackForwardList().getCurrentItem().getTitle());
                    currenturl=view.copyBackForwardList().getCurrentItem().getTitle();
                    urlList.add(currenturl);
                    nameList=removeDuplicate(nameList);
                    urlList=removeDuplicate(urlList);
                    Log.d("array", nameList.toString());
                    if_load=false;
                }
            }

            //页面开始
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);

                if_load=true;
            }

        });

        Context context = webView.getContext();

        //webView.setWebViewClient(new NoAdWebviewClient(context){
        //});

        webView.setWebViewClient(new WebViewClient(){
            @Override
            //重写urlloading接口，实现在打开超链接时拦截指定url，并重定向到一个本地html
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        Log.d("succeed", "shouldOverrideUrlLoading: "+url);
                jsUrl =url;
                AdSorting sortion = new AdSorting();
                String result = sortion.urlSorting(context,url);

                switch (result){
                    case "high":
                        view.loadUrl("file:///android_asset/demo1.html");
                        Log.d("success1", "shouldOverrideUrlLoading: "+url);
                        break;
                    case "medium":
                        view.loadUrl("yy");
                        Log.d("success2", "shouldOverrideUrlLoading: "+url);
                        break;
                    case"low":
                        view.loadUrl("zz");
                        Log.d("success3", "shouldOverrideUrlLoading: "+url);
                        break;
                    default:
                        view.loadUrl(url);
                }return super.shouldOverrideUrlLoading(view,url);
            }
        });

        webView.addJavascriptInterface(this, "Android");
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                if (message.equals("1")) {
                    Log.d("alert", "onJsAlert1: "+message);
//                    webView.canGoBack();
                    webView.goBack();
                    webView.goBack();
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                }else if (message.equals("0")) {
                    Log.d("alert", "onJsAlert2: "+message);
                    view.stopLoading();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl(jsUrl);

                        }
                    });

                    Log.d("alert", "onJsAlert: "+jsUrl);
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                result.confirm();
                return true;
            }
        });
//        webView.loadUrl("file:///android_asset/demo001.html");
        LoadUrl(url);
        //拦截跳转后执行
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrl(WebView view, String url) {
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

    @JavascriptInterface
    public void getClient(String msg){
        Log.d("getclient", "getclient: "+ msg );
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
        Log.d("tag","运行了一次");
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



    private Button btn_back;
    private Button btn_newwindow;
    private Button btn_forward;
    private Button btn_menu;
    private Button btn;
    private Button btn_more;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //这里用到了MenuInflater类，使用该类的inflate方法来读取xml文件并且建立菜单
        MenuInflater inflater = getMenuInflater();
        //设置menu界面为res/menu/menu.xml
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //按钮注册
        btn = (Button)findViewById(R.id.button);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_newwindow = (Button) findViewById(R.id.btn_exit);
        btn_forward = (Button) findViewById(R.id.btn_forward);
        btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_more=(Button) findViewById(R.id.btn_more) ;


        btn_more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //当前这个v就是 button2控件
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.history:
                                Intent intent = new Intent(MainActivity.this,historyActivity.class);
                                Bundle bundle=new Bundle();
                                //传递name参数为tinyphp
                                bundle.putStringArrayList("history",urlList);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case R.id.flag:
                                Log.d("TAG2", "书签");
                                break;
                            case R.id.add_flag:
                                flagList.add(currenturl);
                                flagList=removeDuplicate(flagList);
                                Log.d("array3", flagList.toString());
                                break;
                        }
                        return false;
                    }
                });

                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        //按钮添加监听事件
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }

            }
        });
        btn_newwindow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "多窗口",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btn_forward.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LoadUrl("https://m.baidu.com/");
            }
        });
        final EditText myEditText = (EditText) findViewById(R.id.edit_text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //url判断并且改成想要的格式
                String str = myEditText.getText().toString();
                if (str.equals("")){
                    str="https://m.baidu.com/";
                }else {
                    if(isUrl(str)){
                        str="http://"+str;
                    }
                    if(!isHttpUrl(str)){
//                         str = "http://www.baidu.com/baidu?tn=02049043_69_pg&le=utf-8&word=" + myEditText.getText().toString();
                        str = "http://m.baidu.com/s?baiduid=8155C2BBA5E753A5E061F6569491FCEB&tn=baidulocal&le=utf-8&word=" + myEditText.getText().toString()+"&pu=sz%401321_480&t_noscript=jump";
                    }
                }

                initWebView(str);
            }
        });

        webView = (BridgeWebView) findViewById(R.id.mywebview);
        webView.setDefaultHandler(new DefaultHandler());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/demo001.html");
        webView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("TAG", "js返回 "+data);
                if (data.equals("")){
                    data="https://m.baidu.com/";
                }else {
                    if(isUrl(data)){
                        data="http://"+data;
                    }
                    if(!isHttpUrl(data)){
//                         str = "http://www.baidu.com/baidu?tn=02049043_69_pg&le=utf-8&word=" + myEditText.getText().toString();
                        data = "http://m.baidu.com/s?baiduid=8155C2BBA5E753A5E061F6569491FCEB&tn=baidulocal&le=utf-8&word=" + data+"&pu=sz%401321_480&t_noscript=jump";
                    }
                }
                Log.d("jsbridge", "handler: "+data);
                initWebView(data);
            }
        });




    }


    //调用系统下载管理器的函数
    private void downloadBySystem(String url, String contentDisposition, String mimeType) {

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

        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

// 添加一个下载任务

        long downloadId = downloadManager.enqueue(request);

        System.out.println("downloadId:{}"+ downloadId);

    }

}
