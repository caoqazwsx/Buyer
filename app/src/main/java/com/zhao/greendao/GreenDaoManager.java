package com.zhao.greendao;

import android.content.Context;

import com.zhao.buyer.MyApplication;
import com.zhao.greendao.gen.DaoMaster;
import com.zhao.greendao.gen.DaoSession;
import com.zhao.greendao.util.GreenDaoUpgrade;
import com.zhao.greendao.util.MySQLiteOpenHelper;

/**
 * Created by zhao on 2017/3/15.
 */

public class GreenDaoManager {
    private static GreenDaoManager instance;
    private static DaoMaster daoMaster;

    public static GreenDaoManager getInstance() {
        if (instance == null) {
            instance = new GreenDaoManager();
        }
        return instance;
    }

    public GreenDaoManager(){
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(MyApplication.getContext(), "waimai", null);
        daoMaster = new DaoMaster(mySQLiteOpenHelper.getWritableDatabase());
    }



    public DaoSession getSession(){
       return daoMaster.newSession();
    }

}
