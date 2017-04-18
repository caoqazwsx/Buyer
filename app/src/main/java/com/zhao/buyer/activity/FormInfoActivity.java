package com.zhao.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.Utility;
import com.zhao.buyer.itemmodel.Cart;
import com.zhao.buyer.itemmodel.CartFoodItem;
import com.zhao.buyer.itemmodel.Form;
import com.zhao.buyer.itemmodel.FormFoodItemAdapter;
import com.zhao.buyer.presenter.FormPresenter;
import com.zhao.buyer.tablemodel.SenderInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class FormInfoActivity extends BaseActivity {
    private long formId;
    private Form form;
    private SenderInfo senderInfo;


    private TextView titleText;
    private Button btn_back;

    private TextView submit;
    private TextView accept;
    private TextView arrived;
    private TextView waiPay;

    private ListView foodListView;
    private TextView shopName;
    private TextView totalPrice;

    private TextView sendState;
    private TextView sender;
    private TextView contact;
    private TextView telephone;
    private TextView address;
    private TextView payMethod;
    private TextView submitTime;
    private TextView serviceTime;
    private TextView note;

    private Button btn_pay;
    private Button btn_again;
    private Button btn_confirm;
    private Button btn_goComment;
    private Button btn_cancel;
    private Button btn_complain;

    private  ArrayList<CartFoodItem> cartFoodItems;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    form = (Form)msg.obj;
                    initData();
                    break;
                case 2:
                    initFormInfo();
                    break;
                case 3:
                    if(senderInfo != null){
                        sender.setText("骑手："+senderInfo.getName()+" 联系电话："+senderInfo.getTelephone());
                    }
                    break;
                case -1:
                    Toast.makeText(FormInfoActivity.this,"error",Toast.LENGTH_SHORT).show();
                    Log.d("FormInfoActivity","error init");
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_info);
        formId = getIntent().getLongExtra("formId", 0);
        initView();
        initFormInfo();
    }

    private void initView(){
        titleText = (TextView) findViewById(R.id.title_text);
        btn_back = (Button) findViewById(R.id.title_back);

        submit = (TextView) findViewById(R.id.form_info_submit);
        accept = (TextView) findViewById(R.id.form_info_accept);
        arrived = (TextView) findViewById(R.id.form_info_arrived);
        waiPay = (TextView) findViewById(R.id.form_info_waiPay);

        foodListView = (ListView) findViewById(R.id.form_info_list_food);
        shopName = (TextView) findViewById(R.id.form_info_shop_name);
        totalPrice = (TextView) findViewById(R.id.form_info_price);

        sendState = (TextView) findViewById(R.id.form_info_sendState);
        sender = (TextView) findViewById(R.id.form_info_sender);
        contact = (TextView) findViewById(R.id.form_info_contact);
        telephone = (TextView) findViewById(R.id.form_info_telphone);
        address = (TextView) findViewById(R.id.form_info_address);
        payMethod = (TextView) findViewById(R.id.form_info_payMethod);
        submitTime = (TextView) findViewById(R.id.form_info_submitTime);
        serviceTime = (TextView) findViewById(R.id.form_info_serviceTime);
        note = (TextView) findViewById(R.id.form_info_note);

        btn_pay = (Button) findViewById(R.id.form_info_pay);
        btn_again = (Button) findViewById(R.id.form_info_again);
        btn_confirm = (Button) findViewById(R.id.form_info_confirm);
        btn_goComment = (Button) findViewById(R.id.form_info_goComment);
        btn_cancel = (Button) findViewById(R.id.form_info_cancel);
        btn_complain = (Button)findViewById(R.id.form_info_complain);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_pay .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FormInfoActivity.this,PayActivity.class);
                it.putExtra("pay_price", form.getPayPrice());
                it.putExtra("form_id", form.getId());
                Log.d("forms_item_pay",""+form.getPayPrice()+"  "+form.getId());
                startActivity(it);

            }
        });
        btn_again .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.getCart().clear();
                Cart.getCart().add(cartFoodItems);
                Cart.getCart().setShop_id(form.getShopId());
                Intent it = new Intent(FormInfoActivity.this, SubmitFormActivity.class);
                it.putExtra("shopName",form.getShopName());
                it.putExtra(APPCONST.CLEAR_CART, APPCONST.CLEAR_CART);
                startActivity(it);
            }
        });
        btn_confirm .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormPresenter fp = new FormPresenter();
                fp.confirmForm(form.getId(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if(response.equals("success")) {
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
                        Log.d("FormFrament",e.toString());
                        Message msg = new Message();
                        msg.what = -1;
                        handler.sendMessage(msg);
                    }
                });

            }
        });
        btn_goComment .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FormInfoActivity.this, SubmitCommentActivity.class);
                it.putExtra("formId",form.getId());
                startActivity(it);

            }
        });
        btn_cancel .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormPresenter fp = new FormPresenter();
                fp.cancelForm(form.getId(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if(response.equals("success")) {
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
                        Log.d("FormFrament",e.toString());
                        Message msg = new Message();
                        msg.what = -1;
                        handler.sendMessage(msg);
                    }
                });

            }
        });
        btn_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FormInfoActivity.this,SubmitComplainActivity.class);
                it.putExtra("formId",formId);
                it.putExtra("shopId",form.getShopId());
                startActivity(it);

            }
        });

    }

    private void initData(){
        titleText.setText(form.getShopName());
        cartFoodItems = getFoodList(form.getFormFood());
        FormFoodItemAdapter formFoodItemAdapter = new  FormFoodItemAdapter(this, R.layout.listview_item_form_food, cartFoodItems);
        foodListView.setAdapter(formFoodItemAdapter);
        Utility.setListViewHeightBasedOnChildren(foodListView);
        shopName.setText(form.getShopName());
        totalPrice.setText("￥"+form.getPayPrice()+"");

        if(form.getSendState().equals("ON")){
            sendState.setText("配送状态：商家已标记配送");
            if(!form.getSenderAccount().equals("")){
                getSenderInfo(form.getSenderAccount());
            }
        } else {
            sendState.setText("配送状态：商家未标记配送");
        }
        contact.setText("联系人："+form.getContact());
        telephone.setText("联系电话："+form.getTelephone());
        address.setText("收货地址："+form.getFormAddress());
        payMethod.setText("支付方式："+form.getPayMethod());
        submitTime.setText("提交时间："+form.getSubmitTime());
        serviceTime.setText("送达时间："+form.getSendTime());
        note.setText("备注："+form.getNote());

        checkFormState(form.getFormState());

    }

    private void initFormInfo(){
        FormPresenter fp = new FormPresenter();
        fp.getFormInfo(formId, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    if(response != null){
                        Log.d("FormInfo Data2.",response);
                        Form form = new Gson().fromJson(response,Form.class);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = form;
                        handler.sendMessage(msg);
                    }else{
                        Message msg = new Message();
                        msg.what = -1;
                        handler.sendMessage(msg);
                    }
                }catch (Exception e){
                    Log.d("FormInfoActivity",e.toString());
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
                Log.d("FormInfoActivity",e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }
        });

    }

    private void getSenderInfo(String senderAccount){
        FormPresenter fp = new FormPresenter();
        fp.getSenderInfo(senderAccount, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try{
                    JSONArray ja = new JSONArray(response);
                    if(ja.length() == 1) {
                        senderInfo = new Gson().fromJson(ja.getString(0), SenderInfo.class);
                        Message msg = new Message();
                        msg.what = 3;
                        handler.sendMessage(msg);
                    }

                }catch (Exception e){
                    Log.d("FromInfo",e.toString());
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
                Log.d("FromInfo",e.toString());

            }
        });
    }

    private void checkFormState(String state){
        if( state.equals(APPCONST.WAIT_PAY)){
            waiPay.setText("待付款");
            changeStateTextView(waiPay);
            recoverStateTextView(accept);
            recoverStateTextView(arrived);
            recoverStateTextView(submit);
            btn_pay.setVisibility(View.VISIBLE);
            btn_confirm.setVisibility(View.GONE);
            btn_goComment.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.VISIBLE);
        }else if(state.equals(APPCONST.WAIT_ARRIVED)){
            recoverStateTextView(waiPay);
            changeStateTextView(accept);
            recoverStateTextView(arrived);
            recoverStateTextView(submit);
            btn_pay.setVisibility(View.GONE);
            btn_confirm.setVisibility(View.VISIBLE);
            btn_goComment.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        }else if(state.equals(APPCONST.WAIT_ACCEPT)){
            recoverStateTextView(waiPay);
            changeStateTextView(submit);
            recoverStateTextView(accept);
            recoverStateTextView(arrived);
            btn_pay.setVisibility(View.GONE);
            btn_confirm.setVisibility(View.GONE);
            btn_goComment.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.VISIBLE);
        }else if(state.equals(APPCONST.WAIT_COMMENT)){
            recoverStateTextView(waiPay);
            recoverStateTextView(accept);
            changeStateTextView(arrived);
            recoverStateTextView(submit);
            btn_pay.setVisibility(View.GONE);
            btn_confirm.setVisibility(View.GONE);
            btn_goComment.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.GONE);
        }else if(state.equals(APPCONST.FINISH)){
            recoverStateTextView(waiPay);
            recoverStateTextView(accept);
            changeStateTextView(arrived);
            recoverStateTextView(submit);
            btn_pay.setVisibility(View.GONE);
            btn_confirm.setVisibility(View.GONE);
            btn_goComment.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        }else if(state.equals(APPCONST.CANCEL)){
           waiPay.setVisibility(View.GONE);
           submit.setVisibility(View.GONE);
           arrived.setVisibility(View.GONE);
            accept.setText("订单已取消");
            accept.setTextSize(20);
            btn_pay.setVisibility(View.GONE);
            btn_confirm.setVisibility(View.GONE);
            btn_goComment.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        }else if(state.equals(APPCONST.WAIT_BACK)){
            waiPay.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            arrived.setVisibility(View.GONE);
            accept.setText("等待退单确认");
            accept.setTextSize(20);
            btn_pay.setVisibility(View.GONE);
            btn_confirm.setVisibility(View.GONE);
            btn_goComment.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        }
    }

    private void changeStateTextView(TextView tv){
        tv.setTextColor(getResources().getColor(R.color.red));
        tv.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void recoverStateTextView(TextView tv){
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTypeface(Typeface.DEFAULT);

    }

    private ArrayList<CartFoodItem> getFoodList(String formFood){
        ArrayList<CartFoodItem> cartFoodItems = new ArrayList<CartFoodItem>();
        try {
            JSONArray ja = new JSONArray(formFood);
            for(int i =0 ; i<ja.length();i++) {
                JSONObject jb = ja.getJSONObject(i);
                cartFoodItems.add(new CartFoodItem(jb.getInt("foodId"), jb.getString("foodName"), jb.getInt("foodNum"),
                        jb.getDouble("foodPrice"), jb.getDouble("foodTotalPrice")));
            }
        }catch (Exception e){
            Log.d("FormInfoActivty",e.toString());
        }
        return cartFoodItems;
    }

}
