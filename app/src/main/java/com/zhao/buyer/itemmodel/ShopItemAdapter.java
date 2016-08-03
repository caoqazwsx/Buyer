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
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.presenter.ShopListPresenter;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhao on 2016/4/23.
 */
public class ShopItemAdapter extends ArrayAdapter<Shop> {
    private int currentUpdatePicturePos;
    private int resourceId;
    private Viewhandler viewhandler;
    private List<View> views = new ArrayList<View>();;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bitmap bm = (Bitmap)msg.obj;
                    int pos = msg.arg1;
                 //   Log.d("ShopListItem", "updpicture: "+pos);
                    ImageView imageView = (ImageView)views.get(pos).findViewById(R.id.shop_icon);
                    imageView.setImageBitmap(bm);
                    currentUpdatePicturePos ++;
                    break;
                case -1:
                    Log.d("ShopListItem handler", "error:"+ msg.arg1);
                    break;

            }
        }
    };
    public ShopItemAdapter(Context context, int textViewResourceId,
                       ArrayList<Shop> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        currentUpdatePicturePos = 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Shop shopItem = getItem(position);
        View view;

        if(views.size() <= position){
            Log.d("shoplist","position= "+position);
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            views.add(position,view);
           // Log.d("ShopListItem", "" + (position - start) + "  size =" + views.size());
            viewhandler = new Viewhandler();
            viewhandler.shop_name = (TextView)view.findViewById(R.id.shop_name);
            viewhandler.bot_price = (TextView)view.findViewById(R.id.bot_price);
            viewhandler.shop_icon = (ImageView)view.findViewById(R.id.shop_icon);
            viewhandler.sales = (TextView)view.findViewById(R.id.sales);
            viewhandler.delivery_times = (TextView)view.findViewById(R.id.delivery_times);
            viewhandler.special_offer = (TextView)view.findViewById(R.id.special_offer);
            viewhandler.discount = (TextView)view.findViewById(R.id.discount);
            viewhandler.distance = (TextView)view.findViewById(R.id.distance);
            viewhandler.shop_name.setText(shopItem.getShopName());
            viewhandler.bot_price.setText("起送价：" + shopItem.getBotPrice());
            viewhandler.distance.setText(shopItem.getDistance()+"m");
            //viewhandler.shop_icon.setImageResource(R.drawable.kb);
            updatePicture(shopItem.getShopIcon(), position);
            viewhandler.sales.setText("销售量：" + shopItem.getSales());
            viewhandler.delivery_times.setText(shopItem.getDeliveryTimes() + "分钟");
            if(shopItem.getDiscount().equals("")){
                viewhandler.discount.setVisibility(View.GONE);
            }else {
                viewhandler.discount.setText(createDiscountText(shopItem.getDiscount()));
            }
            viewhandler.special_offer.setText("惠:"+shopItem.getSpecialOffer());
            view.setTag(viewhandler);
            if(shopItem.getState().equals("rest")){
                viewhandler.distance.setText("休息中");
                viewhandler.delivery_times.setVisibility(View.GONE);
                view.setBackgroundResource(R.color.rest);
            }
        }
        else{
             Log.d("shoplist","position= "+position);
             view = views.get(position);
             viewhandler = (Viewhandler)view.getTag();
        }
        return view;
    }

    public void updatePicture(final String path, final int position){

        //new Thread(new Runnable() {
        //    @Override
           // public void run() {
               // while(currentUpdatePicturePos != position);
                ShopListPresenter slp = new ShopListPresenter();
                slp.getPicture(path, new HttpCallbackListener() {
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
                        Log.d("ShopListItem1111",e.toString());
                        Message msg = new Message();
                        msg.what = -1;
                        msg.arg1 = position;
                        msg.obj = e.toString();
                        handler.sendMessage(msg);
                    }
                });
           // }
      //  }).start();

    }

    public String createDiscountText(String discount){
        String res = "";
        if(discount.equals("")) return  res;
        try {
            JSONArray ja = new JSONArray(discount);
            if(ja.length() == 0) return res;
            res += "折：";
            for(int i =0;i<ja.length() ; i++){
                res +="满"+ja.getJSONArray(i).getDouble(0)+"减"+ja.getJSONArray(i).getDouble(1);
                if(i < ja.length()-1) res += ";";
            }
        }catch (Exception e){
            Log.d("shopItemAdapter",e.toString());
            return "";

        }
        return res;

    }

    class Viewhandler{
        TextView shop_name;
        ImageView shop_icon;
        TextView sales;
        TextView bot_price; //起送价
        TextView delivery_times;//配送时间
        TextView special_offer;//优惠活动
        TextView discount;
        TextView distance;
    }


}
