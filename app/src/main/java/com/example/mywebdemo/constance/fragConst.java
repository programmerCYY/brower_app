package com.example.mywebdemo.constance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;

import com.example.mywebdemo.fragment.mainFrag;
import com.example.mywebdemo.webview.MyWebView;
import com.example.mywebdemo.user.User;
import com.example.mywebdemo.news.NewsItem;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fragConst {

    public static List<mainFrag>  fraglist=new ArrayList<>();  //fraglist和fraghashcode成对出现
    public static List<String> fraghashcode=new ArrayList<>(); //存储对象的hashcode
    public static List<MyWebView>myWebViewList=new ArrayList<>();
    public static ArrayList<String>history_url=new ArrayList<>();
    public static ArrayList<String>history_name=new ArrayList<>();
    public static ArrayList<Bitmap>history_icon=new ArrayList<>();
    public static ArrayList<String>history_icon_string=new ArrayList<>();

    public static ArrayList<String>flag_url=new ArrayList<>();
    public static ArrayList<String>flag_name=new ArrayList<>();
    public static ArrayList<Bitmap>flag_icon=new ArrayList<>();
    public static ArrayList<String>flag_icon_string=new ArrayList<>();

    public static ArrayList<NewsItem> news_list = new ArrayList<>();

    public static String icon_temp_string;
    public static User user;



    public static  int page_interval=1;  //界面之间的间隔

    public static  int init_page_count=1;  //初始化的界面个数

    public static  int new_mainfrag_count=0;  // mainFrag类构造函数被调用的次数

    public static String user_account = ""; //当前登录用户

    public static String http_msg = ""; // 后台链接失败返回信息


    public static String bitmapToString(Bitmap bitmap){
        //将Bitmap转换成字符串
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();

//        Log.d("bitmap2",""+bitmap);

        bitmap.compress(Bitmap.CompressFormat.PNG,10,bStream);
        byte[]bytes=bStream.toByteArray();
        string=Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    public static Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

//    public static File saveBitmapFile(Bitmap bitmap) throws IOException {
//        String path= Environment.getDataDirectory()+"/imag_1.jpg";
//        File file=new File(path);
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
//            bos.flush();
//            bos.close();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        FileInputStream fs=new FileInputStream(path);
//
//    }

    public static Bitmap loadBitmap(String picturePath) throws FileNotFoundException {
//        BitmapFactory.Options opt = new BitmapFactory.Options();
//        opt.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        // 获取到这个图片的原始宽度和高度
//        int picWidth = opt.outWidth;
//        int picHeight = opt.outHeight;
//        // 获取画布中间方框的宽度和高度
//        int screenWidth = CameraManager.MAX_FRAME_WIDTH;
//        int screenHeight = CameraManager.MAX_FRAME_HEIGHT;
//        // isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
//        opt.inSampleSize = 1;
//        // 根据屏的大小和图片大小计算出缩放比例
//        if (picWidth > picHeight) {
//            if (picWidth > screenWidth)
//                opt.inSampleSize = picWidth / screenWidth;
//        } else {
//            if (picHeight > screenHeight)
//                opt.inSampleSize = picHeight / screenHeight;
//        }
//        // 生成有像素经过缩放了的bitmap
//        opt.inJustDecodeBounds = false;
//        bitmap = BitmapFactory.decodeFile(picturePath, opt);
//        if (bitmap == null) {
//            throw new FileNotFoundException("Couldn't open " + picturePath);
//        }
        return bitmap;
    }



}
