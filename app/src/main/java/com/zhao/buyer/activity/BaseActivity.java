package com.zhao.buyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhao on 2016/4/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManage.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.removeActivity(this);
    }

}
