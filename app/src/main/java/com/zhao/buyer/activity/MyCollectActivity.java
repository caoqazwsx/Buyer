package com.zhao.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhao.buyer.R;
import com.zhao.buyer.custom.DownUpListView;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.itemmodel.Shop;
import com.zhao.buyer.itemmodel.ShopItemAdapter;
import com.zhao.buyer.presenter.MinePresenter;
import com.zhao.buyer.presenter.ShopListPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MyCollectActivity extends BaseActivity {

    private TextView title;
    private Button btn_back;
    private ProgressBar progressBar;
    private DownUpListView listView;
    private  ArrayList<Shop> shopItems;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setVisibility(View.GONE);
                    listView.onRefreshComplete();
                    ShopItemAdapter shopItemAdapter = new ShopItemAdapter(MyCollectActivity.this, R.layout.listview_item_shop,( ArrayList<Shop>)msg.obj );
                    listView.setAdapter(shopItemAdapter);
                    //Utility.setListViewHeightBasedOnChildren(listView);
                    break;
                case -1:
                    Toast.makeText(MyCollectActivity.this,"请检查你的网络",Toast.LENGTH_SHORT).show();
                    Log.d("MyCollectActivity","error init");
                    break;
                case -2:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MyCollectActivity.this, "无内容", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        title = (TextView)findViewById(R.id.title_text);
        btn_back = (Button)findViewById(R.id.title_back);
        listView = (DownUpListView) findViewById(R.id.myCollect_list);
        progressBar = (ProgressBar)findViewById(R.id.myCollect_progressBar);
        shopItems = new ArrayList<Shop>();
        title.setText("我的收藏");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setonRefreshListener(new DownUpListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MyCollectActivity.this, ShopActivity.class);//此处的position-1是因为加入了刷新头视图，内容项整体后移
                it.putExtra("shop_name", shopItems.get(position-1).getShopName());
                it.putExtra("shop_id", shopItems.get(position-1).getId());
                it.putExtra("bot_price", shopItems.get(position-1).getBotPrice());
                it.putExtra("deliveryService", shopItems.get(position-1).getDeliveryService());
                it.putExtra("serviceTime", shopItems.get(position-1).getServiceTime());
                it.putExtra("specialOffer", shopItems.get(position-1).getSpecialOffer());
                it.putExtra("address", shopItems.get(position-1).getTelephone());
                it.putExtra("telephone", shopItems.get(position-1).getTelephone());
                it.putExtra("discount", shopItems.get(position-1).getDiscount());
                it.putExtra("state", shopItems.get(position-1).getState());
                startActivity(it);
            }
        });

        init();
    }
    
    private void  init(){
        listView.setAdapter(null);
        shopItems.clear();
        MinePresenter mp = new MinePresenter();
        mp.getMyCollect(new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    shopItems = new ArrayList<Shop>();
                    if(ja.length() != 0){
                        ShopListPresenter slp = new ShopListPresenter();
                        ArrayList<Shop> restShopItems  = new ArrayList<Shop>();
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jb = ja.getJSONObject(i);
                            jb.put("discount",jb.getString("discount"));
                            Shop shop = new Gson().fromJson(jb.toString(),Shop.class);
                            if(shop.getState().equals("rest")){
                                restShopItems.add(shop);
                            }else {
                                shop.setDistance(slp.countDistance(shop,MyCollectActivity.this));
                                shopItems.add(shop);
                            }
                        }
                        for(int i =0;i<restShopItems.size();i++){
                            shopItems.add(restShopItems.get(i));
                        }
                        Log.d("MyCollect", "shopitems add success!");
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = shopItems;
                        handler.sendMessage(msg);
                    }else{
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);
                    }
                }catch (Exception e){
                    Log.d("MyCollect", e.toString());
                    Message msg = new Message();
                    msg.what = -1;
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
                Log.d("MyCollect", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);

            }
        });

    }
}
