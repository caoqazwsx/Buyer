package com.zhao.buyer.callback;


import com.zhao.buyer.model.JsonModel;

/**
 * Created by zhao on 2016/10/25.
 */

public interface ResultCallback {

    void onFinish(Object o,int code);

    void onError(Exception e);

}
