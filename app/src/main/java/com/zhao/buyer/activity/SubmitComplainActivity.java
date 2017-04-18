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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.R;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.Utility;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.itemmodel.Complain;
import com.zhao.buyer.presenter.FormPresenter;

import java.io.InputStream;

public class SubmitComplainActivity extends AppCompatActivity {
    private Complain complain;

    private TextView title;
    private Button back;


    private RadioGroup rg_reason;

    private EditText complainText;
    private Button submit;

    private String strReason;

    private String[] mReasonText={"商品质量问题","商品配送问题","其他原因"};

    private int[] mReasonId={R.id.complain_reason1,R.id.complain_reason2,R.id.complain_reason3};

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(SubmitComplainActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(SubmitComplainActivity.this,"error",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_complain);
        title = (TextView)findViewById(R.id.title_text);
        back = (Button)findViewById(R.id.title_back);
        rg_reason = (RadioGroup)findViewById(R.id.complain_reason);
        complainText = (EditText) findViewById(R.id.complain_text);
        submit = (Button)findViewById(R.id.complain_submit);
        complain = new Complain();
        for(int i = 0; i < mReasonId.length; i++){
            RadioButton rbtn =  (RadioButton)findViewById(mReasonId[i]);
            rbtn.setText(mReasonText[i]);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rg_reason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.complain_reason1){
                    strReason = mReasonText[0];
                }else  if(checkedId == R.id.complain_reason2){
                    strReason = mReasonText[1];
                }else  if(checkedId == R.id.complain_reason3){
                    strReason = mReasonText[2];
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    setComplain();
                    FormPresenter fp = new FormPresenter();
                    fp.submitComplain(complain, new HttpCallbackListener() {
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
                            Log.d("SubmitComplainActivity",e.toString());
                            Message msg = new Message();
                            msg.what = -1;
                            handler.sendMessage(msg);

                        }
                    });
                }
            }
        });
    }

    private boolean checkInput(){
        if(strReason == null) {
            Toast.makeText(SubmitComplainActivity.this,"请至少选择一个原因",Toast.LENGTH_SHORT).show();
            return false;
        }else if(complainText.getText().toString().equals("")){
            Toast.makeText(SubmitComplainActivity.this,"请填写详细原因",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setComplain(){
        complain.setBuyerAccount(APPCONST.ACCOUNT);
        complain.setFormId(getIntent().getLongExtra("formId",0));
        complain.setShopId(getIntent().getIntExtra("shopId",0));
        complain.setComplainState("WaitHandle");
        complain.setComplainText(strReason+": "+complainText.getText().toString());
        complain.setTime(Utility.getStringNowTime());
    }
}
