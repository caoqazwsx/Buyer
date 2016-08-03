package com.zhao.buyer.fragment.shopfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhao.buyer.R;
import com.zhao.buyer.activity.ShopActivity;

public class SellerFragment extends Fragment {
    private View rootView;
 //   private int shopId;

    private LinearLayout context;

//    private String str_deliveryService;
//    private String str_serviceTime;
//    private String str_specialOffer;

    private TextView telephone;
    private TextView address;
    private TextView serviceTime;
    private TextView deliveryService;
    private TextView specialOffer;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_seller, container, false);
 //           ShopActivity sa = (ShopActivity)getActivity();
//            shopId = sa.getShopId();
//            str_deliveryService = sa.getDeliveryService();
//            str_serviceTime = sa.getServiceTime();
//            str_specialOffer = sa.getSpecialOffer();
            telephone = (TextView)rootView.findViewById(R.id.seller_telephone);
            address = (TextView)rootView.findViewById(R.id.seller_address);
            serviceTime = (TextView)rootView.findViewById(R.id.seller_serviceTime);
            deliveryService = (TextView)rootView.findViewById(R.id.seller_deliveryService);
            specialOffer = (TextView)rootView.findViewById(R.id.seller_spcialOffer);
            context = (LinearLayout)rootView.findViewById(R.id.seller_context);
            context.setVisibility(View.GONE);

        }else{
            ViewGroup parent = (ViewGroup)rootView.getParent();
            if(parent != null){
                parent.removeView(rootView);
            }
        }
        init();
        return rootView;
    }

    private void  init(){
        ShopActivity sa = (ShopActivity)getActivity();
     //   Log.d("SellerFragment",str_deliveryService+" "+str_serviceTime+" "+str_specialOffer);
        deliveryService.setText(sa.getIntent().getStringExtra("deliveryService"));
        serviceTime.setText(sa.getIntent().getStringExtra("serviceTime"));
        specialOffer.setText(sa.getIntent().getStringExtra("specialOffer"));
        telephone.setText(sa.getIntent().getStringExtra("telephone"));
        address.setText(sa.getIntent().getStringExtra("address"));
        context.setVisibility(View.VISIBLE);
//        SellerPresenter sp =new SellerPresenter();
//        sp.getSellerInfo(shopId, new HttpCallbackListener() {
//            @Override
//            public void onFinish(String response) {
//                Log.d("SellerFragment",response);
//
//                try {
//                    JSONArray ja = new JSONArray(response);
//                    if (ja.length() != 0) {
//                        SellerInfo sellerInfo = new Gson().fromJson(ja.getJSONObject(0).toString(), SellerInfo.class);
//                        Message msg = new Message();
//                        msg.what = 1;
//                        msg.obj = sellerInfo;
//                        handler.sendMessage(msg);
//                    } else {
//                        Message msg = new Message();
//                        msg.what = -2;
//                        handler.sendMessage(msg);
//                    }
//                } catch (Exception e) {
//                    Log.d("SellerFragment", e.toString());
//                    Message msg = new Message();
//                    msg.what = -1;
//                    handler.sendMessage(msg);
//                }
//
//            }
//
//            @Override
//            public void onFinish(InputStream in) {
//
//            }
//
//            @Override
//            public void onFinish(Bitmap bm) {
//
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.d("SellerFragment",e.toString());
//                Message msg = new Message();
//                msg.what = -1;
//                handler.sendMessage(msg);
//            }
//        });

    }
}