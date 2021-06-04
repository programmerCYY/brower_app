package com.example.mywebdemo;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentActivity;

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
import com.example.mywebdemo.fragment.fragAdapter;
import com.example.mywebdemo.fragment.mainFrag;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.widget.RelativeLayout.*;

public class FragActivity extends FragmentActivity {

    private verticalViewPager mViewPager;
    private fragAdapter fragPagerAdapter;
    private TextView pagebt;
    private ImageView leftbt, rightbt, setbt, homebt, delfrag, addnewpage, returnmain;
    private LinearLayout llayoutviewpage, pagebarlt, mainbarlt;
    private DisplayMetrics dm2;
    private GestureDetectorCompat mDetector;
    private PercentRelativeLayout mainrootrl;
    private mainActivitySimpleOnGestureListener mainSimpleOnGestureListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        for (int i = 0; i < fragConst.init_page_count; i++) {
            mainFrag tmp=new mainFrag();
            fragConst.fraglist.add(tmp);
            fragConst.fraghashcode.add(String.valueOf(tmp.hashCode()));
        }
        mViewPager = (verticalViewPager) findViewById(R.id.mainviewpage);
        fragPagerAdapter = new fragAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(fragPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        EventBus.getDefault().register(this);
        mainSimpleOnGestureListener = new mainActivitySimpleOnGestureListener();
        mDetector = new GestureDetectorCompat(this, mainSimpleOnGestureListener);
        mainrootrl = (PercentRelativeLayout) findViewById(R.id.mainrootrl);


        viewInit();

    }

    private void viewInit() {

        leftbt = (ImageView) findViewById(R.id.leftbt);
        rightbt = (ImageView) findViewById(R.id.rightbt);
        setbt = (ImageView) findViewById(R.id.setbt);
        pagebt = (TextView) findViewById(R.id.pagebt);
        homebt = (ImageView) findViewById(R.id.homebt);
        delfrag = (ImageView) findViewById(R.id.delfrag);

        addnewpage = (ImageView) findViewById(R.id.addnewpage);
        returnmain = (ImageView) findViewById(R.id.returnmain);

        leftbt.setOnClickListener((View v) -> {
            bthander(v.getId());
        });
        rightbt.setOnClickListener((View v) -> {
            bthander(v.getId());
        });
        setbt.setOnClickListener((View v) -> {
            bthander(v.getId());
        });
        pagebt.setOnClickListener((View v) -> {
            bthander(v.getId());
        });
        homebt.setOnClickListener((View v) -> {
            bthander(v.getId());
        });

        addnewpage.setOnClickListener((View v) -> {
            bthander(v.getId());
        });
        returnmain.setOnClickListener((View v) -> {
            bthander(v.getId());
        });


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


    private void bthander(int id) {
        switch (id) {
            case R.id.leftbt:
                break;
            case R.id.rightbt:
                break;
            case R.id.setbt:

                break;
            case R.id.pagebt:
                zoomchange();
                break;
            case R.id.homebt:
                //   removePage(mViewPager.getCurrentItem());
                break;
            /**********************************************************/
            case R.id.addnewpage:
                addNewPage();
                break;
            case R.id.returnmain:
                zoomchange();
                break;

        }


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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        fragConst.fraglist.clear();
        fragConst.new_mainfrag_count = 0; //调用次数清0
    }


}
