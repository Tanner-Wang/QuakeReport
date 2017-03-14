package com.example.administrator.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    /** 日志消息标签 */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** 查询 URL */
    private String mUrl;

    /**
     * 构建新 {@link EarthquakeLoader}。
     *
     * 活动的 @param 上下文
     * 要从中加载数据的 @param url
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl==null) {
            return null;
        }
        return QueryUtils.fetchEarthquakeData(mUrl);
    }
}
