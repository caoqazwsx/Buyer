package com.zhao.buyer.itemmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zhao.buyer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2016/4/28.
 */
public class FormFoodItemAdapter extends ArrayAdapter<CartFoodItem> {

    private int resourceId;
    private Viewhandler viewhandler;
    private List<View> views = new ArrayList<View>();

    public FormFoodItemAdapter(Context context, int textViewResourceId,
                               ArrayList<CartFoodItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CartFoodItem cartFoodItem = getItem(position);
        View view;
        if (views.size() <= position) {
            Log.d("formfoodlist", "position= " + position);
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            views.add(position, view);
            viewhandler = new Viewhandler();
            viewhandler.food_name = (TextView) view.findViewById(R.id.form_food_name);
            viewhandler.total_price = (TextView) view.findViewById(R.id.form_food_price);
            viewhandler.food_num = (TextView) view.findViewById(R.id.form_food_num);
            viewhandler.food_name.setText(cartFoodItem.getFoodName());
            viewhandler.total_price.setText("￥" + cartFoodItem.getFoodTotalPrice());
            viewhandler.food_num.setText("x" + cartFoodItem.getFoodNum());
            view.setTag(viewhandler);
        } else {
            Log.d("formfoodlist", "positionfood= " + position);
            view = views.get(position);
            viewhandler = (Viewhandler) view.getTag();
        }
        return view;
    }

    class Viewhandler {
        TextView food_name;
        TextView total_price;
        TextView food_num;


    }
}
