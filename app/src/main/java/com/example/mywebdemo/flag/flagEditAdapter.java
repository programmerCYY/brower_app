package com.example.mywebdemo.flag;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.httputils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class flagEditAdapter extends RecyclerView.Adapter<flagEditAdapter.ViewHolder> {
    private ArrayList<String> url_list;
    private ArrayList<String> title_list;
    private ArrayList<Bitmap> icon_list;
    private ArrayList<String> icon_list_string;
    private Context mContext;
    private RecyclerView mRv;
    public boolean isAllSelect=false;


    public flagEditAdapter(Context context, RecyclerView recyclerView){
        url_list = fragConst.flag_url;
        title_list=fragConst.flag_name;
        icon_list=fragConst.flag_icon;
        icon_list_string=fragConst.flag_icon_string;

        this.mContext = context;
        this.mRv = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flag_edit_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        //准许在主线程中调用网络线程
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //编辑状态的时候再监听
//        if(flagActivity.getisEdit()) {

//            if(isAllSelect){
//                holder.boxselect.setChecked(true);
//            }

//            for (int i = 0; i < flagActivity.getListCheck().size(); i++) {
//                if(flagActivity.getListCheck().get(i)==true){
//                    holder.boxselect.setChecked(true);
//                }
//            }
            //监听是否选中
            holder.boxselect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = holder.getAdapterPosition();
                    if (isChecked) {
                        flagEditActivity.setlistcheck(position, true);
                    } else {
                        flagEditActivity.setlistcheck(position, false);
                    }
                }
            });

            //删除按钮
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

                    String temp=fragConst.flag_url.get(position);
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.DeleteFlag(temp);
                    fragConst.flag_url.remove(position);
                    fragConst.flag_name.remove(position);
                    if(fragConst.user_account=="") {
                        fragConst.flag_icon.remove(position);
                    }
//                flagActivity activity=(flagActivity) v.getContext();
//                MainActivity.setflagList(flaglist);
//                MainActivity.settitleList(titlelist);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });

            //跳转编辑事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), EditActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("editPosition",position);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);


                }
            });




//        }else {
//            holder.boxselect.setVisibility(View.GONE);
//            holder.btnDelete.setVisibility(View.GONE);

            //编写点击事件
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //用v.getContext();
//                    int position = holder.getAdapterPosition();
//                    String currenturl=fragConst.flag_url.get(position);
//                    Intent intent = new Intent(v.getContext(), FragActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    FragActivity.setUrl(currenturl);
//
//                    v.getContext().startActivity(intent);
//                    flagActivity activity=(flagActivity)v.getContext();
//                    activity.finish();
//                }
//            });
//        }



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mflagnameView.setText(title_list.get(position));
        holder.mflagurlView.setText(url_list.get(position));
        if(fragConst.user_account=="") {
            holder.mflagiconView.setImageBitmap(icon_list.get(position));
        }else {
            holder.mflagiconView.setImageBitmap(returnBitMap(icon_list_string.get(position)));
            //holder.mflagiconView.setImageResource(R.mipmap.bookmark_color);
        }
//        if(isAllSelect){
//            holder.boxselect.setChecked(flagActivity.getListCheck().get(position));
//        }
//        if (flagActivity.getListCheck().get(position) == true) {
            Log.e("refresh","success");
            Log.e("refresh2",""+flagEditActivity.getListCheck().get(position));
            holder.boxselect.setChecked(flagEditActivity.getListCheck().get(position));
//        }
    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }



    @Override
    public int getItemCount() {
        return fragConst.flag_url.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mflagnameView;
        public TextView mflagurlView;
        public ImageView mflagiconView;
        public CheckBox boxselect;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mflagnameView= itemView.findViewById(R.id.flag_name_edit);
            mflagurlView=itemView.findViewById(R.id.flag_url_edit);
            mflagiconView=itemView.findViewById(R.id.flag_icon_edit);
            boxselect=itemView.findViewById(R.id.flag_choose_edit);
            btnDelete =itemView.findViewById(R.id.flag_delete_edit);

        }

    }
}
