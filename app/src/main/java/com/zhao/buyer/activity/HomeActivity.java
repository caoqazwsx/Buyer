package com.zhao.buyer.activity;


import com.zhao.buyer.Database.BuyerDatabaseHelper;
import com.zhao.buyer.R;
import com.zhao.buyer.custom.FragmentTabHost;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.fragment.homefragment.FormFragment;
import com.zhao.buyer.fragment.homefragment.ShopFragment;
import com.zhao.buyer.fragment.homefragment.MineFragment;
import com.zhao.buyer.common.APPCONST;
import com.zhao.greendao.service.TestService;

public class HomeActivity extends BaseActivity {
    private FragmentTabHost mTabHost;
    private int exitTime;

    //定义一个布局
    private LayoutInflater layoutInflater;

    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {ShopFragment.class,FormFragment.class, MineFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_shoplist, R.drawable.tab_form, R.drawable.tab_mine};

    //Tab选项卡的Tag
    private String mTextviewArray[] = {"shop", "form", "mine"};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkLoginState();
        initView();
        exitTime = 0;
        new TestService().getUser();
        //自定义
    }


    /**
     * 初始化组件
     */
    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //得到fragment的个数
       int count = mTextviewArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            //mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.lb);
        }

    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {

        View view = layoutInflater.inflate(R.layout.tabhost_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.tabitemtext);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    public Fragment getFragment(String tag){
        // Log.d("FoodList",fragmentTag.get(index));
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    public void checkLoginState(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BuyerDatabaseHelper dbh = new BuyerDatabaseHelper(HomeActivity.this,"Buyer.db",null,1);
                SQLiteDatabase db = dbh.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from User",null);
                if(cursor.moveToFirst()){
                    APPCONST.ACCOUNT = cursor.getString(cursor.getColumnIndex("account"));
                    APPCONST.PASSWROD = cursor.getString(cursor.getColumnIndex("password"));
                    APPCONST.LOGIN_STATE = true;
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed(){
        if(exitTime == 0){
            Toast.makeText(HomeActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            exittiming();
        }else {
            super.onBackPressed();
        }
    }

    private void exittiming(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(exitTime < 3){
                    exitTime++;
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){

                    }
                }
                exitTime = 0;
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    ShopFragment sf = ( ShopFragment)getFragment("shop");
                    boolean state = data.getBooleanExtra("state",false);
                    if(state){
                       // Log.d("Location",data.getDoubleExtra("lat",0)+"  "+getIntent().getDoubleExtra("lng",0));
                        sf.setLat(data.getDoubleExtra("lat",0));
                        sf.setLng(data.getDoubleExtra("lng",0));
                        sf.setMyLocation(data.getStringExtra("name"));
                        sf.initShopList();
                    }else{
                        sf.initMyLocation();
                    }
                }
                break;
            default:
        }
    }





}