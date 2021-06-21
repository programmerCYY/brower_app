package com.example.mywebdemo.adblock;

import android.content.Context;

public class AdSorting {


    public String urlSorting(Context context,String url){
        AdBlocker.init(context);
        AdBlocker2.init(context);
        AdBlocker3.init(context);

        if (AdBlocker.isAd(url)){
            return "high";
        }else if (AdBlocker2.isAd(url)){
            return "medium";
        }else if (AdBlocker3.isAd(url)){
            return "low";
        }else return "none";

    }


}
