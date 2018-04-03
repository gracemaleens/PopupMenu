package com.example.com.popupmenu.core;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.com.popupmenu.R;
import com.example.com.popupmenu.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by grace on 2018/4/2.
 */

public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.PlayViewHolder> {
    private List<String> mData;
    private OnItemClickListener mListener;

    public PlayAdapter(List<String> data){
        mData = data;
    }

    @Override
    public PlayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.play_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PlayViewHolder holder, int position) {
        holder.contentTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class PlayViewHolder extends RecyclerView.ViewHolder{
        TextView contentTv;

        public PlayViewHolder(View view){
            super(view);
            contentTv = (TextView)view.findViewById(R.id.play_item_content_tv);
            contentTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }
    }
}
