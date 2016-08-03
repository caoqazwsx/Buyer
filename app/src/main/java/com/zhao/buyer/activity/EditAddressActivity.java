package com.zhao.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.presenter.MinePresenter;

import java.io.InputStream;

public class EditAddressActivity extends BaseActivity {
    private String state;
    private int editId;

    private EditText contact;
    private EditText telephone;
    private TextView address;
    private EditText no;

    private Button btn_back;
    private Button btn_submit;

    private LinearLayout btn_location;

    private double lat;
    private double lng;

    private TextView title;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(EditAddressActivity.this,"操作成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(EditAddressActivity.this,"操作失败",Toast.LENGTH_SHORT).show();
                    Log.d("AddMyAddress","error init");
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_address);
        state = getIntent().getStringExtra("state");
        contact = (EditText)findViewById(R.id.editAddress_contact);
        telephone = (EditText)findViewById(R.id.editAddress_telephone);
        address = (TextView)findViewById(R.id.editAddress_address);
        no = (EditText)findViewById(R.id.editAddress_no);
        btn_back = (Button)findViewById(R.id.title_back);
        btn_submit = (Button)findViewById(R.id.editAddress_submit);
        btn_location = (LinearLayout)findViewById(R.id.editAddress_location);
        title = (TextView)findViewById(R.id.title_text);
        if(state.equals("edit")){
            title.setText("编辑收货地址");
            editId = getIntent().getIntExtra("id",0);
            contact.setText(getIntent().getStringExtra("contact"));
            telephone.setText(getIntent().getStringExtra("telephone"));
            address.setText(getIntent().getStringExtra("address"));
            no.setText(getIntent().getStringExtra("no"));
        }else{
            title.setText("新增收货地址");
        }

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EditAddressActivity.this,LocationActivity.class);
                startActivityForResult(it,1);
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contact.getText().toString().equals("")||telephone.getText().toString().equals("")||address.getText().toString().equals("")
                ||no.getText().toString().equals("")){
                    Toast.makeText(EditAddressActivity.this,"内容不完整",Toast.LENGTH_SHORT).show();
                }else{
                    MinePresenter mp = new MinePresenter();
                    if(state.equals("edit")){
                        mp.editAddress(editId,contact.getText().toString(), telephone.getText().toString(), address.getText().toString(),
                                no.getText().toString(),lng,lat, new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(String response) {
                                        if(response.equals("success")){
                                            Message msg = new Message();
                                            msg.what = 1;
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
                                        Log.d("EditAddressActivity",e.toString());
                                        Message msg = new Message();
                                        msg.what = -1;
                                        handler.sendMessage(msg);
                                    }
                                });
                    }else{
                        mp.addAddress(contact.getText().toString(), telephone.getText().toString(), address.getText().toString(),
                                no.getText().toString(),lng,lat,new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(String response) {
                                        if(response.equals("success")){
                                            Message msg = new Message();
                                            msg.what = 1;
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
                                        Log.d("EditAddressActivity",e.toString());
                                        Message msg = new Message();
                                        msg.what = -1;
                                        handler.sendMessage(msg);
                                    }
                                });
                    }

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    boolean state = data.getBooleanExtra("state",false);
                    if(state){
                        Log.d("Location",data.getDoubleExtra("lat",0)+"  "+getIntent().getDoubleExtra("lat",0));
                        lat = data.getDoubleExtra("lat",0);
                        lng = data.getDoubleExtra("lng",0);
                        address.setText(data.getStringExtra("name"));

                    }else{
                        Toast.makeText(EditAddressActivity.this, "获取位置失败",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
        }
    }


}
