package com.example.mywebdemo.webview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.mywebdemo.webview.ShowVideoActivity;

public class JavascriptInterfaceAdapter {
    private Context context;




    public JavascriptInterfaceAdapter(Context context){
        this.context = context;
    }

    @JavascriptInterface
    public void openVideo(String video){


        Intent intent = new Intent(context, ShowVideoActivity.class);

        intent.putExtra("video",video);
        Log.d("jsinterface", "openVideo: "+video);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
