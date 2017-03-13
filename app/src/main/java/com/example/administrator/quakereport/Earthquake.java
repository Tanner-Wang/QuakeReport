package com.example.administrator.quakereport;



public class Earthquake {

    //私有属性，只属于本类的属性，都应该创建其公共的get方法，以便其他类访问这些属性
    private double mMag;
    private String mPlace;
    private long mTime;
    private String mUrl;

    //该类的构造方法，英语初始化该类的私有属性
    public Earthquake(double mag, String place, long time, String url){

        mMag = mag;
        mPlace = place;
        mTime = time;
        mUrl = url;

    }

    public double getMag(){
        return mMag;
    }

    public String getPlace(){
        return mPlace;
    }

    public long getTime(){
        return  mTime;
    }

    public String getUrl(){
        return mUrl;
    }

}
