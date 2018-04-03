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

public class CateAdapter extends RecyclerView.Adapter<CateAdapter.CateViewHolder> {
    private List<String> mData;
    private OnItemClickListener mListener;

    public CateAdapter(List<String> data){
        mData = data;
    }

    @Override
    public CateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cate_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CateViewHolder holder, int position) {
        holder.contentTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class CateViewHolder extends RecyclerView.ViewHolder{
        TextView contentTv;

        public CateViewHolder(View view){
            super(view);
            contentTv = (TextView)view.findViewById(R.id.cate_item_tv);
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
