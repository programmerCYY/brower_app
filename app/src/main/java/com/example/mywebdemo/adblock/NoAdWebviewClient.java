package com.example.mywebdemo.adblock;
import android.content.Context;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;


//写个webview.setWebviewClient(NoAdWebviewClient webclient)即可屏蔽指定广告
//有个string.xml（AdUrlString）记录了约定好的协议规范的文档
//本质就是利用这个回调接口拦截url，然后解析这个url的协议，如果是预先约定好的协议就开始解析参数，执行逻辑。
//这个方法可以用来拦截Webview中加载url的过程（okhttp无法实现）代码比较简单适合只在native打开页面的时候
//如果web想要得到方法的返回值就只能用loadUrl去执行JS方法把返回值传递回去
//currently useless

public class NoAdWebviewClient extends WebViewClient{
    private String homeurl;
    private Context context;

    public NoAdWebviewClient(Context context,String homeurl){
        this.context = context;
        this.homeurl = homeurl;
    }



//  api24版本以下
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

//    api24版本以上
//    @Nullable
//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//        String url = request.getUrl().toString().toLowerCase();
//        if (!url.contains(homeurl)){
//            if (!ADFilterTool.hasAd(context,url)){
//                return super.shouldInterceptRequest(view,request);
//            }else {
//                return new WebResourceResponse(null,null,null);
//            }
//        }else {
//            return super.shouldInterceptRequest(view, request);
//        }
//    }
}
