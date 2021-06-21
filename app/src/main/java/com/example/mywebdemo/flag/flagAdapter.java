package com.example.mywebdemo.flag;

import android.app.assist.AssistStructure;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywebdemo.MainActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.history.historyActivity;
import com.example.mywebdemo.history.historyAdapter;

import java.util.ArrayList;

public class flagAdapter extends RecyclerView.Adapter<flagAdapter.ViewHolder> {
    private ArrayList<String> flaglist;
    private ArrayList<String> titlelist;

    public flagAdapter(ArrayList<String> list,ArrayList<String> title){
        flaglist=list;
        titlelist=title;
    }

    @NonNull
    @Override
    public flagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flagitem,parent,false);
       final ViewHolder holder=new ViewHolder(view);
        holder.btnDelete =(Button) view.findViewById(R.id.flag_delete);
        holder.btnEdit = (Button) view.findViewById(R.id.flag_edit);
        holder.select=(CheckBox) view.findViewById(R.id.flag_choose);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                flaglist.remove(position);
                titlelist.remove(position);
                flagActivity activity=(flagActivity) v.getContext();
                MainActivity.setflagList(flaglist);
                MainActivity.settitleList(titlelist);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String currenturl=flaglist.get(position);
                String currenttitle=titlelist.get(position);
                Intent intent = new Intent(v.getContext(), EditActivity.class);
                Bundle bundle=new Bundle();
                //传递name参数为tinyphp
                bundle.putString("editurl",currenturl);
                bundle.putString("edittitle",currenttitle);
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });

        //编写点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用v.getContext();
                int position = holder.getAdapterPosition();
                String currenturl=flaglist.get(position);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                MainActivity.setflagList(flaglist);
                MainActivity.settitleList(titlelist);
                MainActivity.setUrl(currenturl);
                v.getContext().startActivity(intent);
                flagActivity activity=(flagActivity) v.getContext();
                activity.finish();
            }
        });
        holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = holder.getAdapterPosition();
                if(isChecked){
                    flagActivity.setlistcheck(position,true);
                }else{
                    flagActivity.setlistcheck(position,false);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull flagAdapter.ViewHolder holder, int position) {
        holder.mflagView.setText(titlelist.get(position));
    }

    @Override
    public int getItemCount() {
        return flaglist.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mflagView;
        public Button btnDelete;
        public CheckBox select;
        public Button btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            mflagView= itemView.findViewById(R.id.flag_view);
        }
    }
}
