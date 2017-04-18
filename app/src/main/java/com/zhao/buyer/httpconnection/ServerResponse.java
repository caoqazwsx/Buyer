package com.zhao.buyer.httpconnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhao on 2016/4/16.
 */
public class ServerResponse {
    private String response = null;
    private Bitmap bm = null;




    public void getStringResponse(String address, final HttpCallbackListener listener){

        Log.d("Http", "success1");

       HttpUtil.sendGetRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(Bitmap bm){}
            @Override
            public void onFinish(InputStream in){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        response.append(line);
                        line = reader.readLine();
                    }
                    if(listener != null) {
                        Log.d("Http", "read finish："+response);
                        setResponse(response.toString());
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    listener.onError(e);
                }
            }
            @Override
            public void onFinish(String response) {

            }
            @Override
            public void onError(Exception e) {
                if (listener != null) {
                    listener.onError(e);
                }
            }
        });
    }

    public void getBitmapResponse(String address, final HttpCallbackListener listener){
        Log.d("Http", "success1");
        HttpUtil.sendGetRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(Bitmap bm){}
            @Override
            public void onFinish(InputStream in){
                if(listener!=null) {
                    Bitmap bm = BitmapFactory.decodeStream(in);
                    setBitmap(bm);
                    listener.onFinish(bm);
                }
            }
            @Override
            public void onFinish(String response) {

            }
            @Override
            public void onError(Exception e) {
                if (listener != null) {
                    listener.onError(e);
                }
            }
        });
    }

    private void setResponse(String response)
    {
        this.response = response;
    }
    private void setBitmap(Bitmap bm)
    {
        this.bm = bm;
    }
    private String getResponse(String response)
    {
        return response;
    }
    private Bitmap getBitmap(Bitmap bm)
    {
        return bm;
    }
}
