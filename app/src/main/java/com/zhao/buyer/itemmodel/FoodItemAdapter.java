package com.zhao.buyer.itemmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.R;
import com.zhao.buyer.activity.ShopActivity;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.fragment.shopfragment.FoodFragment;
import com.zhao.buyer.presenter.ShopPresenter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2016/4/26.
 */
public class FoodItemAdapter extends ArrayAdapter<Food> {
    private int currentUpdatePicturePos;
    private int resourceId;
    private Viewhandler viewhandler;
    private List<View> views = new ArrayList<View>();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bitmap bm = (Bitmap)msg.obj;
                    int pos = msg.arg1;
                //    Log.d("FoodListItem", "updpicture: " + pos);
                    ImageView imageView = (ImageView)views.get(pos).findViewById(R.id.food_icon);
                    imageView.setImageBitmap(bm);
                    break;
                case -1:
                    Log.d("FoodListItem handler", "error:"+ msg.arg1);
                    break;

            }
        }
    };
    public FoodItemAdapter(Context context, int textViewResourceId,
                           ArrayList<Food> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        initAllView();
    }

    private void initAllView(){
        for(int position = 0; position < getCount(); position++) {
            final Food foodItem = getItem(position);
            View view;
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            views.add(position, view);
            // Log.d("ShopListItem", "" + (position - start) + "  size =" + views.size());
            viewhandler = new Viewhandler();
            viewhandler.food_name = (TextView) view.findViewById(R.id.food_name);
            viewhandler.market_price = (TextView) view.findViewById(R.id.market_price);
            viewhandler.food_icon = (ImageView) view.findViewById(R.id.food_icon);
            viewhandler.sales = (TextView) view.findViewById(R.id.food_sales);
            viewhandler.price = (TextView) view.findViewById(R.id.price);
            viewhandler.note = (TextView) view.findViewById(R.id.note);
            viewhandler.add = (Button) view.findViewById(R.id.add_food);
            viewhandler.reduce = (Button) view.findViewById(R.id.reduce_food);
            viewhandler.food_num = (TextView) view.findViewById(R.id.food_num);
            viewhandler.nofood = (TextView) view.findViewById(R.id.food_item_nofood);
            init(viewhandler, position);
            view.setTag(viewhandler);
            viewhandler = (Viewhandler) view.getTag();
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        return views.get(position);
    }

    private void init(Viewhandler viewhandler,int pos){
        final Food foodItem = getItem(pos);
        final int position = pos;
        Cart cart = Cart.getCart();
        viewhandler.food_name.setText(foodItem.getFoodName());
        viewhandler.price.setText("￥"+foodItem.getPrice()+"/份");
        updatePicture(foodItem.getFoodIcon(), position);
        viewhandler.sales.setText("销售量：" + foodItem.getFoodSales());
        viewhandler.market_price.setText("门市价" + foodItem.getMarketPrice());
        viewhandler.note.setText( foodItem.getNote());
        if(!cart.isEmpty()){
            if (cart.isFoodExist(foodItem) != -1) {
                viewhandler.reduce.setVisibility(View.VISIBLE);
                viewhandler.food_num.setVisibility(View.VISIBLE);
                viewhandler.food_num.setText("" + cart.getCart_food_list().get(cart.isFoodExist(foodItem)).getFoodNum());
            }
        }
        viewhandler.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = Cart.getCart();
                Viewhandler vh = (Viewhandler) views.get(position).getTag();
                if (cart.isFoodExist(foodItem) == -1) {
                    vh.reduce.setVisibility(View.VISIBLE);
                    vh.food_num.setVisibility(View.VISIBLE);
                }
                else {
                    if (foodItem.getRemain() - cart.getCart_food_list().get(cart.isFoodExist(foodItem)).getFoodNum() == 0) {
                        Toast.makeText(getContext(), "此商品不能再继续添加，剩余数量不足", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                cart.add(foodItem);
                vh.food_num.setText("" + cart.getCart_food_list().get(cart.isFoodExist(foodItem)).getFoodNum());
                ShopActivity sa = (ShopActivity) getContext();
                FoodFragment foodFragment = (FoodFragment) sa.getFragment(0);
                foodFragment.checkCart();
            }
        });

        viewhandler.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = Cart.getCart();
                cart.reduce(foodItem);
                Viewhandler vh = (Viewhandler) views.get(position).getTag();
                if (cart.isFoodExist(foodItem) == -1) {
                    vh.reduce.setVisibility(View.INVISIBLE);
                    vh.food_num.setVisibility(View.INVISIBLE);
                } else {
                    vh.food_num.setText("" + cart.getCart_food_list().get(cart.isFoodExist(foodItem)).getFoodNum());
                }
                ShopActivity sa = (ShopActivity) getContext();
                FoodFragment foodFragment = (FoodFragment) sa.getFragment(0);
                foodFragment.checkCart();
            }
        });
        ShopActivity sa = (ShopActivity)getContext();
        if(sa.getState().equals("rest")){
            viewhandler.add.setVisibility(View.GONE);
            viewhandler.reduce.setVisibility(View.GONE);
        }
        if(foodItem.getRemain() <= 0){
            viewhandler.add.setVisibility(View.GONE);
            viewhandler.nofood.setVisibility(View.VISIBLE);
        }else {
            viewhandler.add.setVisibility(View.VISIBLE);
            viewhandler.nofood.setVisibility(View.GONE);
        }
    }

    private void updatePicture(final String path, final int position) {

        ShopPresenter sp = new ShopPresenter();
        sp.getPicture(path, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

            }

            @Override
            public void onFinish(InputStream in) {

            }

            @Override
            public void onFinish(Bitmap bm) {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = position;
                msg.obj = bm;
                handler.sendMessage(msg);

            }

            @Override
            public void onError(Exception e) {
                Log.d("FoodListItem", e.toString());
                Message msg = new Message();
                msg.what = -1;
                msg.arg1 = position;
                msg.obj = e.toString();
                handler.sendMessage(msg);
            }
        });

    }

    public void updateFoodNum() {
        Cart cart = Cart.getCart();
        int tem = getCount();
        for (int i = 0; i < getCount(); i++) {
            Viewhandler viewhandler = (Viewhandler) views.get(i).getTag();
            if (cart.isFoodExist(getItem(i)) != -1) {
                int curFoodNum = cart.getCart_food_list().get(cart.isFoodExist(getItem(i))).getFoodNum();
                viewhandler.reduce.setVisibility(View.VISIBLE);
                viewhandler.food_num.setVisibility(View.VISIBLE);
                viewhandler.food_num.setText("" + curFoodNum);
            } else {
                viewhandler.reduce.setVisibility(View.INVISIBLE);
                viewhandler.food_num.setVisibility(View.INVISIBLE);
                viewhandler.food_num.setVisibility(View.INVISIBLE);
            }
        }
    }

    class Viewhandler{
        TextView food_name;
        ImageView food_icon;
        TextView sales;
        TextView market_price;
        TextView price;
        TextView note;
        TextView food_num;
        TextView nofood;
        Button add;
        Button reduce;
    }
}


