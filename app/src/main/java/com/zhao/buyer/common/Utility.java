package com.zhao.buyer.common;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhao on 2016/5/5.
 */
public class Utility {

    public static void  setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static String encode(String data){
        String res = null;
        try {
            res = URLEncoder.encode(data, "UTF-8");

        }catch (Exception e){
            Log.d("Http", e.toString());
        }
        return res;
    }

    public static String getStringNowTime(){
        SimpleDateFormat formatter  =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate  =  new Date(System.currentTimeMillis());//获取当前时间
        String  time  =  formatter.format(curDate);
        return time;
    }

    @NonNull
    public static String hideAccount(String account){
        int i = account.length();
        char[] str =  account.toCharArray();
        for(int j=1;j < i-1;j++){
            str[j] = '*';
        }
        return String.copyValueOf(str);
    }

    public static  int getP2PDistance(double lng1,double lat1,double lng2,double lat2){
        double p2pDis,lai_dis,lng_dis;

        lai_dis = Math.abs(lat1-lat2);
        lng_dis = Math.abs(lng1-lng2);

        if(lng_dis > 180) lng_dis = 360-lng_dis;

        p2pDis = Math.sqrt(Math.pow(lai_dis/0.00001, 2)+Math.pow(lng_dis/0.00001, 2));

        //Log.d("Location","p2pDis:"+(int)p2pDis+"    lai_dis="+lai_dis/0.00001+" lng_dis="+lng_dis/0.00001);

        return (int)p2pDis;


    }


}
