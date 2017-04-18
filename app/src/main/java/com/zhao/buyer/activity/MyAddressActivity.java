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
import com.zhao.buyer.itemmodel.DeliveryAddress;
import com.zhao.buyer.itemmodel.DeliveryAddressAdapter;
import com.zhao.buyer.presenter.MinePresenter;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

public class MyAddressActivity extends BaseActivity {
    private String state;

    private TextView title;
    private Button btn_back;
    private ProgressBar progressBar;
    private DownUpListView listView;
    private Button btn_add;

    private ArrayList<DeliveryAddress>  deliveryAddresses;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setVisibility(View.GONE);
                    listView.onRefreshComplete();
                    DeliveryAddressAdapter deliveryAddressAdapter = new DeliveryAddressAdapter(MyAddressActivity.this,
                            R.layout.listview_item_address,deliveryAddresses);
                    listView.setAdapter(deliveryAddressAdapter);
                    //Utility.setListViewHeightBasedOnChildren(listView);
                    break;
                case -1:
                    Toast.makeText(MyAddressActivity.this,"网络不好",Toast.LENGTH_SHORT).show();
                    Log.d("DeliveryAddress","error init");
                    break;
                case -2:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MyAddressActivity.this, "无内容", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        deliveryAddresses = new ArrayList<DeliveryAddress>();
        state = getIntent().getStringExtra("state");
        title = (TextView)findViewById(R.id.title_text);
        btn_back = (Button)findViewById(R.id.title_back);
        listView = (DownUpListView) findViewById(R.id.myAddress_list);
        progressBar = (ProgressBar)findViewById(R.id.myAddress_progressBar);
        btn_add = (Button)findViewById(R.id.myAddress_btn_add);
        if(state.equals("show")) {
            listView.setEnabled(false);
            title.setText("我的地址");
        }else {
            listView.setEnabled(true);
            title.setText("选择我的地址");
        }
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
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MyAddressActivity.this,EditAddressActivity.class);
                it.putExtra("state","add");
                startActivity(it);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent();
                it.putExtra("contact", deliveryAddresses.get(position-1).getContact());
                it.putExtra("telephone", deliveryAddresses.get(position-1).getTelephone());
                it.putExtra("address", deliveryAddresses.get(position-1).getAddress()+" "+deliveryAddresses.get(position-1).getNo());
                it.putExtra("addressLocation",deliveryAddresses.get(position-1).getLocation());
                setResult(RESULT_OK, it);
                finish();
            }
        });

       // init();
    }

    public void init() {
        deliveryAddresses.clear();
        listView.setAdapter(null);
        MinePresenter mp = new MinePresenter();
        mp.getMyAddress(new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    if (ja.length() != 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            deliveryAddresses.add(new Gson().fromJson(ja.getString(i), DeliveryAddress.class));
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }else{
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);
                    }
                }catch(Exception e){
                    Log.d("DeliveryAddress", e.toString());
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
                Log.d("DeliveryAddress", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        init();
    }
}
