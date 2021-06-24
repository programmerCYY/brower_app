package com.example.mywebdemo.history;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.ArrayList;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {

    private ArrayList<String> mList;
    private ArrayList<String> mtitle;
    private ArrayList<Bitmap> micon;
    private Context mContext;
    private RecyclerView mRv;

    public historyAdapter(Context context, RecyclerView recyclerView) {
        mList =fragConst.history_url;
        mtitle=fragConst.history_name;
        micon=fragConst.history_icon;
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


        //删除按钮
        holder.btnDelete =(Button) view.findViewById(R.id.btn_delete);
         holder.btnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int position = holder.getAdapterPosition();
                 fragConst.history_url.remove(position);
                 fragConst.history_name.remove(position);
                 fragConst.history_icon.remove(position);
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
        holder.mtitleView.setText(mtitle.get(position));
        holder.murlView.setText(mList.get(position));
        holder.miconView.setImageBitmap(micon.get(position));
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
