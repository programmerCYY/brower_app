package com.example.mywebdemo.adblock;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class AdBlocker {
    private static final String AD_HOST_FILE = "host.txt";
    private static final Set<String> AD_HOST = new HashSet<>();

    public static void init(final Context context) {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    loadFromAssets(context);
                }catch (IOException e){

                }
                return null;
            }
        }.execute();
    }

    private static void loadFromAssets(Context context) throws IOException{

        InputStream stream = context.getAssets().open(AD_HOST_FILE);

        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        //通过管理器打开文件并读取
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        //逐行添加
        while ((line= bufferedReader.readLine())!=null)AD_HOST.add(line);
        //释放
        bufferedReader.close();
        inputStreamReader.close();
        stream.close();
    }

    public static boolean isAd(String url){
        try {
            return isAdHost(getHost(url))
                   || AD_HOST.contains(Uri.parse(url).getLastPathSegment());
        }catch ( MalformedURLException e)
        {
            return false;
        }
    }

    private static boolean isAdHost(String host){
        if (TextUtils.isEmpty(host)){
            return false;
        }
        int index = host.indexOf(".");
        return index >=0 && (AD_HOST.contains(host))
                         || index+1 <host.length()
                         && isAdHost(host.substring(index+1)) ;
    }

    public static String getHost(String url)throws MalformedURLException {
        return new URL(url).getHost();
    }


    //设置中间弹出界面可以修改这个方法

    public static WebResourceResponse createEmptyResourse(){
        return new WebResourceResponse("text/plain","utf-8", new ByteArrayInputStream("".getBytes()));
    }

}
