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

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.httputils.HttpUtils;

import java.util.ArrayList;

public class flagActivity extends AppCompatActivity {
    private RecyclerView flagRecyclerView;
    private static flagAdapter flagAdapter;
    private LinearLayoutManager mLayoutManager;
//    private ArrayList<String> flaglist;
//    private ArrayList<String> titlelist;
//    private String currenturl;

    private Button btn;
    private Button clear_flag;
    private Button edit_flag;





    public static flagAdapter getFlagAdapter() {
        return flagAdapter;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);




        if(fragConst.user_account!="") {
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.GetFlag();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


//        initData();
        flagRecyclerView = findViewById(R.id.recycle_flag);

        //线性布局
        mLayoutManager = new LinearLayoutManager(this);

        flagAdapter = new flagAdapter(this, flagRecyclerView);
        flagRecyclerView.setLayoutManager(mLayoutManager);
        flagRecyclerView.setAdapter(flagAdapter);

        //设置默认动画
        flagRecyclerView.setItemAnimator(new DefaultItemAnimator());

        flagRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));




        //返回按钮
        btn=(Button)findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(flagActivity.this, FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });



        //编辑按钮
        edit_flag=(Button)findViewById(R.id.edit_flag);
//        edit_box=(LinearLayout)findViewById(R.id.edit_box);
        edit_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                flagAdapter.setEdit(isEdit);
//                isEdit=true;
//                flagAdapter.notifyItemRangeChanged(0,flagAdapter.getItemCount());

                //出现全选和删除按钮
//                all_select_box.setVisibility(View.VISIBLE);
//                select_delete_box.setVisibility(View.VISIBLE);
//                edit_box.setVisibility(View.GONE);

                Intent intent = new Intent(flagActivity.this, flagEditActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();



            }
        });



        //清空按钮
        HttpUtils httpUtils1=new HttpUtils();
        clear_flag =(Button)findViewById(R.id.clear_flag);
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


                    //Log.d("clear",""+ fragConst.history_url.size());
                }
                flagAdapter.notifyDataSetChanged();
            }
        });




    }



}
