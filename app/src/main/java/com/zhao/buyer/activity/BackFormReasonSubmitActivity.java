package com.zhao.buyer.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.presenter.FormPresenter;

import java.io.InputStream;

public class BackFormReasonSubmitActivity extends BaseActivity {
    private long formId;

    private TextView title;
    private Button btn_back;

    private EditText text;
    private Button submit;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    finish();
                    break;

                case -1:
                    Toast.makeText(BackFormReasonSubmitActivity.this,"error",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_form_submit);
        title = (TextView)findViewById(R.id.title_text);
        btn_back = (Button)findViewById(R.id.title_back);

        text = (EditText)findViewById(R.id.back_form_reason_text);
        submit = (Button)findViewById(R.id.back_form_submit);
        title.setText("退单");
        formId = getIntent().getLongExtra("formId",0);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().equals("")){
                    Toast.makeText(BackFormReasonSubmitActivity.this,"退单理由不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    FormPresenter fp = new FormPresenter();
                    fp.backForm(formId, text.getText().toString(), new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            if(response.equals("success")){
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }else {
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
                            Message msg = new Message();
                            msg.what = -1;
                            handler.sendMessage(msg);

                        }
                    });
                }
            }
        });
    }
}
