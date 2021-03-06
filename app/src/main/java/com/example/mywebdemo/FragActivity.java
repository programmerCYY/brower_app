package com.example.mywebdemo;

//import com.nostra13.universalimageloader;
import com.example.mywebdemo.news.NewsActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.custom.mainActivitySimpleOnGestureListener;
import com.example.mywebdemo.customview.verticalViewPager;
import com.example.mywebdemo.event.baseEvent;
import com.example.mywebdemo.event.delThisFrag;
import com.example.mywebdemo.event.deleteFragEvent;
import com.example.mywebdemo.event.fragEvent;
import com.example.mywebdemo.event.showDelImg;
import com.example.mywebdemo.event.slideEvent;
import com.example.mywebdemo.event.zoomEvent;
import com.example.mywebdemo.flag.flagActivity;
import com.example.mywebdemo.fragment.fragAdapter;
import com.example.mywebdemo.fragment.mainFrag;
import com.example.mywebdemo.history.historyActivity;
import com.example.mywebdemo.httputils.HttpUtils;
import com.example.mywebdemo.user.LoginActivity;
import com.example.mywebdemo.user.MeActivity;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.widget.RelativeLayout.*;

public class FragActivity extends FragmentActivity {

    private verticalViewPager mViewPager;
    private fragAdapter fragPagerAdapter;
    private TextView pagebt;
    private ImageView leftbt, rightbt, setbt, homebt, delfrag, deleteallpage,addnewpage, returnmain,img1;
    private LinearLayout llayoutviewpage, pagebarlt, mainbarlt,mainbar;
    private DisplayMetrics dm2;
    private GestureDetectorCompat mDetector;
    private PercentRelativeLayout mainrootrl;
    private mainActivitySimpleOnGestureListener mainSimpleOnGestureListener;
    private static boolean isDay;
    private static boolean isNoHistory;
    private static String url="";//???????????????url


    public static boolean getIsNoHistory() {
        return isNoHistory;
    }

    public static boolean getIsDay(){
        return isDay;
    }


    public static void setUrl(String s){
        url=s;
    }









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final int EXTERNAL_STORAGE_REQ_CODE = 10 ;

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // ????????????
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQ_CODE);
        }


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //????????????
        isDay=true;
        //????????????
        isNoHistory=false;

        //fraglist???fragment?????????fraghashcode????????????????????????
        for (int i = 0; i < fragConst.init_page_count; i++) {
            mainFrag tmp=new mainFrag();
            fragConst.fraglist.add(tmp);
            fragConst.fraghashcode.add(String.valueOf(tmp.hashCode()));
        }
        //??????ViewPager
        mViewPager = (verticalViewPager) findViewById(R.id.mainviewpage);
        fragPagerAdapter = new fragAdapter(this, getSupportFragmentManager());
//        getSupportFragmentManager().getFragment();
        mViewPager.setAdapter(fragPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //??????ViewPager??????????????????
        EventBus.getDefault().register(this);
        mainSimpleOnGestureListener = new mainActivitySimpleOnGestureListener();
        mDetector = new GestureDetectorCompat(this, mainSimpleOnGestureListener);
        mainrootrl = (PercentRelativeLayout) findViewById(R.id.mainrootrl);

        //???????????????
        viewInit();

    }

    private void viewInit() {



        leftbt = (ImageView) findViewById(R.id.leftbt);
        rightbt = (ImageView) findViewById(R.id.rightbt);
        setbt = (ImageView) findViewById(R.id.setbt);
        pagebt = (TextView) findViewById(R.id.pagebt);
        homebt = (ImageView) findViewById(R.id.homebt);
        delfrag = (ImageView) findViewById(R.id.delfrag);

        deleteallpage=(ImageView) findViewById(R.id.deleteallpage);
        addnewpage = (ImageView) findViewById(R.id.addnewpage);
        returnmain = (ImageView) findViewById(R.id.returnmain);

        leftbt.setOnClickListener((View v) -> {
            bthander(v.getId(),v);
        });
        rightbt.setOnClickListener((View v) -> {
            bthander(v.getId(),v);
        });
        setbt.setOnClickListener((View v) -> {
            bthander(v.getId(),v);
        });
        pagebt.setOnClickListener((View v) -> {
            bthander(v.getId(),v);
        });
        homebt.setOnClickListener((View v) -> {
            bthander(v.getId(),v);
        });


        deleteallpage.setOnClickListener((View v)->{
            bthander(v.getId(),v);
        });
        addnewpage.setOnClickListener((View v) -> {
            bthander(v.getId(),v);
        });
        returnmain.setOnClickListener((View v) -> {
            bthander(v.getId(),v);
        });

//        if(fromAnotherurl){
//            //        //???????????????????????????????????????
////        if(getIntent()!=null){
////            String toUrl=getIntent().getStringExtra("toUrl");
////            //Log.d("toUrl",toUrl);
//           goUrl(url);
//           fromAnotherurl=false;
////        }
//        }

        mainbarlt = (LinearLayout) findViewById(R.id.mainbarlt);
        pagebarlt = (LinearLayout) findViewById(R.id.pagebarlt);
        llayoutviewpage = (LinearLayout) findViewById(R.id.llayoutviewpage);
        dm2 = getResources().getDisplayMetrics();

        mainrootrl.setOnTouchListener((View v, MotionEvent event) -> {
            mDetector.onTouchEvent(event);
            return true;
        });
        pagebt.setText(fragConst.fraglist.size() + "");








    }


    //????????????
    private void bthander(int id,View v) {
        switch (id) {
            case R.id.leftbt:
                goBack();
                //command="goBack";
//                Log.d("gol","go");
                break;
            case R.id.rightbt:
                goForward();
                //command="goforward";
                break;
            case R.id.setbt:
                showPopupWindow(v);

                break;
            case R.id.pagebt:
                zoomchange();

                break;
            case R.id.homebt:
                //command="goHome";
                goHome();
                //   removePage(mViewPager.getCurrentItem());
                break;
            /**********************************************************/
            case R.id.deleteallpage:
                deleteAllPage();
                break;
            case R.id.addnewpage:
                addNewPage();
                break;
            case R.id.returnmain:
                zoomchange();
                break;

        }


    }
    public void goBack(){
        //Log.d("goback","gobacksuccess");
//        mainFrag goback=mainFrag.newInstance("goBack");
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.llayoutviewpage, goback)
//                .commit();

        //return "goBack";

//        if(mOnTitleClickListener != null){
//            mOnTitleClickListener.onClick("OK");
//        }
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
        m.goBack();


        //getSupportFragmentManager().getFragment();

    }
    public void goForward(){
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
        m.goForward();


    }
    public void goHome(){
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
        m.goHome();


    }

    private void showPopupWindow(View view){

        View v ;
        if(isDay) {
            v = LayoutInflater.from(this).inflate(R.layout.popuplayout, null);
        }else {
            v=LayoutInflater.from(this).inflate(R.layout.popuplayout_night, null);
        }

        //??????????????????
        v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight=v.getMeasuredHeight();

        //?????????????????????????????????
        mainbarlt.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int barHeight=mainbarlt.getMeasuredHeight();

        final PopupWindow window=new PopupWindow(v,getWindowManager().getDefaultDisplay().getWidth(),popupHeight,true);
        //????????????
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //??????????????????????????????????????????????????????????????????
        window.setOutsideTouchable(true);
        //??????????????????????????????????????????????????????
        window.setTouchable(true);
        //?????????????????????   ???????????????style???????????????????????????anim???????????????animation?????????    ????????????????????????????????????????????????????????????????????????style????????????
        window.setAnimationStyle(R.style.translate_anim);
        //??????1(anchor)??????
        //??????2???3??????????????????x???y?????????????????????
        window.showAsDropDown(view,-getWindowManager().getDefaultDisplay().getWidth(),-popupHeight-barHeight-50);


        //???????????????????????????
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());

        if(m.get_isWindows()){
            ImageView imageView=v.findViewById(R.id.popup_window_imagine);
            imageView.setImageResource(R.mipmap.mobile_phone);
            TextView textView=v.findViewById(R.id.popup_window_text);
            textView.setText("?????????");

        }

        //????????????????????????
        if(isDay){

        }else{
            //mainbarlt.setBackground(getDrawable(R.color.night));
            ImageView imageView=v.findViewById(R.id.popup_night_imagine);
            imageView.setImageResource(R.mipmap.ic_sun);
            TextView textView=v.findViewById(R.id.popup_night_text);
            textView.setText("????????????");
        }


        //??????????????????
        if(isNoHistory){
            ImageView imageView=v.findViewById(R.id.popup_no_history);
            imageView.setImageResource(R.mipmap.no_history_active);
            TextView textView=v.findViewById(R.id.popup_no_history_text);
            textView.setTextColor(getResources().getColor(R.color.active));
            //???????????????????????????
            LinearLayout linearLayout=v.findViewById(R.id.popup_set);
            linearLayout.setEnabled(false);
            LinearLayout linearLayout1=v.findViewById(R.id.popup_share);
            linearLayout1.setEnabled(false);

        }



        img1=v.findViewById(R.id.user_pic);
        if(fragConst.user_account!=""){
            TextView textView=(TextView)v.findViewById(R.id.user_name);
            textView.setText(""+fragConst.user_account);
            HttpUtils httpUtils=new HttpUtils();
            httpUtils.GetUser();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            img1.setImageBitmap(returnBitMap(fragConst.user.getAvatar()));


        }
        v.findViewById(R.id.popup_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                showImagePickDialog();

                Intent intent2 = new Intent(FragActivity.this, NewsActivity.class);
                startActivity(intent2);
                window.dismiss();
            }
        });



        v.findViewById(R.id.to_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragConst.user_account=="") {
                    Intent intent2 = new Intent(FragActivity.this, LoginActivity.class);
                    startActivity(intent2);
                }else {
                    Intent intent3 = new Intent(FragActivity.this, MeActivity.class);
                    startActivity(intent3);
                }
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_bookmark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(FragActivity.this, flagActivity.class);
                startActivity(intent2);
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
//                mRecyclerView = findViewById(R.id.recycler_view);
                Intent intent = new Intent(FragActivity.this, historyActivity.class);
                startActivity(intent);
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"???????????????????????????",Toast.LENGTH_SHORT).show();
                PackageInfo pi = null;
                try {
                    pi = getPackageManager().getPackageInfo("com.amaze.filemanager", 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
                resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                resolveIntent.setPackage(pi.packageName);


                List<ResolveInfo> apps = getPackageManager().queryIntentActivities(resolveIntent, 0);

                ResolveInfo ri = apps.iterator().next();
                if (ri != null ) {
                    String packageName = ri.activityInfo.packageName;
                    String className = ri.activityInfo.name;

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);

                    ComponentName cn = new ComponentName(packageName, className);

                    intent.setComponent(cn);
                    startActivity(intent);
                }
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
                ClipboardManager cbm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cbm.setText(m.geturl());
                Toast.makeText(FragActivity.this,"??????????????????????????????",Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Toast.makeText(FragActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                refresh();
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
                m.change_isWindows();


                Toast.makeText(FragActivity.this,!m.get_isWindows()?"??????????????????":"??????????????????",Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });
        v.findViewById(R.id.popup_no_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNoHistory=!isNoHistory;
                if(isNoHistory) {
                    Toast.makeText(FragActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(FragActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
                window.dismiss();
            }
        });
        v.findViewById(R.id.popup_night).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDay=!isDay;
                mainbar=(LinearLayout)findViewById(R.id.mainbar);
                TextView pagebt=(TextView) findViewById(R.id.pagebt);
                if(isDay){
                    mainbar.setBackgroundColor(getColor(R.color.day));
                }else{
                    //mainbarlt.setBackground(getDrawable(R.color.night));
                    pagebt.setTextColor(getResources().getColor(R.color.gray));
                    mainbar.setBackground(getDrawable(R.color.night));
                }
                //??????
                fragPagerAdapter.notifyDataSetChanged();




                Toast.makeText(FragActivity.this,!isDay?"?????????????????????":"?????????????????????",Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });
        HttpUtils httpUtils=new HttpUtils();
        v.findViewById(R.id.popup_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
                String url=m.geturl();
                String title=m.gettitle();
                Bitmap icon=m.geticon();



                if (fragConst.user_account!="") {
                    HttpUtils httpUtils = new HttpUtils();

                    //??????????????????url
                    File f=httpUtils.bitmapChangeFile(icon);
                    try {
                        httpUtils.UploadPic(f);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //??????????????????
                    httpUtils.AddFlag(url, title, httpUtils.appendUrl(fragConst.icon_temp_string));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fragConst.icon_temp_string="";
                }else {
                    fragConst.flag_url.add(url);
                    int sizeBefore=fragConst.flag_url.size();
//                    Log.d("flag_url_1",""+fragConst.flag_url.size()+" "+fragConst.flag_url);
//                           fragConst.history_name=removeDuplicate(fragConst.history_name);
                    fragConst.flag_url=removeDuplicate(fragConst.flag_url);
                    int sizeAfter=fragConst.flag_url.size();
                    if(sizeBefore==sizeAfter) {
                        fragConst.flag_icon.add(icon);
                        fragConst.flag_name.add(title);
                    }

                }

                Toast.makeText(FragActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });
        v.findViewById(R.id.popup_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
                window.dismiss();
                finish();
            }
        });
    }

    public void refresh(){
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
        m.refresh();
    }

    //??????
    public static ArrayList removeDuplicate(ArrayList list){
        ArrayList tempList = new ArrayList(list.size());
        for(int i=0;i<list.size();i++){
            if(!tempList.contains(list.get(i)))
                tempList.add(list.get(i));
        }
        return tempList;
    }




    private void deleteAllPage(){

        if(fragConst.fraglist.size()==1 && fragConst.fraghashcode.size()==1) {
            return;

        }else {
            //??????size?????????
            int length=fragConst.fraglist.size();
                for(int i = 1; i<length;i++)
                {
                    fragConst.fraghashcode.remove(fragConst.fraghashcode.size()-1);
                    fragConst.fraglist.remove(fragConst.fraglist.size()-1);
                }


            fragPagerAdapter.notifyDataSetChanged();
        }
        mainFrag m=fragConst.fraglist.get(0);
        m.goHome();
    }


    private void addNewPage() {

        mainFrag tmp=new mainFrag();

        fragConst.fraghashcode.add(String.valueOf( tmp.hashCode()));

        fragConst.fraglist.add(mViewPager.getCurrentItem() + 1, tmp);

        fragPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (dm2.widthPixels - mViewPager.getWidth() < 5) {
            } else {
                zoomchange();
            }
        }, 400);

    }

    private void removePage(int position) {
        if (position >= 0 && position < fragConst.fraglist.size()) {
            if (fragConst.fraglist.size() <= 1) {
                return;
            }
            fragConst.fraglist.remove(position);
            fragConst.fraghashcode.remove(position);
            fragPagerAdapter.notifyDataSetChanged();
        }
    }


    private boolean currentIsFull = true;//?????????????????????

    private void zoomchange() {

        int thewidth = mViewPager.getWidth();
        if (dm2.widthPixels - mViewPager.getWidth() < 5) {
            mViewPager.setPageMargin(fragConst.page_interval);
            //   Logger.v("??????  " + thewidth);
            llayoutviewpage.setGravity(CENTER_IN_PARENT);


            //??????
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.8f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.8f);
            ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(mViewPager, pvhX, pvhY);
            scale.setDuration(30);
            scale.start();


            RelativeLayout.LayoutParams Rlparam = new RelativeLayout.LayoutParams(dm2.widthPixels * 8 / 10, dm2.heightPixels * 8 / 10);
            Rlparam.addRule(CENTER_IN_PARENT);
            llayoutviewpage.setLayoutParams(Rlparam);


            mainSimpleOnGestureListener.setViewPagePosition(mViewPager.getWidth(), mViewPager.getHeight());
            pagebarlt.setVisibility(VISIBLE);
            mainbarlt.setVisibility(INVISIBLE);
            EventBus.getDefault().post(new zoomEvent(false));
        } else {
            //    Logger.v("??????  " + thewidth + " ----  " + dm2.widthPixels);
            mViewPager.setPageMargin(0);

            //?????????????????????
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1f);
            ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(mViewPager, pvhX, pvhY);
            scale.setDuration(30);
            scale.start();
            currentIsFull = true;
            RelativeLayout.LayoutParams Rlparam2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Rlparam2.addRule(CENTER_IN_PARENT);
            llayoutviewpage.setLayoutParams(Rlparam2);
            pagebarlt.setVisibility(INVISIBLE);
            mainbarlt.setVisibility(VISIBLE);
            pagebt.setText(fragConst.fraglist.size() + "");
            EventBus.getDefault().post(new zoomEvent(true));
        }
    }


    @Subscribe
    public void onEventMainThread(baseEvent event) {

        if (event instanceof fragEvent) {
            //  Toast.makeText(this, " ?????? event ??????  " + ((fragEvent) event).getFragTag(), Toast.LENGTH_SHORT).show();
            if (dm2.widthPixels - mViewPager.getWidth() < 5) {
            } else {
                zoomchange();
            }
        }
        if (event instanceof slideEvent) {
            //   Toast.makeText(this, " ?????? event ??????  " + ((slideEvent) event).getType()+"  "+((slideEvent) event).getDirection(), Toast.LENGTH_SHORT).show();
            switch (((slideEvent) event).getType()) {
                case MotionEvent.ACTION_MOVE:
                    switch (((slideEvent) event).getDirection()) {
                        case "left":
                            break;
                        case "right":
                            break;
                    }

                    break;
                case MotionEvent.ACTION_DOWN:
                    switch (((slideEvent) event).getDirection()) {
                        case "left":
                            int cItem = mViewPager.getCurrentItem();
                            mViewPager.setCurrentItem(cItem > 0 ? cItem - 1 : cItem);
                            break;
                        case "right":
                            int rItem = mViewPager.getCurrentItem();
                            mViewPager.setCurrentItem(rItem < fragConst.fraglist.size() - 1 ? rItem + 1 : rItem);
                            break;
                    }
                    break;
            }
        }
        if (event instanceof deleteFragEvent) {
            int i = 0;
            String Tag = ((deleteFragEvent) event).getFragTag();
            for (mainFrag temp : fragConst.fraglist) {
                if (temp.getFragTag().equals(Tag)) {

                    Handler dohandler = new Handler();
                    final int page = i;
                    dohandler.postDelayed(() -> {
                        // Logger.v("??????  page " + page);
                        removePage(page);
                    }, 80);
                }
                i++;
            }
        }
        if (event instanceof showDelImg) {
            if (((showDelImg) event).isShow()) {
                delfrag.setVisibility(VISIBLE);
            } else {
                delfrag.setVisibility(INVISIBLE);
            }
        }
        if (event instanceof delThisFrag) {
            removePage(mViewPager.getCurrentItem());
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(url!=""){
            goUrl(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        fragConst.fraglist.clear();
        fragConst.new_mainfrag_count = 0; //???????????????0
    }



    public void goUrl(String str){
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
        m.goUrl(str);
    }


    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }




}
