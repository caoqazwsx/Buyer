package com.zhao.buyer.presenter;

import android.util.Log;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.globalvariable.Utility;
import com.zhao.buyer.itemmodel.CartFoodItem;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhao on 2016/5/5.
 */
public class CommentPresenter extends BasePresenter {

    public void submitComment(long formId, Integer shopId, String formFood,Integer shop_grade, Integer send_grade,String commentText,
                              final HttpCallbackListener listener){
        String indata = initComment(formId,shopId,formFood,shop_grade,send_grade,commentText);
        String address = Globalvariable.SERVER_ADDRESS+"type=insert&inserData=" + Utility.encode(indata);
        update(address,listener);

    }

    public String initComment(long formId,Integer shopId, String formFood,Integer shop_grade,Integer send_grade, String commentText){
        Log.d("CommentPresenter",formFood);
        FormPresenter fp = new FormPresenter();
        ArrayList<CartFoodItem> cartFoodItems = fp.getFoodList(formFood);
        String food = "";
        for(int i = 0;i<cartFoodItems.size();i++){
            food += Utility.encode(cartFoodItems.get(i).getFoodName());
            if(i < cartFoodItems.size()-1){
                food +=Utility.encode("+");
            }
        }
        JSONObject indata = new JSONObject();  //前台数据获取
        JSONObject tabledata = new JSONObject();
        try {
            tabledata.put("commentAccount", Globalvariable.ACCOUNT);
            tabledata.put("shopId", shopId);
            tabledata.put("formId", formId);
            tabledata.put("food", food);
            tabledata.put("shopGrade", shop_grade);
            tabledata.put("sendGrade", send_grade);
            tabledata.put("commentText", Utility.encode(commentText));
            tabledata.put("commentTime", Utility.getStringNowTime());
            indata.put("table","comment");
            indata.put("data",tabledata);
        }catch (Exception e){
            Log.d("CommentPresenter",e.toString());
        }
        Log.d("CommentPresenter",indata.toString());
        return indata.toString();

    }

    public void getCommentList(int shopId,final HttpCallbackListener listener){
        String address = Globalvariable.SERVER_ADDRESS+"type=select&sql="+Utility.encode("from Comment where shopId="+shopId+" order by commentTime desc");
        getList(address,listener);
    }
}
