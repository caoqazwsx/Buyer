package com.zhao.buyer.source;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.URLCONST;
import com.zhao.buyer.callback.HttpCallback;
import com.zhao.buyer.callback.JsonCallback;

import com.zhao.buyer.model.JsonModel;
import com.zhao.buyer.util.HttpUtil;
import com.zhao.buyer.util.RSAUtilV2;
import com.zhao.buyer.util.StringHelper;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by zhao on 2016/4/16.
 */

public class HttpDataSource {

    public static void  httpGet(String url, final JsonCallback callback){
       Log.d("HttpGet URl", url);
       HttpUtil.sendGetRequest(url, new HttpCallback() {
            @Override
            public void onFinish(Bitmap bm){

            }
            @Override
            public void onFinish(InputStream in){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        response.append(line);
                        line = reader.readLine();
                    }
                    if(callback != null) {
                        Log.i("Http", "read finish："+response.toString());
                       // setResponse(response.toString());
                        JsonModel jsonModel = new Gson().fromJson(response.toString(), JsonModel.class);
//                        jsonModel.setResult(jsonModel.getResult().replace("\n",""));
//                        test(jsonModel.getResult());
//                        String str = new String(RSAUtilV2.decryptByPrivateKey(Base64.decode(jsonModel.getResult().replace("\n",""),Base64.DEFAULT),APPCONST.privateKey));
                        if(URLCONST.isRSA && !StringHelper.isEmpty(jsonModel.getResult())) {
                            jsonModel.setResult(StringHelper.decode(new String(RSAUtilV2.decryptByPrivateKey(Base64.decode(jsonModel.getResult().replace("\n", ""), Base64.DEFAULT), APPCONST.privateKey))));
                        }
                        callback.onFinish(jsonModel);
                        Log.d("Http", "RSA finish："+ new Gson().toJson(jsonModel));
                    }
                }catch (Exception e){
                    callback.onError(e);
                }
            }
            @Override
            public void onFinish(String response) {

            }

            @Override
            public void onError(Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }

        });
    }

    public static void httpPost(String url, String output, final JsonCallback callback){
        Log.d("HttpPost:", url + "&" + output);
        HttpUtil.sendPostRequest(url,output,new HttpCallback() {
            @Override
            public void onFinish(Bitmap bm){

            }
            @Override
            public void onFinish(InputStream in){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        response.append(line);
                        line = reader.readLine();
                    }
                    if(callback != null) {
                        Log.d("Http", "read finish："+response);
                        // setResponse(response.toString());
                        JsonModel jsonModel = new Gson().fromJson(response.toString(), JsonModel.class);
                        if(URLCONST.isRSA && !StringHelper.isEmpty(jsonModel.getResult())) {
                            jsonModel.setResult(StringHelper.decode(new String(RSAUtilV2.decryptByPrivateKey(Base64.decode(jsonModel.getResult().replace("\n", ""), Base64.DEFAULT), APPCONST.privateKey))));
                        }
                        callback.onFinish(jsonModel);
                        Log.d("Http", "RSA finish："+ new Gson().toJson(jsonModel));
                    }
                }catch (Exception e){
                    callback.onError(e);
                }
            }
            @Override
            public void onFinish(String response) {
                Log.e("http",response);
            }
            @Override
            public void onError(Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public static void httpGetNoRSA(String url, final JsonCallback callback){
        Log.d("HttpGet URl", url);
        HttpUtil.sendGetRequest(url, new HttpCallback() {
            @Override
            public void onFinish(Bitmap bm){

            }
            @Override
            public void onFinish(InputStream in){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        response.append(line);
                        line = reader.readLine();
                    }
                    if(callback != null) {
                        Log.i("Http", "read finish："+response.toString());
                        // setResponse(response.toString());
                        JsonModel jsonModel = new Gson().fromJson(response.toString(), JsonModel.class);
                        callback.onFinish(jsonModel);
                        Log.d("Http", "RSA finish："+ new Gson().toJson(jsonModel));
                    }
                }catch (Exception e){
                    callback.onError(e);
                }
            }
            @Override
            public void onFinish(String response) {

            }
            @Override
            public void onError(Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public static void httpGetBitmap(String url, final HttpCallback callback){
        Log.d("Http", "success1");
        HttpUtil.sendBitmapGetRequest(url, new HttpCallback() {
            @Override
            public void onFinish(Bitmap bm){

            }
            @Override
            public void onFinish(InputStream in){
                if(callback != null) {
                    Bitmap bm = BitmapFactory.decodeStream(in);
//                    setBitmap(bm);
                    callback.onFinish(bm);
                }
            }
            @Override
            public void onFinish(String response) {

            }
            @Override
            public void onError(Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }



    public static void httpGetFile(String url, final HttpCallback callback){
        Log.d("Http", "success1");
        HttpUtil.sendGetRequest(url, new HttpCallback() {
            @Override
            public void onFinish(Bitmap bm){

            }
            @Override
            public void onFinish(InputStream in){
                callback.onFinish(in);

            }
            @Override
            public void onFinish(String response) {

            }
            @Override
            public void onError(Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public static String makeURL(String p_url, Map<String,Object> params) {
      return HttpUtil.makeURL(p_url,params);
    }

    public static String makeURLNoRSA(String p_url, Map<String,Object> params) {
     return HttpUtil.makeURLNoRSA(p_url,params);
    }

    public static String makePostOutput(Map<String, Object> params) {
       return HttpUtil.makePostOutput(params);
    }

}
