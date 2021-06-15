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
//        historyRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));

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

        clear_history=(Button)findViewById(R.id.clear_history);
        clear_history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                while (fragConst.history_url.size()!=0){
                    fragConst.history_url.remove(fragConst.history_url.size()-1);
                    fragConst.history_name.remove(fragConst.history_name.size()-1);
                    //Log.d("clear",""+ fragConst.history_url.size());
                }
            }
        });
    }



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
