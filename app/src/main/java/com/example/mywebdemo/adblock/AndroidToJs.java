package com.example.mywebdemo.adblock;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class AndroidToJs extends Object{

    @JavascriptInterface
    public void getclient(String msg){
        Log.d("getclient", "getclient: "+ msg );
    }
}
