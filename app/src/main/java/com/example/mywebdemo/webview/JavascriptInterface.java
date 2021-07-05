package com.example.mywebdemo.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class JavascriptInterface {
    private Context context;
    private ArrayList<String> list_imgs = new ArrayList<>();
    private int index = 0;

    public JavascriptInterface(Context context) {
        this.context = context;
    }

    @android.webkit.JavascriptInterface
    public void openImage(String img,String[] imgs) {
//        Log.d("String", "ssss");
        try {
            list_imgs.clear();
            for (int i = 0; i < imgs.length; i++) {
                if (imgs[i].equals(img)) {
                    index = i;
                }
                list_imgs.add(imgs[i]);
            }
            Intent intent = new Intent();
            intent.setClass(context, ShowWebImageActivity.class);
            Bundle bundle=new Bundle();
            bundle.putStringArrayList("img",list_imgs);
            intent.putExtras(bundle);
            context.startActivity(intent);
            System.out.println(img);
            Log.d("String", list_imgs.toString());
        }catch (Exception e){
            Log.d("eeeeeee",e+"");
        }

    }


}
