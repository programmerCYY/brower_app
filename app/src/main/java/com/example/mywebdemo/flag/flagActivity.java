package com.example.mywebdemo.flag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywebdemo.MainActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.history.historyActivity;


import java.util.ArrayList;

public class flagActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private flagAdapter mMyAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<String> flaglist;
    private ArrayList<String> titlelist;
    private static ArrayList<Boolean> listCheck;
    private String currenturl;
    private Button btn;

    public static void setlistcheck(int position,boolean value){
        listCheck.set(position,value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
        initData();
        btn=(Button)findViewById(R.id.button4);
        Button btn_delete = (Button) findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(flagActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                MainActivity.settitleList(titlelist);
                MainActivity.setflagList(flaglist);
                startActivity(intent);
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=listCheck.size()-1;i>=0;i--){
                    if(listCheck.get(i)){
                        flaglist.remove(i);
                        titlelist.remove(i);
                        listCheck.remove(i);
                        mMyAdapter.notifyItemRemoved(i);
                        mMyAdapter.notifyItemRangeChanged(0,flaglist.size());
                    }
                }
            }
        });
        mRecyclerView = findViewById(R.id.recycle_flag);
        mMyAdapter = new flagAdapter(flaglist,titlelist);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        flaglist=bundle.getStringArrayList("flag");
        titlelist=bundle.getStringArrayList("title");
        int size=flaglist.size();
        listCheck = new ArrayList<Boolean>();
        for(int i=0;i<size;i++){
            Boolean tmp = false;
            listCheck.add(tmp);
        }
    }

    public void setList(ArrayList mylist){
        flaglist=mylist;
    }

    public void setNamelist(ArrayList mynamelist){
        titlelist=mynamelist;
    }
}
