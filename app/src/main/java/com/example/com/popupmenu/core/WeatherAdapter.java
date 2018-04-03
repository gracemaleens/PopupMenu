package com.example.com.popupmenu.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.com.popupmenu.R;

import java.util.List;

/**
 * Created by grace on 2018/3/30.
 */

public class WeatherAdapter extends BaseAdapter {
    private List<Weather> mData;

    public WeatherAdapter(List<Weather> data){
        mData = data;
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
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Weather weather = mData.get(position);
        viewHolder.weekTv.setText(weather.getWeek());
        viewHolder.weatherTv.setText(weather.getWeather());
        viewHolder.dcTv.setText(weather.getDc());
        return convertView;
    }

    private class ViewHolder{
        TextView weekTv;
        TextView weatherTv;
        TextView dcTv;

        public ViewHolder(View view){
            weekTv = (TextView)view.findViewById(R.id.weather_item_week_tv);
            weatherTv = (TextView)view.findViewById(R.id.weather_item_weather_tv);
            dcTv = (TextView)view.findViewById(R.id.weather_item_dc_tv);
        }
    }
}
