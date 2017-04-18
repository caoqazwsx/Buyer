package com.zhao.buyer.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.Utility;
import com.zhao.buyer.itemmodel.CartFoodItem;
import com.zhao.buyer.itemmodel.Complain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zhao on 2016/5/1.
 */
public class FormPresenter extends BasePresenter {

    public void getFormList(final HttpCallbackListener listener) {
        String address = APPCONST.SERVER_ADDRESS + "type=formlist&account=" + Utility.encode(APPCONST.ACCOUNT);
        getList(address, listener);
    }

    public void getFormInfo(long id,final HttpCallbackListener listener){
        String address = APPCONST.SERVER_ADDRESS + "type=select&sql=" + Utility.encode("from Form where id ="+id);
        getList(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    Log.d("FormInfo Data",response);
                    JSONObject jb = new JSONArray(response).getJSONObject(0);
                    jb.put("formFood",jb.getString("formFood"));
                    String strForm = jb.toString();
                    if(listener!=null) {
                        listener.onFinish(strForm);
                    }
                }catch (Exception e){
                    if(listener!=null) {
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
                if(listener!=null) {
                    listener.onError(e);
                }

            }
        });
    }

    public void deleteFormItem(long id, final HttpCallbackListener listener) {
        String address = APPCONST.SERVER_ADDRESS + "type=deleteform&formId=" + id;
        update(address, listener);
    }

    public void getFormFoodListToCart(long id, final HttpCallbackListener listener) {

        String address = APPCONST.SERVER_ADDRESS + "type=select&sql=" + Utility.encode("select formFood from Form where id=" + id);
        getList(address, listener);
    }

    public void confirmForm(long id, final HttpCallbackListener listener) {
        String address = APPCONST.SERVER_ADDRESS + "type=confirmForm&formId="+id;
        update(address, listener);
    }

    public void cancelForm(long id, final HttpCallbackListener listener) {
        String address = APPCONST.SERVER_ADDRESS + "type=cancelform&formId=" + id;
        update(address, listener);

    }

    public void backForm(long id,String reason,final HttpCallbackListener listener){
        String address = APPCONST.SERVER_ADDRESS + "type=update&sql="
                + Utility.encode("update Form set formState='WaitBack',note='"+Utility.encode(reason)+"' where id ="+id);
        update(address, listener);
    }

    public void getSenderInfo(String senderAccount,final HttpCallbackListener listener) {
        String address = APPCONST.SERVER_ADDRESS + "type=select&sql=" +
                Utility.encode("from SenderInfo where senderAccount='"+senderAccount+"'");
        getList(address, listener);
    }

    public void submitComplain(Complain complain,final HttpCallbackListener listener){
        JSONObject tabledata = new JSONObject();
        complain.setComplainText(Utility.encode(complain.getComplainText()));
        try {
            tabledata.put("table","Complain");
            tabledata.put("data",new Gson().toJson(complain));
        }catch (Exception e){
            Log.d("submitComplain",e.toString());
        }
        String address = APPCONST.SERVER_ADDRESS + "type=insert&inserData=" + Utility.encode(tabledata.toString());
        update(address, listener);
    }

    public ArrayList<CartFoodItem> getFoodList(String formFood){
        ArrayList<CartFoodItem> cartFoodItems = new ArrayList<CartFoodItem>();
        try {
            JSONArray ja = new JSONArray(formFood);
            for(int i =0 ; i<ja.length();i++) {
                JSONObject jb = ja.getJSONObject(i);
                cartFoodItems.add(new CartFoodItem(jb.getInt("foodId"), jb.getString("foodName"), jb.getInt("foodNum"),
                        jb.getDouble("foodPrice"), jb.getDouble("foodTotalPrice")));
            }
        }catch (Exception e){
            Log.d("FormInfoActivty",e.toString());
        }
        return cartFoodItems;
    }
}

