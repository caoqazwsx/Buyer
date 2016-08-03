package com.zhao.buyer.itemmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.activity.CartActivity;
import com.zhao.buyer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2016/4/27.
 */
public class CartFoodItemAdapter extends ArrayAdapter<CartFoodItem> {

    private int resourceId;
    private Viewhandler viewhandler;
    private List<View> views = new ArrayList<View>();
    public CartFoodItemAdapter(Context context, int textViewResourceId,
                           ArrayList<CartFoodItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

       final CartFoodItem cartFoodItem = getItem(position);
        View view;
        if(views.size() <= position){
          //  Log.d("cartfoodlist", "position= " + position);
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            views.add(position,view);
            viewhandler = new Viewhandler();
            viewhandler.food_name = (TextView)view.findViewById(R.id.cart_food_name);
            viewhandler.total_price = (TextView)view.findViewById(R.id.cart_food_price);
            viewhandler.food_num = (TextView)view.findViewById(R.id.cart_food_num);
            viewhandler.reduce = (Button)view.findViewById(R.id.cart_btn_reduce);
            viewhandler.add = (Button)view.findViewById(R.id.cart_btn_add);
            viewhandler.food_name.setText(cartFoodItem.getFoodName());
            viewhandler.total_price.setText("￥" + cartFoodItem.getFoodTotalPrice());
            viewhandler.food_num.setText(""+cartFoodItem.getFoodNum());

            viewhandler.reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart = Cart.getCart();
                    CartActivity ca = (CartActivity)getContext();
                    cart.reduce(cartFoodItem);
//                    if(cart.reduce(cartFoodItem) != -1){
//                        Viewhandler vh = (Viewhandler) views.get(position).getTag();
//                        vh.total_price.setText("￥" + cartFoodItem.getFoodTotalPrice());;
//                        vh.food_num.setText("" + cartFoodItem.getFoodNum());
//                        //notifyDataSetChanged();
//                    }else{
//
//                        ca.updCartList();
//                    }
                    ca.checkCart();
                    ca.updCartList();
                    //notifyDataSetChanged();

                }
            });
            viewhandler.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart = Cart.getCart();
                    if(cartFoodItem.getFoodNum() < cartFoodItem.getRemain()) {
                        cart.add(cartFoodItem);
                        CartActivity ca = (CartActivity)getContext();
                        ca.checkCart();
                        ca.updCartList();
                    }else {
                        Toast.makeText(getContext(),"已达到限额",Toast.LENGTH_SHORT).show();
                    }

                    //Viewhandler vh = (Viewhandler) views.get(position).getTag();
//                    vh.total_price.setText("￥" + cartFoodItem.getFoodTotalPrice());
//                    vh.food_num.setText("" + cartFoodItem.getFoodNum());


                }
            });

            if(cartFoodItem.getFoodId() == 0){
                viewhandler.add.setVisibility(View.INVISIBLE);
                viewhandler.reduce.setVisibility(View.INVISIBLE);
            }

            view.setTag(viewhandler);
        }
        else{
          //  Log.d("cartfoodlist","positionfood= "+position);
            view = views.get(position);
            viewhandler = (Viewhandler)view.getTag();
        }
        return view;
    }
    class Viewhandler{
        TextView food_name;
        TextView total_price;
        TextView food_num;
        Button reduce;
        Button add;

    }
}
