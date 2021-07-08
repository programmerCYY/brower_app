package com.example.mywebdemo.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.history.historyActivity;
import com.example.mywebdemo.history.historyAdapter;
import com.example.mywebdemo.httputils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder> {
    private ArrayList<NewsItem> mList;
    private Context mContext;
    private RecyclerView mRv;

    public newsAdapter(Context context, RecyclerView recyclerView) {
        mList = fragConst.news_list;
        this.mContext = context;
        this.mRv = recyclerView;
    }
    @NonNull
    @Override
    public newsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //挂载item视图
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item,parent,false);
        final newsAdapter.ViewHolder holder = new newsAdapter.ViewHolder(view);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //编写点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用v.getContext();
                int position = holder.getAdapterPosition();
                String currenturl=mList.get(position).url;
                Intent intent = new Intent(v.getContext(), FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                FragActivity.setUrl(currenturl);

                v.getContext().startActivity(intent);
                NewsActivity activity=(NewsActivity)v.getContext();
                activity.finish();
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull newsAdapter.ViewHolder holder, final int position) {
//        Log.d("size",mList.size()+" "+fragConst.history_url.size());
//        Log.d("position",""+position);
        holder.mtitleView.setText(mList.get(position).title);
        holder.murlView.setText(mList.get(position).source+"  "+mList.get(position).date.substring(0,10));
        holder.miconView.setImageBitmap(returnBitMap(mList.get(position).imgUrl));
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
        return fragConst.news_list.size() ;
    }


    //创建ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mtitleView;
        public TextView murlView;
        public ImageView miconView;
//        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mtitleView= itemView.findViewById(R.id.news_name);
            murlView=itemView.findViewById(R.id.news_message);
            miconView=itemView.findViewById(R.id.news_icon);
        }
    }

}
