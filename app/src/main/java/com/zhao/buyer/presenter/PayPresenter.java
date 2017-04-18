package com.zhao.buyer.presenter;

import android.util.Log;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.httpconnection.ServerResponse;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.Utility;

/**
 * Created by zhao on 2016/4/28.
 */
public class PayPresenter  extends BasePresenter{

    public void pay(double price,long form_id,final HttpCallbackListener listener){
        ServerResponse sr = new ServerResponse();

        String address = "http://"+ APPCONST.ServerIP +"/android_Server/requestAccept.action?type=pay&price="+Utility.encode(price+"")+"&formId="
                +Utility.encode(""+form_id)+"&account="+Utility.encode(APPCONST.ACCOUNT);
        Log.d("111PayActivity",address);
       update(address,listener);
    }

    public void getBalance(String account,final HttpCallbackListener listener){
        String address = APPCONST.SERVER_ADDRESS+"type=select&sql="+ Utility.encode("from Coffer where account='"+account+"'");
        getList(address,listener);
    }
}
