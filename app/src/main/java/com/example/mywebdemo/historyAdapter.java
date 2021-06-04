package com.example.mywebdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {
    private List<String> mList;

    public historyAdapter(List<String> list) {
        mList = list;
    }
    @NonNull
    @Override
    public historyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        historyAdapter.ViewHolder holder = new historyAdapter.ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item, parent,
                false));
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull historyAdapter.ViewHolder holder, int position) {
        holder.mView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.text_view);
        }
    }
}
