package com.zhao.buyer.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.presenter.MinePresenter;

import java.io.InputStream;
import java.text.DecimalFormat;

public class ChargeActivity extends AppCompatActivity {
    private TextView title;
    private Button btn_back;

    private EditText cash;
    private double db_cash;

    private Button submit;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    Toast.makeText(ChargeActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;


                case -1:
                    Toast.makeText(ChargeActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        title = (TextView) findViewById(R.id.title_text);
        btn_back = (Button)findViewById(R.id.title_back);
        cash = (EditText)findViewById(R.id.charge_cash);
        submit = (Button)findViewById(R.id.charge_submit) ;
        title.setText("充值");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCash()) {
                    DecimalFormat df = new DecimalFormat("#.00");
                    df.format(db_cash);
                    MinePresenter sp = new MinePresenter();
                    sp.charge(db_cash, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            if (response.equals("success")) {
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            } else {
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
                            Log.d("CashActivity", e.toString());
                            Message msg = new Message();
                            msg.what = -2;
                            handler.sendMessage(msg);
                        }
                    });
                }

            }
        });
    }

    public boolean checkCash(){
        try {
            db_cash = Double.parseDouble(cash.getText().toString());
            if(db_cash < 0) {
                Toast.makeText(this, "充值不能为负",Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            Toast.makeText(this, "数据格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
