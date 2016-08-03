package com.zhao.buyer.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhao.buyer.R;
import com.zhao.buyer.custom.RadioGrade;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.itemmodel.Form;
import com.zhao.buyer.presenter.CommentPresenter;
import com.zhao.buyer.presenter.FormPresenter;

import java.io.InputStream;

public class SubmitCommentActivity extends BaseActivity {
    private long formId;


    private TextView titleText;
    private Button btn_back;

    private TextView shopName;
    private RadioGrade radioGrade_shop;
    private RadioGrade radioGrade_send;

//    private ImageButton btn_grade1;
//    private ImageButton btn_grade2;
//    private ImageButton btn_grade3;
//    private ImageButton btn_grade4;
//    private ImageButton btn_grade5;
    private int send_grade;
    private int shop_grade;

    private EditText text;

    private Button btn_submit;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                   final Form form = (Form) msg.obj;
                    init(form);
                    break;
                case 2:
                    Toast.makeText(SubmitCommentActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(SubmitCommentActivity.this, "error", Toast.LENGTH_SHORT).show();
                    Log.d("CommentActivity", "error init");
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_comment);
        formId = getIntent().getLongExtra("formId", 0);
        titleText = (TextView) findViewById(R.id.title_text);
        btn_back = (Button) findViewById(R.id.title_back);
        radioGrade_shop = (RadioGrade)findViewById(R.id.comment_item_radioGrade_shop) ;
        radioGrade_send = (RadioGrade)findViewById(R.id.comment_item_radioGrade_send) ;
        shopName = (TextView)findViewById(R.id.comment_shopName);
        shop_grade = 0;
        send_grade = 0;
        radioGrade_shop.setOnChangeGradeListener(new RadioGrade.OnChangeGradeListener() {
            @Override
            public void onChange(int grade) {
                if(grade!=0){
                    shop_grade = grade;
                    if(shop_grade*send_grade!=0) {
                        btn_submit.setClickable(true);
                        btn_submit.setBackgroundResource(R.drawable.pv);
                    }
                }
            }
        });
        radioGrade_send.setOnChangeGradeListener(new RadioGrade.OnChangeGradeListener() {
            @Override
            public void onChange(int grade) {
                if(grade!=0){
                    send_grade = grade;
                    if(shop_grade*send_grade!=0) {
                        btn_submit.setClickable(true);
                        btn_submit.setBackgroundResource(R.drawable.pv);
                    }
                }
            }
        });



        text = (EditText) findViewById(R.id.comment_text);
        btn_submit = (Button) findViewById(R.id.comment_submit);
        getFormInfo();
    }

    private void init(final Form form){

        titleText.setText("评价");
        shopName.setText(form.getShopName());
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentPresenter cp = new CommentPresenter();
                cp.submitComment(form.getId(),form.getShopId(), form.getFormFood(),shop_grade,send_grade,text.getText().toString(),
                        new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if(response.equals("success")){
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }else{
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
                        Log.d("SubmitComment",e.toString());
                        Message msg = new Message();
                        msg.what = -1;
                        handler.sendMessage(msg);

                    }
                });

            }
        });

    }


    private void getFormInfo() {
        FormPresenter fp = new FormPresenter();
        fp.getFormInfo(formId, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    if (response != null) {
                        Log.d("FormInfo Data2.", response);
                        Form form = new Gson().fromJson(response, Form.class);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = form;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = -1;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Log.d("FormInfoActivity", e.toString());
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
                Log.d("FormInfoActivity", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }
        });

    }
}

