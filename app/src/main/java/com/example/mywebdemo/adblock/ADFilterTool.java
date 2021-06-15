package com.example.mywebdemo.adblock;
//currently useless
import android.content.Context;
import android.content.res.Resources;

import com.example.mywebdemo.R;


public class ADFilterTool {
    public static boolean hasAd(Context context, String url){
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls){
            if (url.contains(adUrl)){
                return true;
            }
        }return false;
    }
}
