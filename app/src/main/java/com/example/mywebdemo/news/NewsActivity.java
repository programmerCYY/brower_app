package com.example.mywebdemo.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.history.historyActivity;
import com.example.mywebdemo.history.historyAdapter;
import com.example.mywebdemo.httputils.HttpUtils;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private newsAdapter newsAdapter;
    private LinearLayoutManager mLayoutManager;


    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        HttpUtils httpUtils=new HttpUtils();
        httpUtils.GetNews();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        newsRecyclerView=findViewById(R.id.recycle_view_news);
        mLayoutManager = new LinearLayoutManager(this);

        newsAdapter=new newsAdapter(this,newsRecyclerView);

        newsRecyclerView.setLayoutManager(mLayoutManager);
        newsRecyclerView.setAdapter(newsAdapter);

        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        newsRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        //返回按钮
        btn=(Button) findViewById(R.id.return_from_news);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(NewsActivity.this, FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
    }
}
