package com.zhao.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.presenter.PayPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class PayActivity extends BaseActivity {
    private RadioGroup radioGroup;
    private Button btn_back;
    private TextView title;
    private Button btn_pay;
    private TextView balance;
    private String pay_method;
    private double pay_price;
    private long form_id;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(PayActivity.this, "付款成功", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(PayActivity.this, HomeActivity.class);
                   // it.putExtra("form","form");
                    startActivity(it);
                    break;
                case 2:
                    String bal = (String)msg.obj;
                    balance.setText("余额：￥ "+bal);
                    break;
                case -1:
                    Toast.makeText(PayActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        radioGroup = (RadioGroup)findViewById(R.id.pay_method);
        btn_back = (Button)findViewById(R.id.title_back);
        btn_pay = (Button)findViewById(R.id.pay);
        balance = (TextView)findViewById(R.id.balance);
        title = (TextView)findViewById(R.id.title_text);
        title.setText("在线支付");
        init();
    }

    private void init(){

        pay_method = "balance";
        pay_price = getIntent().getDoubleExtra("pay_price",0);
        form_id = getIntent().getLongExtra("form_id",0);
        PayPresenter pp = new PayPresenter();
        pp.getBalance(Globalvariable.ACCOUNT, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if(!response.equals("null")) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        JSONObject jb = (JSONObject) ja.get(0);
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = jb.getString("balance");
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = "error";
                        handler.sendMessage(msg);

                    }
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
                Message msg = new Message();
                msg.what = 2;
                msg.obj = "error";
                handler.sendMessage(msg);

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PayActivity.this, HomeActivity.class);
               // it.putExtra("form","form");
                startActivity(it);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_balance) {
                    balance.setVisibility(View.VISIBLE);
                    pay_method = "balance";
                } else if (checkedId == R.id.radio_other) {
                    balance.setVisibility(View.INVISIBLE);
                    pay_method = "other";
                }
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pay_method.equals("balance")) {
                   // Log.d("PayActivity", "111");
                    PayPresenter pp = new PayPresenter();
                    Log.d("PayActivity", pay_price + "   " + form_id);
                    pp.pay(pay_price, form_id, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            Message msg = new Message();
                            if (response.equals("success")) {
                                msg.what = 1;
                                handler.sendMessage(msg);
                            } else {
                                msg.what = -1;
                                msg.obj = response;
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
                            Log.d("PayActivity", e.toString());
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = e.toString();
                            handler.sendMessage(msg);
                        }
                    });
                }else{
                    Toast.makeText(PayActivity.this, "暂不支持此种方式付款", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
