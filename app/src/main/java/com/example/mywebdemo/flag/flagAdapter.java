package com.example.mywebdemo.flag;

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
import com.example.mywebdemo.MainActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.history.historyActivity;

import java.util.ArrayList;

public class flagAdapter extends RecyclerView.Adapter<flagAdapter.ViewHolder> {
    private ArrayList<String> url_list;
    private ArrayList<String> title_list;
    private ArrayList<Bitmap> icon_list;
    private Context mContext;
    private RecyclerView mRv;

    public flagAdapter(Context context, RecyclerView recyclerView){
        url_list =fragConst.flag_url;
        title_list=fragConst.flag_name;
        icon_list=fragConst.flag_icon;
        this.mContext = context;
        this.mRv = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flagitem,parent,false);
       final ViewHolder holder=new ViewHolder(view);

       //删除按钮
        holder.btnDelete =(Button) view.findViewById(R.id.flag_delete);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                fragConst.flag_url.remove(position);
                fragConst.flag_name.remove(position);
                fragConst.flag_icon.remove(position);
//                flagActivity activity=(flagActivity) v.getContext();
//                MainActivity.setflagList(flaglist);
//                MainActivity.settitleList(titlelist);
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
                String currenturl=fragConst.flag_url.get(position);
                Intent intent = new Intent(v.getContext(), FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                FragActivity.setUrl(currenturl);

                v.getContext().startActivity(intent);
                flagActivity activity=(flagActivity)v.getContext();
                activity.finish();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mflagnameView.setText(title_list.get(position));
        holder.mflagurlView.setText(url_list.get(position));
        holder.mflagiconView.setImageBitmap(icon_list.get(position));
    }

    @Override
    public int getItemCount() {
        return fragConst.flag_url.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mflagnameView;
        public TextView mflagurlView;
        public ImageView mflagiconView;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mflagnameView= itemView.findViewById(R.id.flag_name);
            mflagurlView=itemView.findViewById(R.id.flag_url);
            mflagiconView=itemView.findViewById(R.id.flag_icon);

        }

    }
}
