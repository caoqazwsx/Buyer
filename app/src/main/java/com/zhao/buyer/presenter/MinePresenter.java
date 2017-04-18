package com.zhao.buyer.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.Utility;
import com.zhao.buyer.tablemodel.Bill;

import org.json.JSONObject;

/**
 * Created by zhao on 2016/5/9.
 */
public class MinePresenter extends BasePresenter {

    public void charge(double cash,final HttpCallbackListener listener){
        Bill bill = new Bill();
        bill.setAccount(APPCONST.ACCOUNT);
        bill.setIncomeAndExpenses(cash);
        bill.setNote("Charge");
        bill.setTime(Utility.getStringNowTime());
        JSONObject intable = new JSONObject();
        try {
            intable.put("table","Bill");
            intable.put("data",new Gson().toJson(bill));
        }catch (Exception e){
            e.printStackTrace();
        }
        String address = APPCONST.SERVER_ADDRESS+"type=insert&inserData="+Utility.encode(intable.toString());
        update(address,listener);

    }

    public void getBalance(final HttpCallbackListener listener){
        String address = APPCONST.SERVER_ADDRESS+"type=select&sql="+ Utility.encode("from Coffer where account='"+ APPCONST.ACCOUNT+"'");
        getList(address,listener);
    }

    public void getMyCommentList(final HttpCallbackListener listener){
        String address = APPCONST.SERVER_ADDRESS+"type=select&sql="+Utility.encode("from Comment where commentAccount='"+ APPCONST.ACCOUNT+"'");
        getList(address,listener);
    }

    public void getMyCollect(final HttpCallbackListener listener){
        String address = APPCONST.SERVER_ADDRESS+"type=mycollect&account="+Utility.encode(APPCONST.ACCOUNT);
        getList(address,listener);
    }

    public void getMyAddress(final HttpCallbackListener listener){
        String address = APPCONST.SERVER_ADDRESS+"type=select&sql="+Utility.encode("from DeliveryAddress where buyerAccount='"
                + APPCONST.ACCOUNT+"'");
        getList(address,listener);
    }

    public  void addAddress( String contact, String telephone, String address,String no ,double lng,double lat,final HttpCallbackListener listener) {
        JSONObject indata = new JSONObject();
      //前台数据获取
        JSONObject tabledata = new JSONObject();
        try {
            tabledata.put("buyerAccount", Utility.encode(APPCONST.ACCOUNT));
            tabledata.put("contact", Utility.encode(contact));
            tabledata.put("telephone", telephone);
            tabledata.put("address", Utility.encode(address));
            tabledata.put("no",Utility.encode(no));
            tabledata.put("location",lng+","+lat);
            indata.put("table", "DeliveryAddress");
            indata.put("data", tabledata);

        } catch (Exception e) {
            Log.d("addAddressPresenter", e.toString());
            listener.onError(e);
        }

        Log.d("SubmitFormPresenter ", "data" + indata.toString());
        String ads = APPCONST.SERVER_ADDRESS+"type=insert&inserData=" + Utility.encode(indata.toString());
        update(ads,listener);
    }

    public void deleteAddress(int addressId,final HttpCallbackListener listener){
        String address = APPCONST.SERVER_ADDRESS + "type=delete&sql="+Utility.encode("delete DeliveryAddress where id="+addressId);
        update(address,listener);
    }

    public  void editAddress( int addressId,String contact, String telephone, String address,String no ,double lng,double lat,final HttpCallbackListener listener) {
        String ads = APPCONST.SERVER_ADDRESS + "type=update&sql="+Utility.encode(
                "update DeliveryAddress set contact='"+Utility.encode(contact)+"',telephone='"+Utility.encode(telephone)+
                        "',address='"+Utility.encode(address)+"',no='"+ Utility.encode(no)+"',location='"+lng+","+lat+ "' where id="+addressId);
        update(ads,listener);
    }
}
