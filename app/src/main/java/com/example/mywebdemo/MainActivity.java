package com.example.mywebdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
public class MainActivity extends AppCompatActivity {

    private WebView webView ;
    //注册浏览器
    private void initWebView(String url) {

        webView = (WebView) findViewById(R.id.mywebview);
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

        webView.setWebViewClient(new WebViewClient());
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
                                Log.d("TAG", "历史");
                                break;
                            case R.id.flag:
                                Log.d("TAG2", "书签");
                                break;
                            case R.id.add_flag:
                                Log.d("TAG3", "添加书签");
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
                LoadUrl("https://www.baidu.com/");
            }
        });
        final EditText myEditText = (EditText) findViewById(R.id.edit_text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //url判断并且改成想要的格式
                String str = myEditText.getText().toString();
                if (str.equals("")){
                    str="https://www.baidu.com/";
                }else {
                    if(isUrl(str)){
                        str="http://"+str;
                    }
                    if(!isHttpUrl(str)){
                         str = "http://www.baidu.com/baidu?tn=02049043_69_pg&le=utf-8&word=" + myEditText.getText().toString();
                    }
                }

                initWebView(str);
            }
        });
    }
}*/
