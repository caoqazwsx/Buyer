package com.zhao.buyer.fragment.homefragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.zhao.buyer.activity.LocationActivity;
import com.zhao.buyer.R;
import com.zhao.buyer.activity.ShopActivity;
import com.zhao.buyer.custom.DownUpListView;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.globalvariable.Utility;
import com.zhao.buyer.itemmodel.Shop;
import com.zhao.buyer.itemmodel.ShopItemAdapter;
import com.zhao.buyer.presenter.LocationPresenter;
import com.zhao.buyer.presenter.ShopListPresenter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class ShopFragment extends Fragment {
    private ArrayList<Shop> shopItems;
    private View rootView;
    private DownUpListView listView;
   private  ShopListPresenter slp;
    private ProgressBar progressBar;
    private LocationManager locationManager;
    private String provider;
    private TextView myLocation;
    private LinearLayout setLocation;

    private double lng;
    private double lat;



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    listView.onRefreshComplete();
                    ShopItemAdapter shopItemAdapter = new ShopItemAdapter(getContext(), R.layout.listview_item_shop, shopItems);
                    progressBar.setVisibility(View.GONE);
                    listView.setAdapter(shopItemAdapter);
                    Log.d("shoplist","success init");
                    break;
                case 2:
                    myLocation.setText(LocationPresenter.reduceStrLocation((String)msg.obj));
                    initShopList();
                    break;
                case -1:
                    listView.onRefreshComplete();
                    Toast.makeText(getActivity(), "请检查你的网络", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(getActivity(), "定位错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopItems = new ArrayList<Shop>();
        slp = new ShopListPresenter();
        //init();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView == null){
            Log.d("shoplistFragment","rootview == null");
            View view = inflater.inflate(R.layout.fragment_shop, container, false);
            progressBar = (ProgressBar)view.findViewById(R.id.progress_bar_shoplist);
            listView = (DownUpListView) view.findViewById(R.id.list_shops);
            myLocation = (TextView)view.findViewById(R.id.myLocation);
            setLocation = (LinearLayout)view.findViewById(R.id.shop_setLocktion);
           // listView = (ListView) view.findViewById(R.id.list_shops);
            progressBar.setVisibility(View.VISIBLE);
            lng = 0;
            lat = 0;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(getActivity(), ShopActivity.class);//此处的position-1是因为加入了刷新头视图，内容项整体后移
                    it.putExtra("shop_name", shopItems.get(position-1).getShopName());
                    it.putExtra("shop_id", shopItems.get(position-1).getId());
                    it.putExtra("bot_price", shopItems.get(position-1).getBotPrice());
                    it.putExtra("deliveryService", shopItems.get(position-1).getDeliveryService());
                    it.putExtra("serviceTime", shopItems.get(position-1).getServiceTime());
                    it.putExtra("specialOffer", shopItems.get(position-1).getSpecialOffer());
                    it.putExtra("address", shopItems.get(position-1).getAddress());
                    it.putExtra("telephone", shopItems.get(position-1).getTelephone());
                    it.putExtra("discount", shopItems.get(position-1).getDiscount());
                    it.putExtra("state", shopItems.get(position-1).getState());
                    getActivity().startActivity(it);
                }
            });

            setLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(getActivity(), LocationActivity.class);
                    getActivity().startActivityForResult(it,1);
                }
            });
            listView.setonRefreshListener(new DownUpListView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initShopList();
                    }
            });
            rootView=view;
        }
        else{
            Log.d("shopFragment","rootview != null");
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup)rootView.getParent();
            if(parent!=null){
                Log.d("shopFragment","parent != null");
                parent.removeView(rootView);
            }
        }
        initMyLocation();
        return rootView;
    }
    //初始化商店子项
    public void initShopList(){
        shopItems.clear();
        listView.setAdapter(null);
        slp.getShopList(lng,lat,5000,new HttpCallbackListener(){
            @Override
            public void onFinish(String response) {
                try {
                    JSONArray res = new JSONArray(response);
                    ArrayList<Shop> restShopItems  = new ArrayList<Shop>();
                    Log.d("shopFragment", "get Data length:" + res.length());
                    for (int i = 0; i < res.length(); i++) {
                        //JSONObject jb = res.getJSONObject(i);
                        JSONObject jb = res.getJSONObject(i);
                        jb.put("discount",jb.getString("discount"));
                        Shop shop = new Gson().fromJson(jb.toString(),Shop.class);
                        if(shop.getState().equals("rest")){
                            restShopItems.add(shop);
                        }else {
                            shop.setDistance(slp.countDistance(shop,lng,lat,getContext()));
                            shopItems.add(shop);
                        }
                    }
                    for(int i =0;i<restShopItems.size();i++){
                        shopItems.add(restShopItems.get(i));
                    }
                    Log.d("shopFragment", "shopItems add success!");
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Log.d("shopFragment", e.toString());
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
                Log.d("shoplist", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }
        });
    }

    public void initMyLocation(){
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(getContext(), "No location provider to use",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            final Location location = locationManager.getLastKnownLocation(provider);
            if(location != null){
                lng = location.getLongitude();
                lat = location.getLatitude();
                //initShopList();
                updLocation();
            }
            locationManager.requestLocationUpdates(provider,3000,1000,new LocationListener(){

                @Override
                public void onLocationChanged(final Location location){
                    if(location != null) {
                        if(lng ==0& lat ==0){
                            lng = location.getLongitude();
                            lat = location.getLatitude();
                           // initShopList();
                           updLocation();
                        }else if (Utility.getP2PDistance(location.getLongitude(), location.getLatitude(),lng,lat) > 1000) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            dialog.setTitle("提示");
                            dialog.setMessage("你的位置发生变化，是否重新定位到当前位置");
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("定位", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    lng = location.getLongitude();
                                    lat = location.getLatitude();
                                    //initShopList();
                                    updLocation();
                                }
                            });
                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.show();

                        }
                    }
                }

                @Override
                public void onProviderDisabled(String provider)  {

                }

                @Override
                public void onProviderEnabled(String provider)  {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

            });

        } catch (SecurityException e) {
            Log.d("Location",e.toString());
            Toast.makeText(getContext(), "请开启定位权限",
                    Toast.LENGTH_SHORT).show();
        }

      //  Log.d("Location",provider+"  " +location.toString());

    }

    public void updLocation(){
        LocationPresenter lp = new LocationPresenter();
        if(lng == 0&lat ==0){
            Toast.makeText(getContext(), "定位失败",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        lp.getStringLocation(lng,lat, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    if(!response.equals("error")) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }else {
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);
                    }
                }catch (Exception e){
                    Log.d("shopFragment",e.toString());
                    Message msg = new Message();
                    msg.what = -2;
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
                Log.d("shopFragment",e.toString());
                Message msg = new Message();
                msg.what = -2;
                handler.sendMessage(msg);
            }
        });
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setMyLocation(String string){
        myLocation.setText(LocationPresenter.reduceStrLocation(string));
    }


}
