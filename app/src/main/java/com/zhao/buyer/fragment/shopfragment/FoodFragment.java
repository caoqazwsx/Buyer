package com.zhao.buyer.fragment.shopfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhao.buyer.activity.CartActivity;
import com.zhao.buyer.R;
import com.zhao.buyer.activity.ShopActivity;
import com.zhao.buyer.activity.SubmitFormActivity;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.itemmodel.Cart;
import com.zhao.buyer.itemmodel.Food;
import com.zhao.buyer.itemmodel.FoodItemAdapter;
import com.zhao.buyer.presenter.ShopPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class FoodFragment extends Fragment {
    private int shop_id;
    private double bot_price;
    private ArrayList<Food> fooditems;
    private FoodItemAdapter foodItemAdapter;
    private View rootView;
    private ListView listView;
    private ListView listViewSign;
    private ProgressBar progressBar;
    private Button btn_show_cart;
    private Button btn_submit;
    private TextView total_price;
    private Cart cart;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setVisibility(View.GONE);
                    foodItemAdapter = new FoodItemAdapter(getContext(), R.layout.listview_item_food, fooditems);
                    listView.setAdapter(foodItemAdapter);
                    initLVSign();
                    Log.d("FoodFragment", "success init");
                    break;
                case -1:
                    Toast.makeText(getActivity(), "请检查你网络", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.d("FoodFragment", "error init");
                    break;
                case -2:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "本店没有菜单", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fooditems = new ArrayList<Food>();
        //init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            Log.d("FoodFragment", "rootview == null");
            View view = inflater.inflate(R.layout.fragment_food, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_foodlist);
            listView = (ListView) view.findViewById(R.id.list_foods);
            listViewSign = (ListView)view.findViewById(R.id.list_sign);
            btn_show_cart = (Button) view.findViewById(R.id.show_cart);
            btn_submit = (Button) view.findViewById(R.id.submit);
            total_price = (TextView)view.findViewById(R.id.total_price);
            listViewSign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listView.setSelection(position);
                }
            });
            btn_show_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent it = new Intent(getActivity(), CartActivity.class);
                    it.putExtra("bot_price",bot_price);
                    getActivity().startActivityForResult(it,1);

                }
            });
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(getActivity(),SubmitFormActivity.class);
                    ShopActivity sa = (ShopActivity)getActivity();
                    it.putExtra("shopName",sa.getShop_name());
                    getActivity().startActivity(it);
                }
            });
            progressBar.setVisibility(View.VISIBLE);
            rootView = view;
        } else {
            Log.d("FoodFragment", "rootview != null");
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                Log.d("FoodFragment", "parent != null");
                parent.removeView(rootView);
            }
        }
        init();
        return rootView;
    }


    //初始化菜单
    public void init() {
        listView.setAdapter(null);
        fooditems.clear();
        ShopPresenter sp = new ShopPresenter();
        ShopActivity sa = (ShopActivity) getActivity();
        shop_id = sa.getShopId();
        bot_price = sa.getBotPrice();
        cart = Cart.getCart();
        sp.getFoodList(sa.getShopId(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                try {
                    JSONArray res = new JSONArray(response);
                    if (res.length() != 0) {
                        Log.d("Foodlist", "get Data length:" + res.length());
                        for (int i = 0; i < res.length(); i++) {
                            JSONObject jb = res.getJSONObject(i);
                            Log.d("Foodlist Data:", jb.toString());
                            fooditems.add(new Gson().fromJson(jb.toString(),Food.class));
                        }
                        Log.d("Foodlist", "fooditems add success!");
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Log.d("Foodlist", e.toString());
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
                Log.d("foodlist", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }
        });
        checkCart();
    }

    private void initLVSign(){
        if(listView.getCount() < 1){
            return;
        }
        ArrayList<String> sign = new ArrayList<String>();
        for(int i = 0; i < listView.getCount(); i ++){
           sign.add(i+"");
        }
        listViewSign.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,sign));
    }

    public void checkCart(){
        cart = Cart.getCart();
        if(shop_id == cart.getShop_id()) {
            if (!cart.isEmpty()) {
                btn_show_cart.setClickable(true);
                btn_show_cart.setBackgroundResource(R.drawable.uu);
                if(cart.getTotal_price() < bot_price){
                    btn_submit.setClickable(false);
                    btn_submit.setBackgroundResource(R.color.transparent);
                    total_price.setText("还差 ￥" + (bot_price - cart.getTotal_price())+"起送");
                }else {
                    btn_submit.setClickable(true);
                    btn_submit.setBackgroundResource(R.drawable.pv);
                    total_price.setText("实付：￥" + cart.getPayPrice());
                }
            } else {
                btn_show_cart.setClickable(false);
                btn_submit.setClickable(false);
                btn_submit.setBackgroundResource(R.color.transparent);
                btn_show_cart.setBackgroundResource(R.drawable.ut);
                total_price.setText("起送￥"+ bot_price);
            }
        }else{
            cart.clear();
            btn_submit.setClickable(false);
            btn_submit.setBackgroundResource(R.color.transparent);
            btn_show_cart.setClickable(false);
            btn_show_cart.setBackgroundResource(R.drawable.ut);
            total_price.setText("实付：");
        }
    }

    public void updateListView(){
        foodItemAdapter.updateFoodNum();
        checkCart();
    }

   /* @Override
    public void onResume() {

        if(!isHidden()){
            init();
        }
        super.onResume();

    }*/

}