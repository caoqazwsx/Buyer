package com.zhao.greendao.service;

import android.database.Cursor;
import android.util.Log;

import com.zhao.greendao.GreenDaoManager;
import com.zhao.greendao.entity.User;
import com.zhao.greendao.gen.DaoMaster;
import com.zhao.greendao.gen.DaoSession;
import com.zhao.greendao.gen.UserDao;
import com.zhao.greendao.util.MySQLiteOpenHelper;

import java.util.List;

/**
 * Created by zhao on 2017/3/15.
 */

public class TestService {

    public void getUser(){
     /*   MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this, "waimai", null);
        DaoMaster daoMaster = new DaoMaster(mySQLiteOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        User user = new User(null, "heheheh","heheheh",100,100,100);
        userDao.insert(user);
        List<User> userList = userDao.queryBuilder()
                .where(UserDao.Properties.Name.eq("heheheh"))
                .build().list();

        for(User user1 : userList){
            Log.d("GreenDao",user1.getName());
        }*/

        UserDao dao = GreenDaoManager.getInstance().getSession().getUserDao();
        Cursor cursor = dao.getDatabase().rawQuery("select * from user where name='heheheh'", null);
        while (cursor.moveToNext()) {
            String salesWxNickName = cursor.getString(0);
            String clientWxNickName = cursor.getString(1);
            int chatCount = cursor.getInt(2);
            int talkerId = cursor.getInt(3);
            String salesWxAccount = cursor.getString(4);
        }
    }
}
