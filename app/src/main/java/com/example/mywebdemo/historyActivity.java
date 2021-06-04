package com.example.mywebdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class historyActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private historyAdapter mMyAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initData();


        mRecyclerView = findViewById(R.id.recycle_view);
        mMyAdapter = new historyAdapter(list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        list=bundle.getStringArrayList("history");
    }

}