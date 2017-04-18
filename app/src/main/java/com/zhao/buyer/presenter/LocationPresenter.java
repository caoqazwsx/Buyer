package com.zhao.buyer.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.httpconnection.ServerResponse;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.Utility;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zhao on 2016/5/11.
 */
public class LocationPresenter extends BasePresenter{

    public void getStringLocation(double lng,double lat, final HttpCallbackListener listener){
        String address = APPCONST.GEOCODING_SERVER_ADDRESS +"ak="+ APPCONST.BAIDU_MAP_AK+"&mcode="+ APPCONST.BAIDU_MAP_MCODE+
                "&&output=json&pois=0&location="+lat+","+lng;
        ServerResponse sr = new ServerResponse();
        sr.getStringResponse(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    if(jo.getInt("status") == 0) {
                        String strLocation = jo.getJSONObject("result").getString("sematic_description");
                        listener.onFinish(strLocation);
                    }else{
                       Log.d("Location",jo.getString("message"));
                        listener.onFinish("error");
                    }
                }catch (Exception e){
                   listener.onError(e);
                }
            }
            @Override
            public void onFinish(InputStream in) {

            }

            @Override
            public void onFinish(Bitmap bm) {

            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);

            }
        });
    }

    public void getPlaceSuggestionList(String key,final HttpCallbackListener listener){
        String address = APPCONST.PLACE_SUGGESTION_ADDRESS+"region=131&output=json"+
                "&ak="+ APPCONST.BAIDU_MAP_AK+"&mcode="+ APPCONST.BAIDU_MAP_MCODE+"&query="+ Utility.encode(key);
       getBaiduAPI(address,listener);
    }

    public void getNearbyAddress(double lng,double lat, final HttpCallbackListener listener){
        String address = APPCONST.PLACE_SEARCH_ADDRESS + "query="+Utility.encode("地标")+"&page_size=10&page_num=0&scope=1" +
                "&location="+lat+","+lng+"&radius=500&output=json&ak="+ APPCONST.BAIDU_MAP_AK+"&mcode="+ APPCONST.BAIDU_MAP_MCODE;
        getBaiduAPI(address,listener);

    }

    public Location getCurrentLocation(Context context){
        Location location = null;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = null;
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(context, "No location provider to use",
                    Toast.LENGTH_SHORT).show();
            return null;
        }
        try {
            location = locationManager.getLastKnownLocation(provider);
        }catch (SecurityException e) {
            Log.d("Location",e.toString());
            Toast.makeText(context, "请开启定位权限",
                    Toast.LENGTH_SHORT).show();
        }
        return location;
    }

    public static String reduceStrLocation(String strlocation){
        String res = strlocation;
        if(strlocation.length() > 15){
            char[] tem = res.toCharArray();
            res = String.copyValueOf(tem,0,15);
            res +="...";
        }
        return res;
    }







}
