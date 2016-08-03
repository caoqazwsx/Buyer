package com.zhao.buyer.activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.zhao.buyer.R;
import com.zhao.buyer.custom.FragmentTabHost;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.fragment.shopfragment.*;

import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.itemmodel.Cart;
import com.zhao.buyer.presenter.ShopPresenter;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

public class ShopActivity extends BaseActivity {
    private int shop_id;
    private boolean isCollect;
    private String collect;

    private double bot_price;
    private String shop_name;
    private String deliveryService;
    private String serviceTime;
    private String specialOffer;
    private String state;


    private TextView tv_shop_name;
    private Button btn_back;
    private Button btn_collect;
    //  private ProgressBar progressBar;
    private FragmentTabHost mTabHost;
    //   private LayoutInflater layoutInflater;
    private ArrayList<String> fragmentTag;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    checkCollectState();
                    break;
                case 2:
                    initCollectState();
                    break;
                case -1:
                    Log.d("ShopActivity", "error collect");
                    break;
                case -2:
                    Log.d("ShopActivity", "error changeCollectState");
                    break;
            }
        }
    };


    private String mTextviewArray[] = {"food", "Comment", "seller"};

    //Tab选项卡的文字
    private String mViewArray[] = {"点菜", "评价", "商家"};
    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {FoodFragment.class, CommentFragment.class, SellerFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initView();
        initCollectState();
    }
//        new AsyncTask<Void,Integer,String>(){
//            @Override
//            protected void onPreExecute() {
//
//            }
//            @Override
//            protected String doInBackground(Void... params) {
//                return "";
//            }
//            @Override
//            protected void onPostExecute(String result){
//
//
//            }
//
//
//
//        }.execute();






    private void initView() {
        Intent it = getIntent();
        shop_id = it.getIntExtra("shop_id", Globalvariable.ERROR);
        shop_name = it.getStringExtra("shop_name");
        bot_price = it.getDoubleExtra("bot_price", Globalvariable.ERROR);
        serviceTime = it.getStringExtra("serviceTime");
        deliveryService = it.getStringExtra("deliveryService");
        specialOffer = it.getStringExtra("specialOffer");
        state = it.getStringExtra("state");
        Log.d("ShopActivity shopId", "" + shop_id);
        tv_shop_name = (TextView) findViewById(R.id.title_text);
        btn_back = (Button) findViewById(R.id.title_back);
        btn_collect = (Button) findViewById(R.id.btn_collect);
        tv_shop_name.setText(shop_name);
        isCollect = false;
        btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collect == null) {
                    initCollectState();
                    return;
                }
                ShopPresenter sp = new ShopPresenter();
                sp.changeShopCollect(collect, shop_id, isCollect, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if (response.equals("success")) {
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        } else {
                            Message msg = new Message();
                            msg.what = -2;
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFinish(InputStream in) {

                    }

                    @Override
                    public void onFinish(Bitmap bm) {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("ShopActivity", e.toString());
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);

                    }
                });

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!getIntent().getStringExtra("discount").equals("")) {
            try {
                Cart.getCart().setDiscount(new JSONArray(getIntent().getStringExtra("discount")));
            } catch (Exception e) {
                Log.d("ShopActivity", e.toString());
                Cart.getCart().setDiscount(null);
            }
        } else {
            Cart.getCart().setDiscount(null);
        }

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);


        //得到fragment的个数
        int count = mTextviewArray.length;


        fragmentTag = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(mViewArray[i]);
            fragmentTag.add(i, tabSpec.getTag());
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_background);
        }

    }


    public Fragment getFragment(int index) {
        // Log.d("FoodList",fragmentTag.get(index));
        return getSupportFragmentManager().findFragmentByTag(fragmentTag.get(index));
    }

    public void initCollectState() {
        final ShopPresenter sp = new ShopPresenter();
        sp.getCollect(shop_id, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("ShopActivity", response);
                collect = response;
                isCollect = sp.isCollected(collect, shop_id);
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void onFinish(InputStream in) {

            }

            @Override
            public void onFinish(Bitmap bm) {

            }

            @Override
            public void onError(Exception e) {
                Log.d("ShopActivity", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }
        });
    }

    public void checkCollectState() {
        if (isCollect) {
            btn_collect.setBackgroundResource(R.drawable.ek);
        } else {
            btn_collect.setBackgroundResource(R.drawable.en);
        }

    }

    public String getState() {
        return state;
    }

    public int getShopId() {
        return shop_id;
    }

    public String getDeliveryService() {
        return this.deliveryService;
    }

    public String getServiceTime() {
        return this.serviceTime;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getSpecialOffer() {
        return specialOffer;
    }

    public double getBotPrice() {
        return bot_price;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                     FoodFragment foodFragment = (FoodFragment) getFragment(0);
                    foodFragment.updateListView();
                }
                break;
            default:
        }
    }
}


