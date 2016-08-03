package com.zhao.buyer.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.httpconnection.ServerResponse;
import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.globalvariable.Utility;
import com.zhao.buyer.itemmodel.Cart;
import com.zhao.buyer.itemmodel.CartFoodItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zhao on 2016/4/28.
 */
public class SubmitFormPresenter extends BasePresenter{
    private  long formId;

    public void submit(Double pay_price,String formState,Integer shopId, String shopName, String telephone,
                       String contact,String formAddress, String payMethod, String sendTime,String note,String addressLocation,
                       final HttpCallbackListener listener){

        JSONObject indata = new JSONObject();  //前台数据获取
        JSONObject tabledata = new JSONObject();
        try {
            tabledata.put("shopId",shopId);
            tabledata.put("shopName", Utility.encode(shopName));
            tabledata.put("telephone",telephone);
            tabledata.put("contact",Utility.encode(contact));
            tabledata.put("formAddress",Utility.encode(formAddress));
            tabledata.put("addressLocation",addressLocation);
            tabledata.put("payMethod",payMethod);
            tabledata.put("sendTime",Utility.encode(sendTime));
            tabledata.put("formState",formState);
            tabledata.put("formFood",getFormFood());
            formId = Long.parseLong(createFormId());
            tabledata.put("id",formId);
            tabledata.put("buyerAccount", Globalvariable.ACCOUNT);
            tabledata.put("submitTime", Utility.getStringNowTime());
            tabledata.put("note", Utility.encode(note));
            tabledata.put("payPrice",pay_price);
            indata.put("table","form");
            indata.put("data", tabledata);
        }catch (Exception e){
            Log.d("SubmitFormPresenter",e.toString());
           listener.onError(e);
        }
        ServerResponse sr = new ServerResponse();
        Log.d("SubmitFormPresenter ", "data"+indata.toString());
        String addr = "http://"+Globalvariable.ServerIP+"/android_Server/requestAccept.action?type=submitform&inserData=" + Utility.encode(indata.toString());
        sr.getStringResponse(addr, new HttpCallbackListener() {
            @Override
            public void onFinish(Bitmap bm){}
            @Override
            public void onFinish(InputStream in) {
            }

            @Override
            public void onFinish(String response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                    String res = jsonObject.getString("Data");
                    if (res.equals("success"))
                        listener.onFinish(formId+"");
                    else
                        listener.onFinish("error:"+res);

                } catch (Exception e) {
                    listener.onError(e);
                }
            }
            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });
    }

    public void getAddress(final HttpCallbackListener listener){
        String address = Globalvariable.SERVER_ADDRESS+"type=select&sql="+Utility.encode("from DeliveryAddress where buyerAccount='"
                +Globalvariable.ACCOUNT+"'");
        getList(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    if(ja.length() != 0){
                        listener.onFinish(ja.getString(0));
                    }else{
                        listener.onFinish("");
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


    private String createFormId(){

        SimpleDateFormat formatter  =  new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate  =  new Date(System.currentTimeMillis());//获取当前时间
        String  str  =  formatter.format(curDate);
        int r = (int)(100+Math.random()*999);
        String rand =Integer.toString(r);
        return rand+str;

    }


    private String getFormFood(){
        Cart cart = Cart.getCart();
        ArrayList<CartFoodItem> foodItems = cart.getCart_food_list();
        //String str = null;
        String str = "[";
       // JSONArray formFood;
        JSONObject food = new JSONObject();
        try {

            for (int i = 0; i < foodItems.size(); i++) {
                food.put("foodId", foodItems.get(i).getFoodId());
                food.put("foodName", Utility.encode(foodItems.get(i).getFoodName()));
                food.put("foodNum", foodItems.get(i).getFoodNum());
                food.put("foodTotalPrice", foodItems.get(i).getFoodTotalPrice());
                food.put("foodPrice", foodItems.get(i).getFoodPrice());
                if(i < foodItems.size()+1) str += food.toString()+",";
            }
            str += "]";
           // formFood = new JSONArray(str);
        }catch(Exception e){
            return null;
        }

        return str;
    }


}
