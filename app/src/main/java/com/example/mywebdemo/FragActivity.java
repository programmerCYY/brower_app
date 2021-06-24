package com.example.mywebdemo;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.mywebdemo.user.LoginActivity;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static android.widget.RelativeLayout.*;

public class FragActivity extends FragmentActivity {

    private verticalViewPager mViewPager;
    private fragAdapter fragPagerAdapter;
    private TextView pagebt;
    private ImageView leftbt, rightbt, setbt, homebt, delfrag, deleteallpage,addnewpage, returnmain;
    private LinearLayout llayoutviewpage, pagebarlt, mainbarlt,mainbar;
    private DisplayMetrics dm2;
    private GestureDetectorCompat mDetector;
    private PercentRelativeLayout mainrootrl;
    private mainActivitySimpleOnGestureListener mainSimpleOnGestureListener;
    private static boolean isDay;
    private static String url="";//接收跳转的url



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


        //白天模式
        isDay=true;

        //fraglist存fragment对象，fraghashcode存对应随机哈希码
        for (int i = 0; i < fragConst.init_page_count; i++) {
            mainFrag tmp=new mainFrag();
            fragConst.fraglist.add(tmp);
            fragConst.fraghashcode.add(String.valueOf(tmp.hashCode()));
        }
        //设置ViewPager
        mViewPager = (verticalViewPager) findViewById(R.id.mainviewpage);
        fragPagerAdapter = new fragAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(fragPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //设置ViewPager上下滑监听器
        EventBus.getDefault().register(this);
        mainSimpleOnGestureListener = new mainActivitySimpleOnGestureListener();
        mDetector = new GestureDetectorCompat(this, mainSimpleOnGestureListener);
        mainrootrl = (PercentRelativeLayout) findViewById(R.id.mainrootrl);

        //初始化界面
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
//            //        //监听是否从其他界面跳转而来
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


    //导航按钮
    private void bthander(int id,View v) {
        switch (id) {
            case R.id.leftbt:
                goBack();
                //command="goBack";
                Log.d("gol","go");
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

        //获取弹窗尺寸
        v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight=v.getMeasuredHeight();

        //按钮所在底部导航栏尺寸
        mainbarlt.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int barHeight=mainbarlt.getMeasuredHeight();

        final PopupWindow window=new PopupWindow(v,getWindowManager().getDefaultDisplay().getWidth(),popupHeight,true);
        //设置背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置能响应外部的点击事件（返回按钮后会消失）
        window.setOutsideTouchable(true);
        //设置能响应点击事件（弹窗能响应事件）
        window.setTouchable(true);
        //①创建动画资源   ②创建一个style应用动画资源（新建anim文件夹下的animation文件）    ③对当前弹窗的动画风格设置为第二部的资源索引（在style中设置）
        window.setAnimationStyle(R.style.translate_anim);
        //参数1(anchor)：锚
        //参数2、3：相对于锚在x、y方向上的偏移量
        window.showAsDropDown(view,-getWindowManager().getDefaultDisplay().getWidth(),-popupHeight-barHeight-50);


        //切换电脑和手机模式
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());

        if(m.get_isWindows()){
            ImageView imageView=v.findViewById(R.id.popup_window_imagine);
            imageView.setImageResource(R.mipmap.mobile_phone);
            TextView textView=v.findViewById(R.id.popup_window_text);
            textView.setText("手机版");

        }

        //切换白天黑夜模式
        if(isDay){

        }else{
            //mainbarlt.setBackground(getDrawable(R.color.night));
            ImageView imageView=v.findViewById(R.id.popup_night_imagine);
            imageView.setImageResource(R.mipmap.ic_sun);
            TextView textView=v.findViewById(R.id.popup_night_text);
            textView.setText("白天模式");
        }

        v.findViewById(R.id.to_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(FragActivity.this, LoginActivity.class);
                startActivity(intent2);
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_bookmark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"您点击了书签",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(FragActivity.this, flagActivity.class);
                startActivity(intent2);
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"您点击了历史",Toast.LENGTH_SHORT).show();
//                mRecyclerView = findViewById(R.id.recycler_view);
                Intent intent = new Intent(FragActivity.this, historyActivity.class);
                startActivity(intent);
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"您点击了下载",Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
                ClipboardManager cbm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cbm.setText(m.geturl());
                Toast.makeText(FragActivity.this,"链接成功复制到剪贴板",Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Toast.makeText(FragActivity.this,"您点击了刷新",Toast.LENGTH_SHORT).show();
                refresh();
                window.dismiss();
            }
        });

        v.findViewById(R.id.popup_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
                m.change_isWindows();

                Toast.makeText(FragActivity.this,!m.get_isWindows()?"切换到手机版":"切换到电脑版",Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });
        v.findViewById(R.id.popup_no_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"您点击了无痕浏览",Toast.LENGTH_SHORT).show();
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
                //刷新
                fragPagerAdapter.notifyDataSetChanged();




                Toast.makeText(FragActivity.this,!isDay?"切换到夜间模式":"切换到白天模式",Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });
        v.findViewById(R.id.popup_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"您点击了设置",Toast.LENGTH_SHORT).show();
                mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
                String url=m.geturl();
                String title=m.gettitle();
                Bitmap icon=m.geticon();
                fragConst.flag_url.add(url);
                fragConst.flag_url=removeDuplicate(fragConst.flag_url);
                fragConst.flag_name.add(title);
                fragConst.flag_name=removeDuplicate(fragConst.flag_name);
                fragConst.flag_icon.add(icon);
                window.dismiss();
            }
        });
        v.findViewById(R.id.popup_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragActivity.this,"成功退出浏览器",Toast.LENGTH_SHORT).show();
                window.dismiss();
                finish();
            }
        });
    }

    public void refresh(){
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
        m.refresh();
    }

    //去重
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
            //注意size在变化
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


    private boolean currentIsFull = true;//当前是不是全屏

    private void zoomchange() {

        int thewidth = mViewPager.getWidth();
        if (dm2.widthPixels - mViewPager.getWidth() < 5) {
            mViewPager.setPageMargin(fragConst.page_interval);
            //   Logger.v("缩小  " + thewidth);
            llayoutviewpage.setGravity(CENTER_IN_PARENT);


            //缩小
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
            //    Logger.v("放大  " + thewidth + " ----  " + dm2.widthPixels);
            mViewPager.setPageMargin(0);

            //放大到原来样子
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
            //  Toast.makeText(this, " 收到 event 数据  " + ((fragEvent) event).getFragTag(), Toast.LENGTH_SHORT).show();
            if (dm2.widthPixels - mViewPager.getWidth() < 5) {
            } else {
                zoomchange();
            }
        }
        if (event instanceof slideEvent) {
            //   Toast.makeText(this, " 收到 event 数据  " + ((slideEvent) event).getType()+"  "+((slideEvent) event).getDirection(), Toast.LENGTH_SHORT).show();
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
                        // Logger.v("删除  page " + page);
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
        fragConst.new_mainfrag_count = 0; //调用次数清0
    }



    public void goUrl(String str){
        mainFrag m=fragConst.fraglist.get(mViewPager.getCurrentItem());
        m.goUrl(str);
    }



}
