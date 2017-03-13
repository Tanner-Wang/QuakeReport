package com.example.administrator.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.administrator.quakereport.QueryUtils.extractEarthquakes;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static final String SIMPLE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(SIMPLE_URL);


    }

 private class MyAsyncTask extends AsyncTask<String, Void, String> {
     @Override
     protected String doInBackground(String... urls) {
         return extractEarthquakes(urls[0]);
     }

     @Override
     protected void onPostExecute(String eJsonResponse) {
         updateUi(eJsonResponse);
     }
 }

    private void updateUi(String jsonResponse) {

        ArrayList<Earthquake> earthquakes = new ArrayList<>();

         try {

            JSONObject root = new JSONObject(jsonResponse);

            JSONArray featuresArray = root.getJSONArray("features");

            for (int i = 0; i<featuresArray.length();i++) {

                JSONObject earthquake = featuresArray.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");

                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                earthquakes.add(new Earthquake(mag,place,time,url));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //一下这部分是用来代替分割线包括的部分
//        ArrayList<Earthquake> earthquakes = extractEarthquakes();

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

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long l) {
                Earthquake currentEarthquake = adapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });
    }
}
