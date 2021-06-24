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

import java.util.ArrayList;

public class flagActivity extends AppCompatActivity {
    private RecyclerView flagRecyclerView;
    private static flagAdapter flagAdapter;
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
    private static boolean isEdit;

    public static boolean getisEdit() {
        return isEdit;
    }

    //记录checkbox是否选中
//    private static ArrayList<Boolean> listCheck;
//
//    public static ArrayList<Boolean> getListCheck() {
//        return listCheck;
//    }

    public static flagAdapter getFlagAdapter() {
        return flagAdapter;
    }


//    //设置checkbox
//    public static void setlistcheck(int position,boolean value){
//        listCheck.set(position,value);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
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



//        //编辑状态下显示的按钮
//        all_select_box=(LinearLayout)findViewById(R.id.all_select_box);
//        select_delete_box=(LinearLayout)findViewById(R.id.select_delete_box);

        //返回按钮
        btn=(Button)findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                isEdit=false;
//                flagAdapter.exitEdit();
                Intent intent = new Intent(flagActivity.this, FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

//        //批量删除按钮
//        select_delete_flag=(Button)findViewById(R.id.edit_delete);
//        //全选按钮
//        all_select_flag=(Button)findViewById(R.id.edit_all_select);


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

//        //全选按钮
//        all_select_flag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                for (int i = 0; i <listCheck.size() ; i++) {
//                    listCheck.set(i,true);
//                }
//                flagAdapter.isAllSelect=true;
//                flagAdapter.notifyDataSetChanged();
//
//                Log.d("all_select_flag",
//                        ""+listCheck);
//            }
//        });


//        //删除清空
//        select_delete_flag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i <listCheck.size() ; i++) {
//                    if(listCheck.get(i)){
//                        fragConst.flag_url.remove(i);
//                        fragConst.flag_name.remove(i);
//                        fragConst.flag_icon.remove(i);
//                        listCheck.remove(i);
//                        flagAdapter.notifyItemRemoved(i);
//                        //Log.d("isEdit",isEdit+"");
//                        flagAdapter.notifyItemRangeChanged(0,flagAdapter.getItemCount());
//
//
//
//                    }
////                    listCheck.set(i,true);
//                }
////                flagAdapter.notifyItemChanged(0,flagAdapter.getItemCount());
//
//                Log.d("delete_select_flag","success");
//
//            }
//        });



        //清空按钮
        clear_flag =(Button)findViewById(R.id.clear_flag);
        clear_flag.setOnClickListener(new View.OnClickListener(){
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

//        //全选按钮
//        all_select_flag=(Button)findViewById(R.id.edit_all_select);
//        all_select_flag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i <listCheck.size() ; i++) {
//                    listCheck.set(i,true);
//                }
//                flagAdapter.notifyItemChanged(0,flagAdapter.getItemCount());
//
//                Log.d("all_select_flag","success");
//            }
//        });




    }
//    private void initData() {
//        int size=fragConst.flag_url.size();
//        listCheck = new ArrayList<Boolean>();
//        for(int i=0;i<size;i++){
//            Boolean tmp = false;
//            listCheck.add(tmp);
//        }
//    }


}
