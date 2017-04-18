package com.zhao.buyer.presenter;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.zhao.buyer.common.URLCONST;
import com.zhao.buyer.callback.JsonCallback;
import com.zhao.buyer.callback.ResultCallback;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.httpconnection.ServerResponse;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.Utility;
import com.zhao.buyer.model.JsonModel;
import com.zhao.buyer.source.HttpDataSource;
import com.zhao.buyer.util.HttpUtil;
import com.zhao.greendao.entity.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhao on 2016/4/16.
 */
public class LoginPresenter extends BasePresenter{

    public void loginCheck(String account, String password, final ResultCallback callback){
        Map<String,Object> params = new HashMap<>();
        params.put("username",account);
        params.put("password",password);
        HttpDataSource.httpGetNoRSA(HttpUtil.makeURLNoRSA(URLCONST.method_login, params), new JsonCallback() {
            @Override
            public void onFinish(JsonModel jsonModel) {
                if(jsonModel.isSuccess()){
                    User user = new Gson().fromJson(jsonModel.getResult(),User.class);
                    callback.onFinish(user,jsonModel.getError());
                }else {
                    callback.onFinish(null,jsonModel.getError());
                }
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);

            }
        });
    }


    public void loginCheck(String account,String password, final HttpCallbackListener listener){
        ServerResponse sr = new ServerResponse();
        String sql = "from User where account='" + account + "' and password='" + password + "' and role='buyer'";
        sql = Utility.encode(sql);
        String address = "http://"+ APPCONST.ServerIP +"/android_Server/requestAccept.action?type=select&sql="+sql;
        sr.getStringResponse(address, new HttpCallbackListener() {
            @Override
            public void onFinish(Bitmap bm){}
            @Override
            public void onFinish(InputStream in) {}
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String tem = jsonObject.getString("Data");
                    JSONArray res = new JSONArray(tem);
                    if (listener != null) {
                        if (res.length() != 0)
                            listener.onFinish(res.getJSONObject(0).getString("account"));
                        else
                            listener.onFinish("error");
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }

                }
            }

            @Override
            public void onError(Exception e) {
                if (listener != null) {
                    listener.onError(e);
                }
            }
        });
    }

    public void register(String account,String password, final HttpCallbackListener listener){
        final JSONObject indata = new JSONObject();  //前台数据获取
        JSONObject tabledata = new JSONObject();
        try {
            tabledata.put("account",account);
            tabledata.put("password",password);
            tabledata.put("role","buyer");
            indata.put("table","user");
            indata.put("data",tabledata);
        }catch (Exception e){}
        final ServerResponse sr = new ServerResponse();
        String sql = "from User where account='" + account + "'";
        sql = Utility.encode(sql);
        final String address = "http://"+ APPCONST.ServerIP +"/android_Server/requestAccept.action?type=select&sql="+sql;
        sr.getStringResponse(address, new HttpCallbackListener() {

            @Override
            public void onFinish(Bitmap bm){}
            @Override
            public void onFinish(InputStream in) {}

            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String tem = jsonObject.getString("Data");
                    JSONArray res = new JSONArray(tem);                   //用户名重复检验
                    if (listener != null) {
                        if (res.length() == 0) {        //注册信息写入
                            String addr = "http://"+ APPCONST.ServerIP+"/android_Server/requestAccept.action?type=insert&inserData=" + Utility.encode(indata.toString());
                            sr.getStringResponse(addr, new HttpCallbackListener() {
                                @Override
                                public void onFinish(Bitmap bm){}
                                @Override
                                public void onFinish(InputStream in) {
                                }

                                @Override
                                public void onFinish(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String res = jsonObject.getString("Data");
                                        if (res.equals("success"))
                                            listener.onFinish("注册成功");
                                        else
                                            listener.onFinish("注册失败");

                                    } catch (Exception e) {

                                        listener.onError(e);

                                    }
                                }

                                @Override
                                public void onError(Exception e) {
                                    listener.onError(e);

                                }
                            });
                        } else {
                            listener.onFinish("用户名已存在");
                        }

                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
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
