package com.example.mywebdemo.adblock;
import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

//写个webview.setWebviewClient(NoAdWebviewClient webclient)即可屏蔽指定广告
public class NoAdWebviewClient extends WebViewClient{
    private String homeurl;
    private Context context;

    public NoAdWebviewClient(Context context,String homeurl){
        this.context = context;
        this.homeurl = homeurl;
    }




    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view,String url) {
        url = url.toLowerCase();
        if (!url.contains(homeurl)){
            if (!ADFilterTool.hasAd(context,url)){
                return super.shouldInterceptRequest(view, url);
            }else {
                return new WebResourceResponse(null,null,null );
            }
        }else {
            return super.shouldInterceptRequest(view,url);
        }
    }
}
