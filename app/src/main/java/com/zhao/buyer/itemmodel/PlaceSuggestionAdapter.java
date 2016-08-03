package com.zhao.buyer.itemmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zhao.buyer.activity.CartActivity;
import com.zhao.buyer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2016/5/12.
 */
public class PlaceSuggestionAdapter extends ArrayAdapter<PlaceSuggestion> {

    private int resourceId;
    private Viewhandler viewhandler;
    private List<View> views = new ArrayList<View>();

    public PlaceSuggestionAdapter(Context context, int textViewResourceId,
                                  ArrayList<PlaceSuggestion> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final PlaceSuggestion placeSuggestion = getItem(position);
        View view;
        if (views.size() <= position) {
            //  Log.d("cartfoodlist", "position= " + position);
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            views.add(position, view);
            viewhandler = new Viewhandler();
            viewhandler.name = (TextView) view.findViewById(R.id.place_item_name);
            viewhandler.city = (TextView) view.findViewById(R.id.place_item_city);
            viewhandler.district = (TextView) view.findViewById(R.id.place_item_district);

            viewhandler.name.setText(placeSuggestion.getName());
            viewhandler.city.setText(placeSuggestion.getCity());
            viewhandler.district.setText(placeSuggestion.getDistrict());


            view.setTag(viewhandler);
        } else {

            view = views.get(position);
            viewhandler = (Viewhandler) view.getTag();
        }
        return view;
    }

    class Viewhandler {
        TextView name;
        TextView city;
        TextView district;
    }
}
