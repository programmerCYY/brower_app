package com.example.mywebdemo.constance;

import android.graphics.Bitmap;
import android.webkit.WebView;

import com.example.mywebdemo.fragment.mainFrag;
import com.example.mywebdemo.webview.MyWebView;
import com.example.mywebdemo.user.User;

import java.util.ArrayList;
import java.util.List;

public class fragConst {

    public static List<mainFrag>  fraglist=new ArrayList<>();  //fraglist和fraghashcode成对出现
    public static List<String> fraghashcode=new ArrayList<>(); //存储对象的hashcode
    public static List<MyWebView>myWebViewList=new ArrayList<>();
    public static ArrayList<String>history_url=new ArrayList<>();
    public static ArrayList<String>history_name=new ArrayList<>();
    public static ArrayList<Bitmap>history_icon=new ArrayList<>();
    public static ArrayList<String>flag_url=new ArrayList<>();
    public static ArrayList<String>flag_name=new ArrayList<>();
    public static ArrayList<Bitmap>flag_icon=new ArrayList<>();
    public static ArrayList<User>user=new ArrayList<>();



    public static  int page_interval=1;  //界面之间的间隔

    public static  int init_page_count=1;  //初始化的界面个数

    public static  int new_mainfrag_count=0;  // mainFrag类构造函数被调用的次数

    public static String user_account = ""; //当前登录用户

    public static String http_msg = ""; // 后台链接失败返回信息

}
