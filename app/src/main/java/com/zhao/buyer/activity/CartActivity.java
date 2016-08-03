package com.zhao.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.R;
import com.zhao.buyer.itemmodel.Cart;
import com.zhao.buyer.itemmodel.CartFoodItemAdapter;

public class CartActivity extends BaseActivity {

    private ListView cartlistview;
    private CartFoodItemAdapter cartFoodItemAdapter;
    private LinearLayout layout;
    private Button btn_show_cart;
    private Button btn_pay;
    private Button btn_clear;
    private TextView pay_price;
    private Cart cart;
    private Double bot_price;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartlistview = (ListView) findViewById(R.id.cart_food_list);
        btn_show_cart = (Button)findViewById(R.id.show_cart);
        btn_pay = (Button) findViewById(R.id.pay);
        btn_clear = (Button)findViewById(R.id.cart_btn_clear);
        pay_price = (TextView)findViewById(R.id.pay_price);
        cartFoodItemAdapter = new CartFoodItemAdapter(this,R.layout.listview_item_cart_food,Cart.getCart().getCart_food_list());
        cartlistview.setAdapter(cartFoodItemAdapter);
        layout = (LinearLayout) findViewById(R.id.cart_layout);
        bot_price = getIntent().getDoubleExtra("bot_price",0);
        btn_show_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CartActivity.this,SubmitFormActivity.class);
                startActivity(it);
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.getCart().clear();
                checkCart();
                updCartList();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        checkCart();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void updCartList(){
        cartFoodItemAdapter = new CartFoodItemAdapter(this,R.layout.listview_item_cart_food,Cart.getCart().getCart_food_list());
        cartlistview.setAdapter(cartFoodItemAdapter);
    }
    public void checkCart(){
        cart = Cart.getCart();
        if (!cart.isEmpty()){
            if(cart.getTotal_price() < bot_price){
                btn_pay.setClickable(false);
                btn_pay.setBackgroundResource(R.color.transparent);
                pay_price.setText("还差 ￥" + (bot_price - cart.getTotal_price())+"起送");
            }else {
                btn_pay.setClickable(true);
                btn_pay.setBackgroundResource(R.drawable.pv);
                pay_price.setText("实付：￥" + cart.getPayPrice());
            }
        }
        else{
          finish();
        }

    }

    @Override
    public void finish(){
       setResult(RESULT_OK);
        super.finish();
    }
}
