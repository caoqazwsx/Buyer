package com.zhao.buyer.itemmodel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.activity.EditAddressActivity;
import com.zhao.buyer.activity.MyAddressActivity;
import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.presenter.MinePresenter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2016/5/10.
 */
public class DeliveryAddressAdapter extends ArrayAdapter<DeliveryAddress> {

    private int resourceId;
    private Viewhandler viewhandler;
    private List<View> views = new ArrayList<View>();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MyAddressActivity  ma = (MyAddressActivity) getContext();
                    ma.init();
                    break;
                case -1:
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                    Log.d("DeliveryAddressAdapter", "error:" + msg.arg1);
                    break;
            }
        }
    };

    public DeliveryAddressAdapter(Context context, int textViewResourceId,
                               ArrayList<DeliveryAddress> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final DeliveryAddress deliveryAddress = getItem(position);
        View view;
        if(views.size() <= position){
        //    Log.d("deliveryAddress", "position= " + position);
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            views.add(position,view);
            viewhandler = new Viewhandler();
            viewhandler.contact = (TextView)view.findViewById(R.id.address_item_contact);
            viewhandler.telephone = (TextView)view.findViewById(R.id.address_item_telephone);
            viewhandler.address = (TextView)view.findViewById(R.id.address_item_address);
           // viewhandler.no = (TextView)view.findViewById(R.id.address_item_no) ;
            viewhandler.delete = (Button)view.findViewById(R.id.address_item_delete);
            viewhandler.edit = (Button)view.findViewById(R.id.address_item_edit);

            viewhandler.contact.setText(deliveryAddress.getContact());
            viewhandler.telephone.setText(deliveryAddress.getTelephone());
            viewhandler.address.setText(deliveryAddress.getAddress()+" "+deliveryAddress.getNo());
            //viewhandler.no.setText(deliveryAddress.getNo());

            viewhandler.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("警告");
                    dialog.setMessage("地址删除后不可恢复，是否确认删除？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MinePresenter mp = new MinePresenter();
                            Log.d("deliveryAddressAdapter",deliveryAddress.getId()+"");
                            mp.deleteAddress(deliveryAddress.getId(),new HttpCallbackListener() {
                                @Override
                                public void onFinish(String response) {
                                    if(response.equals("success")){
                                        Message msg = new Message();
                                        msg.what = 1;
                                        handler.sendMessage(msg);
                                    }else{
                                        Message msg = new Message();
                                        msg.what = -1;
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
                                    Log.d("deliveryAddressAdapter",e.toString());
                                    Message msg = new Message();
                                    msg.what = -1;
                                    handler.sendMessage(msg);
                                }
                            });

                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            });
            viewhandler.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(getContext(), EditAddressActivity.class);
                    it.putExtra("state","edit");
                    it.putExtra("id",deliveryAddress.getId());
                    it.putExtra("contact",deliveryAddress.getContact());
                    it.putExtra("telephone",deliveryAddress.getTelephone());
                    it.putExtra("address",deliveryAddress.getAddress());
                    it.putExtra("no",deliveryAddress.getNo());
                    getContext().startActivity(it);
                }
            });

            view.setTag(viewhandler);
        }
        else{
        //    Log.d("deliveryAddress","positionfood= "+position);
            view = views.get(position);
            viewhandler = (Viewhandler)view.getTag();
        }
        return view;
    }

    class Viewhandler{
        TextView contact;
        TextView telephone;
        TextView address;
        TextView no;
        Button delete;
        Button edit;


    }
}
