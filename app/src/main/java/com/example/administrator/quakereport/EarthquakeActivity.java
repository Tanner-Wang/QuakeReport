package com.example.administrator.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        //一下这部分是用来代替分割线包括的部分
        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        //--------------------------------------------------------------------------------------------
        // 创建Earthquake类的数组列表
//        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
//        earthquakes.add(new Earthquake("7.2","San Francisco","Feb 2, 2016"));
        //上面一行代码展开是这样子的：
        //先实例化一个Earthquake对象，使用的是其构造函数来初始化其属性
        //Earthquake e = new Earthquake("7.2","San Francisco","Feb 2, 2016");
        //然后数组列表对象调用add()方法，传入Earthquake对象为参数，这样就把一个地震事件的信息传入到数组列表中
        //earthquakes.add(e);
//        earthquakes.add(new Earthquake("7.2","San Francisco","Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2","San Francisco","Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2","San Francisco","Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2","San Francisco","Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2","San Francisco","Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2","San Francisco","Feb 2, 2016"));

        //-------------------------------------------------------------------------------------------

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EarthquakeAdapter adapter = new EarthquakeAdapter(
                this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Earthquake currentEarthquake = adapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });
    }
}
