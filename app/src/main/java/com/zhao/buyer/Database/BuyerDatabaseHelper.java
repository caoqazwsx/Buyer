package com.zhao.buyer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhao on 2016/7/10.
 */
public class BuyerDatabaseHelper extends SQLiteOpenHelper{

    public static final String CREATE_USER = "create table User("
            + "id integer primary key autoincrement, "
            + "account varchar, "
            + "password varchar) ";


    private Context mContext;

    public BuyerDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVerson){

    }
}
