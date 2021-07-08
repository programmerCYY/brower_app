package com.example.mywebdemo.flag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.httputils.HttpUtils;

import java.util.ArrayList;

public class flagEditActivity extends AppCompatActivity {
    private RecyclerView flagRecyclerView;
    private static flagEditAdapter flagEditAdapter;
    private LinearLayoutManager mLayoutManager;
    //    private ArrayList<String> flaglist;
//    private ArrayList<String> titlelist;
//    private String currenturl;
    private LinearLayout edit_box;
    private LinearLayout all_select_box;
    private LinearLayout select_delete_box;
    private Button btn;
    private Button clear_flag;
    private Button edit_flag;
    private Button all_select_flag;
    private Button select_delete_flag;
    //是否进入编辑状态
//    private static boolean isEdit;

//    public static boolean getisEdit() {
//        return isEdit;
//    }

    //记录checkbox是否选中
    private static ArrayList<Boolean> listCheck;

    public static ArrayList<Boolean> getListCheck() {
        return listCheck;
    }

    public static flagEditAdapter getFlagEditAdapter() {
        return flagEditAdapter;
    }


    //设置checkbox
    public static void setlistcheck(int position,boolean value){
        listCheck.set(position,value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_flag_edit);
        initData();
        flagRecyclerView = findViewById(R.id.recycle_flag_edit);

        //线性布局
        mLayoutManager = new LinearLayoutManager(this);

        flagEditAdapter = new flagEditAdapter(this, flagRecyclerView);
        flagRecyclerView.setLayoutManager(mLayoutManager);
        flagRecyclerView.setAdapter(flagEditAdapter);

        //设置默认动画
        flagRecyclerView.setItemAnimator(new DefaultItemAnimator());

        flagRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));



        //编辑状态下显示的按钮框
//        all_select_box=(LinearLayout)findViewById(R.id.all_select_box_edit);
//        select_delete_box=(LinearLayout)findViewById(R.id.select_delete_box_edit);

        //返回按钮
        btn=(Button)findViewById(R.id.button4_edit);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                isEdit=false;
//                flagAdapter.exitEdit();
                Intent intent = new Intent(flagEditActivity.this, flagActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        //批量删除按钮
        select_delete_flag=(Button)findViewById(R.id.edit_delete_edit);
        //全选按钮
        all_select_flag=(Button)findViewById(R.id.edit_all_select_edit);



        //全选按钮
        all_select_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i <listCheck.size() ; i++) {
                    listCheck.set(i,true);
                }
//                flagAdapter.isAllSelect=true;
                flagEditAdapter.notifyItemRangeChanged(0,flagEditAdapter.getItemCount());
                flagEditAdapter.notifyDataSetChanged();

            }
        });


        //删除清空
        select_delete_flag.setOnClickListener(new View.OnClickListener() {
//            int l=listCheck.size();
            @Override
            public void onClick(View v) {
                for (int i = listCheck.size()-1; i >=0 ; i--) {
                    if(listCheck.get(i)){

                        String temp=fragConst.flag_url.get(i);
                        HttpUtils httpUtils=new HttpUtils();
                        httpUtils.DeleteFlag(temp);

                        fragConst.flag_url.remove(i);
                        fragConst.flag_name.remove(i);
                        if(fragConst.user_account=="") {
                        fragConst.flag_icon.remove(i);
                        }
                        listCheck.remove(i);
                        flagEditAdapter.notifyItemRemoved(i);

                        //flagEditAdapter.notifyItemRangeChanged(0, flagEditAdapter.getItemCount());



                    }
//                    listCheck.set(i,true);
                }
                flagEditAdapter.notifyItemChanged(0,flagEditAdapter.getItemCount());



            }
        });



        //清空按钮
        HttpUtils httpUtils1=new HttpUtils();
        clear_flag =(Button)findViewById(R.id.clear_flag_edit);
        clear_flag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                while (fragConst.flag_url.size()!=0){
                    httpUtils1.DeleteFlag(fragConst.flag_url.get(fragConst.flag_url.size()-1));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    fragConst.flag_url.remove(fragConst.flag_url.size()-1);
                    fragConst.flag_name.remove(fragConst.flag_name.size()-1);
                    if(fragConst.user_account=="") {
                        fragConst.flag_icon.remove(fragConst.flag_icon.size() - 1);
                    }
                }
                flagEditAdapter.notifyDataSetChanged();
            }
        });





    }
    private void initData() {
        int size=fragConst.flag_url.size();
        listCheck = new ArrayList<Boolean>();
        for(int i=0;i<size;i++){
            Boolean tmp = false;
            listCheck.add(tmp);
        }
    }


}
