package com.example.com.popupmenu.core;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.com.popupmenu.R;
import com.example.com.popupmenu.baseView.PopupMenu;
import com.example.com.popupmenu.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int BACKGROUND = 0xffffffff;
    private PopupMenu mPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPopupMenu = (PopupMenu) findViewById(R.id.popup_menu);

        String[] titles = {"城市", "天气", "美食", "游玩"};
        List<View> Views = new ArrayList<>();

        //city
        String[] citys = {"无", "北京", "上海", "广州", "深圳", "天津", "杭州", "重庆", "南京", "金华"};
        final ListView cityListView = new ListView(this);
        cityListView.setAdapter(new CityAdapter(Arrays.asList(citys)));
        cityListView.setBackgroundColor(BACKGROUND);
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showView(1);
                ((CityAdapter) cityListView.getAdapter()).setChecked(position);
                mPopupMenu.closeMenu();
            }
        });
        ((CityAdapter) cityListView.getAdapter()).setChecked(0);
        Views.add(cityListView);

        //weather
        JSONArray weatherJA = new JSONArray();
        JSONObject weatherJO = new JSONObject();
        try {
            weatherJO.put("week", "周一");
            weatherJO.put("weather", "多云");
            weatherJO.put("dc", "20-26");
            weatherJA.put(weatherJO);
            weatherJO = new JSONObject();
            weatherJO.put("week", "周二");
            weatherJO.put("weather", "晴");
            weatherJO.put("dc", "22-28");
            weatherJA.put(weatherJO);
            weatherJO = new JSONObject();
            weatherJO.put("week", "周三");
            weatherJO.put("weather", "晴");
            weatherJO.put("dc", "23-28");
            weatherJA.put(weatherJO);
            weatherJO = new JSONObject();
            weatherJO.put("week", "周四");
            weatherJO.put("weather", "多云");
            weatherJO.put("dc", "20-26");
            weatherJA.put(weatherJO);
            weatherJO = new JSONObject();
            weatherJO.put("week", "周五");
            weatherJO.put("weather", "小雨");
            weatherJO.put("dc", "18-25");
            weatherJA.put(weatherJO);
            weatherJO = new JSONObject();
            weatherJO.put("week", "周六");
            weatherJO.put("weather", "雷阵雨");
            weatherJO.put("dc", "17-23");
            weatherJA.put(weatherJO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Weather> weathers = new Gson().fromJson(weatherJA.toString(),
                new TypeToken<ArrayList<Weather>>() {
                }.getType());
        ListView weatherListView = new ListView(this);
        weatherListView.setAdapter(new WeatherAdapter(weathers));
        weatherListView.setBackgroundColor(BACKGROUND);
        weatherListView.setSelector(ContextCompat.getDrawable(this, R.drawable.selector_list_item));
        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showView(2);
                mPopupMenu.closeMenu();
            }
        });
        Views.add(weatherListView);

        //cate
        RecyclerView cateRecyclerView = new RecyclerView(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        cateRecyclerView.setLayoutManager(layoutManager);
        String[] cates = {"附近小吃", "人气美食", "评价最高", "重庆风味", "东北风味", "西式餐厅", "法式餐厅", "低调奢华", "情侣餐厅"};
        CateAdapter cateAdapter = new CateAdapter(Arrays.asList(cates));
        cateAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showView(3);
                mPopupMenu.closeMenu();
            }
        });
        cateRecyclerView.setAdapter(cateAdapter);
        cateRecyclerView.setBackgroundColor(BACKGROUND);
        Views.add(cateRecyclerView);

        //play
        RecyclerView playRecyclerView = new RecyclerView(this);
        RecyclerView.LayoutManager playLayoutManager = new GridLayoutManager(this, 4);
        playRecyclerView.setLayoutManager(playLayoutManager);
        String[] plays = {"景点/玩乐", "周边游", "赏花踏青", "当地攻略"};
        PlayAdapter playAdapter = new PlayAdapter(Arrays.asList(plays));
        playAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showView(4);
                mPopupMenu.closeMenu();
            }
        });
        playRecyclerView.setAdapter(playAdapter);
        playRecyclerView.setBackgroundColor(BACKGROUND);
        Views.add(playRecyclerView);

        mPopupMenu.setPopupView(Arrays.asList(titles), Views.toArray(new View[]{}));
    }

    private void showView(int index) {
        ImageView imageView = new ImageView(this);
        int resId = getResources().getIdentifier("content_view_" + index, "drawable", getPackageName());
        imageView.setImageDrawable(ContextCompat.getDrawable(this, resId));
        imageView.setId(index);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mPopupMenu.setContentView(imageView);
    }
}
