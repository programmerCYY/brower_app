package com.example.mywebdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;

public class JavascriptInterfaceAdapter {
    private Context context;




    public JavascriptInterfaceAdapter(Context context){
        this.context = context;
    }

    @JavascriptInterface
    public void openVideo(String video){


        Intent intent = new Intent(context,ShowVideoActivity.class);

        intent.putExtra("video",video);
        Log.d("jsinterface", "openVideo: "+video);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
