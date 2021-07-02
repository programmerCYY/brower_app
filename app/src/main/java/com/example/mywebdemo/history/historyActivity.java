package com.example.mywebdemo.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.MainActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.httputils.HttpUtils;

import java.util.ArrayList;

public class historyActivity extends AppCompatActivity {
    private RecyclerView historyRecyclerView;
    private historyAdapter historyAdapter;
    private LinearLayoutManager mLayoutManager;
//    private ArrayList<String> list;
//    private ArrayList<String> namelist;
//    private String currenturl;
    private Button btn;
    private Button clear_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if(fragConst.user_account!=""){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.GetHistory();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
         e.printStackTrace();
        }
        }

        //                httpUtils.GetHistory();
//                    try {
//                        startLoadingProgress();
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    if(fragConst.http_msg!="succ"){
//                        Toast.makeText(LoginActivity.this,"登录失败，请重试",Toast.LENGTH_SHORT).show();
//                    }else{
//
//                        Toast.makeText(LoginActivity.this,"登录成功，获取记录成功",Toast.LENGTH_SHORT).show();
//                        fragConst.http_msg="";
//                        Intent intent = new Intent(LoginActivity.this, FragActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        startActivity(intent);
//
//                    }
        historyRecyclerView = findViewById(R.id.recycle_view);


        //线性布局
        mLayoutManager = new LinearLayoutManager(this);

//        initData();



        historyAdapter = new historyAdapter(this, historyRecyclerView);
        historyRecyclerView.setLayoutManager(mLayoutManager);
        historyRecyclerView.setAdapter(historyAdapter);

        //设置默认动画
        historyRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //?
        historyRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        //返回按钮
        btn=(Button) findViewById(R.id.return_from_history);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(historyActivity.this, FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                MainActivity.setNameList(namelist);
//                MainActivity.setUrlList(list);
                startActivity(intent);
                finish();
            }
        });

        //清空按钮
        HttpUtils httpUtils1=new HttpUtils();
        clear_history=(Button)findViewById(R.id.clear_history);
        clear_history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                while (fragConst.history_url.size()!=0){
                    httpUtils1.DeleteHistory(fragConst.history_url.get(fragConst.history_url.size()-1));

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fragConst.history_url.remove(fragConst.history_url.size()-1);
                    fragConst.history_name.remove(fragConst.history_name.size()-1);
                    if(fragConst.user_account==""){
                        fragConst.history_icon.remove(fragConst.history_icon.size()-1);
                    }
                    //Log.d("clear",""+ fragConst.history_url.size());
                }
                historyAdapter.notifyDataSetChanged();

            }

        });
    }

    //public void refresh() { onCreate(null); }

//    protected void onResume() {
//        super.onResume();
//        onCreate(null);
//    }


//    private void initData() {
//        Bundle bundle = this.getIntent().getExtras();
//        list=bundle.getStringArrayList("history");
//        namelist=bundle.getStringArrayList("title");
//        currenturl=bundle.getString("currenturl");
//    }

//    public void setList(ArrayList mylist){
//        list=mylist;
//    }
//
//    public void setNamelist(ArrayList mynamelist){
//        namelist=mynamelist;
//    }


}
