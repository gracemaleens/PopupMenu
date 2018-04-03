package com.example.com.popupmenu.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.popupmenu.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grace on 2018/3/26.
 */

public class CityAdapter extends BaseAdapter {
    private List<String> mData;
    private Map<Integer, Boolean> mCheckedMap = new HashMap<>();

    public CityAdapter(List<String> data){
        mData = data;
        resetChecked();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        }else{
            holderView = (HolderView)convertView.getTag();
        }
        holderView.nameTv.setText(mData.get(position));
        if(mCheckedMap.get(position) == null){
            mCheckedMap.put(position, false);
        }
        if(mCheckedMap.get(position)){
            holderView.checkIv.setVisibility(View.VISIBLE);
        }else{
            holderView.checkIv.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private void resetChecked(){
        for(int i = 0; i < mData.size(); i++){
            mCheckedMap.put(i, false);
        }
    }

    public void setChecked(int position){
        resetChecked();
        mCheckedMap.put(position, true);
        notifyDataSetChanged();
    }

    private class HolderView{
        TextView nameTv;
        ImageView checkIv;

        public HolderView(View view){
            nameTv = (TextView)view.findViewById(R.id.city_item_tv);
            checkIv = (ImageView)view.findViewById(R.id.city_item_iv);
        }
    }
}
