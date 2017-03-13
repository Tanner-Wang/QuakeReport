package com.example.administrator.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    //重写getView方法，获得对创建列表视图的控制
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listView = convertView;

        //调用getView方法时，检查是否可以使用回收视图
        if (listView==null){
            //如果不能，就使用list_item XML布局定义的新列表项视图
            listView = LayoutInflater.from(getContext()).inflate
                    (R.layout.list_item, parent, false);
        }

        //然后就可以使用传入的位置参数，从地震列表中获得正确的地震对象的引用
        final Earthquake currentEarthquake = getItem(position);

        //然后就可以将地震对象的数据绑定到列表项布局的视图中去

        //通过视图ID找到每个的视图，为其设置相应的数据
        TextView magTextView = (TextView) listView.findViewById(R.id.mag);
        magTextView.setText(formatMag(currentEarthquake.getMag()));

        // 为震级圆圈设置正确的背景颜色。
        // 从 TextView 获取背景，该背景是一个 GradientDrawable。
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // 根据当前的地震震级获取相应的背景颜色
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMag());

        // 设置震级圆圈的颜色
        magnitudeCircle.setColor(magnitudeColor);

        String mArea = getPlaceMessage(currentEarthquake.getPlace())[0];
        TextView areaTextView = (TextView) listView.findViewById(R.id.area);
        areaTextView.setText(mArea);

        String mLocation = getPlaceMessage(currentEarthquake.getPlace())[1];
        TextView placeTextView = (TextView) listView.findViewById(R.id.location);
        placeTextView.setText(mLocation);

        Date dateObject = new Date(currentEarthquake.getTime());

        TextView dateTextView = (TextView) listView.findViewById(R.id.date);
        dateTextView.setText(formatDate(dateObject));

        TextView timeTextView = (TextView) listView.findViewById(R.id.time);
        timeTextView.setText(formatTime(dateObject));

        //列表项给定位置有了正确的数据之后，就将视图返回给调用方，这里是ListView
        //他将获得所有的列表项并显示到视图列表上
        return listView;
    }

    private String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        return dateFormat.format(date);
    }

    private String formatTime(Date date){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(date);
    }

    private String[] getPlaceMessage(String place){
//        String mPlace = new String(place);
        String[] nPlace = new String[2];
        if(place.contains("of")){
            int a = place.indexOf('f');
            int b = place.length();
            nPlace[0] = place.substring(0,a+1);
            nPlace[1] = place.substring(a+2);

        }
        else{
            nPlace[0] = "Near the";
        nPlace[1] = place;
        }
        return nPlace;
    }

    private String formatMag(double mag){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(mag);
    }

    private int getMagnitudeColor(double mag){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(mag);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }
}
