package com.zhao.buyer;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhao on 2017/3/15.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }

}
