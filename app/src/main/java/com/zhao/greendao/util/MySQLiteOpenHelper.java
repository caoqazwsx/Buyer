package com.zhao.greendao.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhao.greendao.gen.DaoMaster;
import com.zhao.greendao.gen.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by zhao on 2017/3/15.
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //加入你要新建的或者修改的表的信息
        GreenDaoUpgrade.getInstance().migrate(db, UserDao.class);
    }
}
