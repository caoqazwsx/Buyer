package com.zhao.buyer.presenter;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.globalvariable.Utility;

/**
 * Created by zhao on 2016/5/8.
 */
public class SellerPresenter extends  BasePresenter {

    public void getSellerInfo(int shopId, final HttpCallbackListener listener){
        String address = Globalvariable.SERVER_ADDRESS+"type=select&sql="+ Utility.encode("from SellerInfo where shop_id="+shopId);
        getList(address,listener);
    }
}
