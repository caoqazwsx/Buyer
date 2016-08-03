package com.zhao.buyer.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.httpconnection.ServerResponse;
import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.globalvariable.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by zhao on 2016/4/26.
 */
public class ShopPresenter extends BasePresenter {


    public void getShopName(int shop_id, final HttpCallbackListener listener) {
        String order = "type=select&sql=" + Utility.encode("from Shop where id=" + shop_id);
        ServerResponse sr = new ServerResponse();
        sr.getStringResponse(Globalvariable.SERVER_ADDRESS + order, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jb = new JSONObject(response);
                    JSONArray ja = new JSONArray(jb.getString("Data"));
                    if (ja.length() != 0) {
                        if (listener != null) {
                            listener.onFinish(ja.getJSONObject(0).getString("shopName"));
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

    public void getFoodList(int shop_id, final HttpCallbackListener listener) {
        String address = Globalvariable.SERVER_ADDRESS + "type=select&sql=" + Utility.encode("from Food where shopId=" + shop_id);
        getList(address, listener);
    }

    public void getCollect(final int shop_id, final HttpCallbackListener listener) {
        String address = Globalvariable.SERVER_ADDRESS + "type=select&sql=" + Utility.encode("from BuyerInfo where buyerAccount='"
                + Globalvariable.ACCOUNT+"'");
        getList(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("ShopPresenter", response);
                try {
                    JSONObject jb = new JSONArray(response).getJSONObject(0);
                    Log.d("ShopPresenter", jb.toString());
                   listener.onFinish(jb.getString("collect"));
                } catch (Exception e) {
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

    public boolean isCollected(String collect,int shop_id) {

        if (collect.equals("")) {
            return false;
        } else {
            try {
                JSONArray ja = new JSONArray(collect);
                int i;
                for (i = 0; i < ja.length(); i++) {
                    if (ja.getInt(i) == shop_id) {
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }

        }
        return false;
    }

    public void changeShopCollect(String collect,int shop_id,boolean currentState,final HttpCallbackListener listener){
        JSONArray ja = null;
        Log.d("ShopPresenter1", collect + " "+ shop_id+ " "+currentState);
        if(currentState){
            try {
                 ja = new JSONArray(collect);
               for(int i=0 ;i<ja.length();i++){
                   if(ja.getInt(i) == shop_id){
                       ja.remove(i);
                   }
               }
            } catch (Exception e) {

            }
        }else{
            if(collect.equals("")){
                ja = new JSONArray();
            }else {
                try {
                    ja = new JSONArray(collect);
                } catch (Exception e) {

                }
            }
            ja.put(shop_id);
        }
        String address = null;
        try {
            address = Globalvariable.SERVER_ADDRESS + "type=update&sql=" +
                    Utility.encode("update BuyerInfo set collect='" + ja.toString() + "' where buyerAccount='" + Globalvariable.ACCOUNT + "'");
        } catch (Exception e) {
            Log.d("ShopPresenter", e.toString());
        }
        update(address, listener);

    }







}
