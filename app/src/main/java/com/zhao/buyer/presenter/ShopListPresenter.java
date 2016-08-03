package com.zhao.buyer.presenter;

import android.content.Context;
import android.location.Location;

import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.globalvariable.Utility;
import com.zhao.buyer.itemmodel.Shop;


/**
 * Created by zhao on 2016/4/24.
 */
public class ShopListPresenter extends BasePresenter{


    public void getShopList(double lng,double lat,int radius,final HttpCallbackListener listener) {
        String address = Globalvariable.SERVER_ADDRESS+
                "type=nearbyshop&radius="+radius+"&location="+lng+","+lat ;
        getList(address,listener);
    }

    public int countDistance(Shop shop, double lng,double lat,Context context){
        String[] shopLocation = shop.getLocation().split(",");
        //LocationPresenter lp = new LocationPresenter();
       // Location location = lp.getCurrentLocation(context);
        int res = Utility.getP2PDistance(lng,lat,Double.parseDouble(shopLocation[0]),Double.parseDouble(shopLocation[1]));
        return res;
    }
    public int countDistance(Shop shop,Context context){
        String[] shopLocation = shop.getLocation().split(",");
        LocationPresenter lp = new LocationPresenter();
        Location location = lp.getCurrentLocation(context);
        int res = Utility.getP2PDistance(location.getLongitude(),location.getLatitude(),Double.parseDouble(shopLocation[0]),Double.parseDouble(shopLocation[1]));
        return res;
    }




}
