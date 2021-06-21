package com.example.mywebdemo.flag;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private static ArrayList<String> flaglist;
    private static ArrayList<String> titlelist;
    private static ArrayList<Boolean> listCheck;
    private String currenturl;
    private Button btn;

    public static void setxTitle(int position,String title){
        titlelist.set(position,title);
    }

    public static void setxurl(int position,String title){
        flaglist.set(position,title);
    }

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
//        TextView mTextView = (TextView) findViewById(R.id.textview);
//        EditText mEditText = (EditText) findViewById(R.id.edittext);
//        ImageView mImageView = (ImageView) findViewById(R.id.imageview);
//
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //把EditText内容设置为空
//                mEditText.setText("");
//            }
//        });
//
//        mEditText.addTextChangedListener(new TextWatcher() {
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}//文本改变之前执行
//
//            @Override
//            //文本改变的时候执行
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //如果长度为0
//                if (s.length() == 0) {
//                    //隐藏“删除”图片
//                    mImageView.setVisibility(View.GONE);
//                } else {//长度不为0
//                    //显示“删除图片”
//                    mImageView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            public void afterTextChanged(Editable s) { }//文本改变之后执行
//        });
//
//        mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("search", "onClick: 搜索字段");
//            }
//        });


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

    @Override
    protected void onResume() {
        super.onResume();
        mMyAdapter.notifyDataSetChanged();
    }
}
