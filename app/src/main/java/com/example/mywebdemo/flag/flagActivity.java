package com.example.mywebdemo.flag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.MainActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.history.historyAdapter;

import java.util.ArrayList;

public class flagActivity extends AppCompatActivity {
    private RecyclerView flagRecyclerView;
    private flagAdapter flagAdapter;
    private LinearLayoutManager mLayoutManager;
//    private ArrayList<String> flaglist;
//    private ArrayList<String> titlelist;
//    private String currenturl;
    private Button btn;
    private Button clear_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
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

        btn=(Button)findViewById(R.id.button4);

        //返回按钮
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(flagActivity.this, FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        clear_history=(Button)findViewById(R.id.clear_flag);
        clear_history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                while (fragConst.flag_url.size()!=0){
                    fragConst.flag_url.remove(fragConst.flag_url.size()-1);
                    fragConst.flag_name.remove(fragConst.flag_name.size()-1);
                    //Log.d("clear",""+ fragConst.history_url.size());
                }
                flagAdapter.notifyDataSetChanged();
            }
        });



    }


}
