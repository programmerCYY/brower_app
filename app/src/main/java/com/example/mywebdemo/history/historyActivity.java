package com.example.mywebdemo.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mywebdemo.MainActivity;
import com.example.mywebdemo.R;

import java.util.ArrayList;

public class historyActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private historyAdapter mMyAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<String> list;
    private ArrayList<String> namelist;
    private String currenturl;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initData();
        btn=(Button)findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(historyActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                MainActivity.setNameList(namelist);
                MainActivity.setUrlList(list);
                startActivity(intent);
                finish();
            }
        });
        mRecyclerView = findViewById(R.id.recycle_view);
        mMyAdapter = new historyAdapter(list,namelist);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        list=bundle.getStringArrayList("history");
        namelist=bundle.getStringArrayList("title");
        currenturl=bundle.getString("currenturl");
    }

    public void setList(ArrayList mylist){
        list=mylist;
    }

    public void setNamelist(ArrayList mynamelist){
        namelist=mynamelist;
    }

}