package com.example.mywebdemo.history;

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
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.R;
import com.example.mywebdemo.httputils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {

    private ArrayList<String> mList;
    private ArrayList<String> mtitle;
    private ArrayList<Bitmap> micon;
    private ArrayList<String> micon_string;
    private Context mContext;
    private RecyclerView mRv;

    public historyAdapter(Context context, RecyclerView recyclerView) {
        mList =fragConst.history_url;
        mtitle=fragConst.history_name;
        micon=fragConst.history_icon;
        micon_string=fragConst.history_icon_string;
        this.mContext = context;
        this.mRv = recyclerView;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //挂载item视图
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //删除按钮
        holder.btnDelete =(Button) view.findViewById(R.id.btn_delete);
         holder.btnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int position = holder.getAdapterPosition();

//                 if (fragConst.user_account!="") {
//                     HttpUtils httpUtils = new HttpUtils();
//                     httpUtils.(current_url, current_title, fragConst.bitmapToString(current_icon));
//                     try {
//                         Thread.sleep(1000);
//                     } catch (InterruptedException e) {
//                         e.printStackTrace();
//                     }
//                 }    }
                 //Log.d("position",position+"");
                 String temp=fragConst.history_url.get(position);
                 //Log.d("history_url.get(position)",temp);
                 HttpUtils httpUtils = new HttpUtils();
                 httpUtils.DeleteHistory(temp);
                 fragConst.history_url.remove(position);
                 fragConst.history_name.remove(position);
                 if(fragConst.user_account=="") {
                     fragConst.history_icon.remove(position);
                 }else {
                     fragConst.history_icon_string.remove(position);
                 }


//                 historyActivity activity=(historyActivity) v.getContext();
//                 activity.setList(mList);
//                 activity.setNamelist(mtitle);
                 notifyItemRemoved(position);
                 notifyDataSetChanged();
             }
         });
        //编写点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用v.getContext();
                int position = holder.getAdapterPosition();
                String currenturl=fragConst.history_url.get(position);
                Intent intent = new Intent(v.getContext(), FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                FragActivity.setUrl(currenturl);
                //intent.putExtra("toUrl",currenturl);
                //Log.d("toUrl",currenturl);

//                MainActivity.setUrlList(mList);
//                MainActivity.setNameList(mtitle);
//                MainActivity.setUrl(currenturl);
                v.getContext().startActivity(intent);
                historyActivity activity=(historyActivity)v.getContext();
                activity.finish();
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("size",mList.size()+" "+fragConst.history_url.size());
        Log.d("position",""+position);
        holder.mtitleView.setText(mtitle.get(position));
        holder.murlView.setText(mList.get(position));
        if(fragConst.user_account!=""){
            //holder.miconView.setImageResource(R.mipmap.earth);
            holder.miconView.setImageBitmap(returnBitMap(micon_string.get(position)));
        }else {
            holder.miconView.setImageBitmap(micon.get(position));
        }
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
        return fragConst.history_url.size() ;
    }


    //创建ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mtitleView;
        public TextView murlView;
        public ImageView miconView;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mtitleView= itemView.findViewById(R.id.history_name);
            murlView=itemView.findViewById(R.id.history_url);
            miconView=itemView.findViewById(R.id.history_icon);
        }
    }

}
