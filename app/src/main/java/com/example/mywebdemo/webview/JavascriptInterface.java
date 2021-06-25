package com.example.mywebdemo.webview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class JavascriptInterface {
    private Context context;

    public JavascriptInterface(Context context) {
        this.context = context;
    }

    @android.webkit.JavascriptInterface
    public void openImage(String img) {
        Intent intent = new Intent();
        intent.putExtra("img", img);
        intent.setClass(context, ShowWebImageActivity.class);
        context.startActivity(intent);
        System.out.println(img);
    }


}
