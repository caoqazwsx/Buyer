package com.zhao.buyer.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.httpconnection.ServerResponse;
import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.globalvariable.Utility;

import org.json.JSONObject;

import java.io.InputStream;


/**
 * Created by zhao on 2016/4/26.
 */
public class BasePresenter {

    public void getPicture(String path, final HttpCallbackListener listener){

        String address = Globalvariable.SERVER_ADDRESS+"type=picture&path="+ Utility.encode(path);
        ServerResponse sr = new ServerResponse();
        sr.getBitmapResponse(address, new HttpCallbackListener() {
            @Override
            public void onFinish(Bitmap bm){
                if(listener != null){
                    listener.onFinish(bm);
                }
            }
            @Override
            public void onFinish(String response) {
            }

            @Override
            public void onFinish(InputStream in) {
            }

            @Override
            public void onError(Exception e) {
                if(listener != null){
                    listener.onError(e);
                }
            }
        });
    }

    public void getList( String address,final HttpCallbackListener listener) {

        ServerResponse sr = new ServerResponse();
        sr.getStringResponse(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    //Log.d("FormInfo1", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String tem = jsonObject.getString("Data");
                    if (listener != null) {
                        listener.onFinish(tem);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
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
                if (listener != null) {
                    listener.onError(e);
                }

            }
        });

    }

    public void update(String address, final HttpCallbackListener listener){
        ServerResponse sr = new ServerResponse();
        sr.getStringResponse(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String tem = jsonObject.getString("Data");
                    Log.d("update", "" + tem);
                    // JSONArray res = new JSONArray(tem);
                    if (tem.equals("success")) {
                        if (listener != null) {
                            listener.onFinish(tem);
                        }
                    }else{
                        if (listener != null) {
                            listener.onFinish("error");
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
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
                if (listener != null) {
                    listener.onError(e);
                }

            }
        });
    }




}
