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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.Utility;
import com.zhao.buyer.itemmodel.Cart;
import com.zhao.buyer.itemmodel.FormFoodItemAdapter;
import com.zhao.buyer.presenter.SubmitFormPresenter;

import org.json.JSONObject;

import java.io.InputStream;

public class SubmitFormActivity extends BaseActivity {
    private ListView formFoodListView;
    private TextView text_title;
    private TextView total_price;
    private TextView address;
    private TextView contact;
    private TextView telephone;
    private String addressLocation;

    private TextView sendTime;
    private TextView shop_name;
    private TextView pay_price;

    private EditText note;
    private Button btn_back;
    private Button btn_submit;
    private RadioGroup radioGroup;
    private String pay_method;
    //private String formId;
    private String formState;

    private LinearLayout ll_address;
    private LinearLayout ll_sendTime;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(pay_method.equals("Online")) {
                        Intent it = new Intent(SubmitFormActivity.this, PayActivity.class);
                        it.putExtra("pay_price", Cart.getCart().getTotal_price());
                        it.putExtra("form_id", (Long) msg.obj);
                        startActivity(it);
                        Cart.getCart().clear();
                        finish();
                    }else if(pay_method.equals("Cash")){
                        Toast.makeText(SubmitFormActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(SubmitFormActivity.this, HomeActivity.class);
                        startActivity(it);
                        Cart.getCart().clear();
                    }
                    break;
                case 2:
                    JSONObject deAddress = (JSONObject)msg.obj;
                    try{
                        contact.setText(deAddress.getString("contact"));
                        telephone.setText(deAddress.getString("telephone"));
                        address.setText(deAddress.getString("address")+" "+deAddress.getString("no"));
                        addressLocation = deAddress.getString("location");
                    }catch (Exception e){
                        Log.d("SubmitForm2",e.toString());
                    }
                    break;
                case -1:
                    Toast.makeText(SubmitFormActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_form);
        formFoodListView = (ListView)findViewById(R.id.list_form_food);
        text_title  = (TextView)findViewById(R.id.title_text);
        total_price  = (TextView)findViewById(R.id.form_total_price);
        address  = (TextView)findViewById(R.id.address);
        contact = (TextView)findViewById(R.id.contact);
        telephone = (TextView)findViewById(R.id.telephone);
        sendTime = (TextView)findViewById(R.id.send_time);
        shop_name  = (TextView)findViewById(R.id.form_shop_name);
        pay_price = (TextView)findViewById(R.id.pay_price);
        btn_back = (Button)findViewById(R.id.title_back);
        btn_submit = (Button)findViewById(R.id.form_submit);
        note = (EditText)findViewById(R.id.form_note);
        radioGroup = (RadioGroup)findViewById(R.id.pay_method);
        ll_address = (LinearLayout)findViewById(R.id.submit_deliveryAddress);
        ll_sendTime = (LinearLayout)findViewById(R.id.submit_layout_sendTime);
        pay_method = "Online";
        shop_name.setText(getIntent().getStringExtra("shopName"));
        text_title.setText("提交订单");
        total_price.setText("￥"+Cart.getCart().getPayPrice()+"");
        pay_price.setText("待付款￥"+Cart.getCart().getPayPrice());
        FormFoodItemAdapter formFoodItemAdapter = new FormFoodItemAdapter(this,R.layout.listview_item_form_food, Cart.getCart().getCart_food_list());
        formFoodListView.setAdapter(formFoodItemAdapter);
        Utility.setListViewHeightBasedOnChildren(formFoodListView);
        formState = "WaitPay";

        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SubmitFormActivity.this, MyAddressActivity.class);
                it.putExtra("state","submit");
                startActivityForResult(it, 1);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.online) {
                    pay_method = "Online";
                    formState = "WaitPay";
                } else if (checkedId == R.id.cash) {
                    pay_method = "Cash";
                    formState = "WaitAccept";
                }
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
                if(APPCONST.LOGIN_STATE) {
                    if(addressLocation == null){
                        Toast.makeText(SubmitFormActivity.this,"请设置收货地址",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Cart cart = Cart.getCart();
                    SubmitFormPresenter sfp = new SubmitFormPresenter();
                    sfp.submit(Cart.getCart().getPayPrice(),formState,cart.getShop_id(), shop_name.getText().toString(), telephone.getText().toString(),
                            contact.getText().toString(), address.getText().toString(), pay_method, sendTime.getText().toString(),note.getText().toString(),
                            addressLocation, new HttpCallbackListener() {
                                @Override
                                public void onFinish(String response) {
                                    if(!response.contains("error")){
                                        Message msg = new Message();
                                        msg.what = 1;
                                        msg.obj = Long.parseLong(response);
                                        handler.sendMessage(msg);
                                    }
                                    else {
                                        Message msg = new Message();
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
                                    Message msg = new Message();
                                    msg.what = -1;
                                    handler.sendMessage(msg);

                                }
                            });
                }else {
                    Intent it = new Intent(SubmitFormActivity.this, LoginActivity.class);
                    startActivity(it);
                }
            }
        });
        init();
    }

    private void init(){
        SubmitFormPresenter sfp = new SubmitFormPresenter();
        sfp.getAddress(new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if(!response.equals("")){
                    try{
                        JSONObject jo = new JSONObject(response);
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = jo;
                        handler.sendMessage(msg);
                    }catch (Exception e){
                        Log.d("SubmitForm",e.toString());
                        Message msg = new Message();
                        msg.what = -2;
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
                Log.d("SubmitForm",e.toString());
                Message msg = new Message();
                msg.what = -2;
                handler.sendMessage(msg);

            }
        });

    }


    @Override
    protected void onDestroy() {
        String str = getIntent().getStringExtra(APPCONST.CLEAR_CART);
        if(str != null&&str.equals(APPCONST.CLEAR_CART)){
            Cart.getCart().clear();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    contact.setText(data.getStringExtra("contact"));
                    telephone.setText(data.getStringExtra("telephone"));
                    address.setText(data.getStringExtra("address"));
                    addressLocation = data.getStringExtra("addressLocation");
                }
                break;
            default:
        }
    }




}
