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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.activity.BackFormReasonSubmitActivity;
import com.zhao.buyer.activity.HomeActivity;
import com.zhao.buyer.activity.PayActivity;
import com.zhao.buyer.R;
import com.zhao.buyer.activity.SubmitCommentActivity;
import com.zhao.buyer.activity.SubmitFormActivity;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.fragment.homefragment.FormFragment;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.presenter.FormPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zhao on 2016/5/1.
 */
public class FormItemAdapter extends ArrayAdapter<Form> {

    private int alreadyUpdatePicturePos;
    private int resourceId;
    private Viewhandler viewhandler;
    private ArrayList<View> views = new ArrayList<View>();
    private ArrayList<CartFoodItem> cartFoodItems;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bitmap bm = (Bitmap) msg.obj;
                    int pos = msg.arg1;
                 //   Log.d("FormListItem", "updpicture: " + pos);
                    Viewhandler tem = (Viewhandler)views.get(pos).getTag();
                    ImageView imageView = (ImageView) tem.forms_item_icon;
                    imageView.setImageBitmap(bm);
                    break;
                case 2:
                    HomeActivity ha = (HomeActivity)getContext();
                    FormFragment ff = (FormFragment)ha.getFragment("form");
                    ff.init();
                    break;
                case 3:
                    Form formItem = (Form)msg.obj;
                    Cart.getCart().clear();
                    Cart.getCart().add(cartFoodItems);
                    Cart.getCart().setShop_id(formItem.getShopId());
                    Intent it = new Intent(getContext(), SubmitFormActivity.class);
                    it.putExtra("shopName",formItem.getShopName());
                    it.putExtra(APPCONST.CLEAR_CART, APPCONST.CLEAR_CART);
                    getContext().startActivity(it);
                case -1:
                    Log.d("FormListItem handler", "error:" + msg.arg1);
                    break;
                case -2:
                  Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };


    public FormItemAdapter(Context context, int textViewResourceId,
                           ArrayList<Form> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        cartFoodItems = new ArrayList<CartFoodItem>();
        alreadyUpdatePicturePos = -1;
        initAllView();
    }
    private void initAllView(){
        for(int position = 0;position < getCount();position++) {
            final Form formItem = getItem(position);
            View view;
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            views.add(position, view);
            viewhandler = new Viewhandler();
            viewhandler.forms_item_shop_name = (TextView) view.findViewById(R.id.forms_item_shop_name);
            viewhandler.forms_item_price = (TextView) view.findViewById(R.id.forms_item_price);
            viewhandler.forms_item_icon = (ImageView) view.findViewById(R.id.forms_item_icon);
            viewhandler.forms_item_time = (TextView) view.findViewById(R.id.forms_item_time);
            viewhandler.forms_item_state = (TextView) view.findViewById(R.id.forms_item_state);
            viewhandler.forms_item_pay = (Button) view.findViewById(R.id.forms_item_pay);
            viewhandler.forms_item_again = (Button) view.findViewById(R.id.forms_item_again);
            viewhandler.forms_item_confirm = (Button) view.findViewById(R.id.forms_item_confirm);
            viewhandler.forms_item_goComment = (Button) view.findViewById(R.id.forms_item_goComment);
            viewhandler.forms_item_delete = (Button) view.findViewById(R.id.forms_item_delete);
            viewhandler.forms_item_cancel = (Button) view.findViewById(R.id.forms_item_cancel);
            viewhandler.forms_item_back = (Button) view.findViewById(R.id.forms_item_backForm);
            init(formItem);
            view.setTag(viewhandler);
            view = views.get(position);
            viewhandler = (Viewhandler) view.getTag();
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(alreadyUpdatePicturePos < position) {
            alreadyUpdatePicturePos ++;
            updatePicture(getItem(position).getShopIcon(), position);
        }
        return views.get(position);
    }

    private void init(final Form formItem){
        viewhandler.forms_item_shop_name.setText(formItem.getShopName());
        viewhandler.forms_item_price.setText("￥" + formItem.getPayPrice());
        viewhandler.forms_item_state.setText(parseFormState(formItem.getFormState()));
        viewhandler.forms_item_time.setText(formItem.getSubmitTime());
        viewhandler.forms_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("警告");
                dialog.setMessage("订单删除后不可恢复，是否确认删除？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FormPresenter fp = new FormPresenter();
                        Log.d("deleteForm",formItem.getId()+"");
                        fp.deleteFormItem(formItem.getId(),new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                if(response.equals("success")){
                                    Message msg = new Message();
                                    msg.what = 2;
                                    handler.sendMessage(msg);
                                }else{
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
                                Log.d("FormItemAdapter",e.toString());
                                Message msg = new Message();
                                msg.what = -2;
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
        viewhandler.forms_item_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(),PayActivity.class);
                it.putExtra("pay_price", formItem.getPayPrice());
                it.putExtra("form_id", formItem.getId());
                Log.d("forms_item_pay",""+formItem.getPayPrice()+"  "+formItem.getId());
                getContext().startActivity(it);


            }
        });
        viewhandler.forms_item_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormPresenter fp = new FormPresenter();
                fp.getFormFoodListToCart(formItem.getId(),new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if (!response.equals("null")) {
                            try {
                                JSONArray tem = new JSONArray(new JSONArray(response).get(0).toString());
                                Log.d("FormPresenter", tem.toString()+"  "+ tem.length());
                                cartFoodItems.clear();
                                for (int i = 0; i < tem.length(); i++) {
                                    JSONObject jb = tem.getJSONObject(i);
                                    cartFoodItems.add(new CartFoodItem(jb.getInt("foodId"),jb.getString("foodName"),jb.getInt("foodNum"),
                                            jb.getDouble("foodPrice"),jb.getDouble("foodTotalPrice")));
                                    // Log.d("FormPresenter jb", ""+cartFoodItems.get(i).getFoodId());
                                }
                                Message msg = new Message();
                                msg.what = 3;
                                msg.obj = formItem;
                                handler.sendMessage(msg);
                            } catch (Exception e) {
                                Message msg = new Message();
                                msg.what = -2;
                                handler.sendMessage(msg);
                                Log.d("FormPresenter", e.toString());
                            }
                        }else{
                            Message msg = new Message();
                            msg.what = -2;
                            handler.sendMessage(msg);
                            //Log.d("FormPresenter", e.toString());
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
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);
                    }
                });

            }
        });
        viewhandler.forms_item_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormPresenter fp = new FormPresenter();
                fp.confirmForm(formItem.getId(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if(response.equals("success")) {
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }else{
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
                        Log.d("FormFrament",e.toString());
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);
                    }
                });

            }
        });
        viewhandler.forms_item_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormPresenter fp = new FormPresenter();
                fp.cancelForm(formItem.getId(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if(response.equals("success")) {
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }else{
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
                        Log.d("FormFrament",e.toString());
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);
                    }
                });

            }
        });
        viewhandler.forms_item_goComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), SubmitCommentActivity.class);
                it.putExtra("formId",formItem.getId());
                getContext().startActivity(it);

            }
        });
        viewhandler.forms_item_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), BackFormReasonSubmitActivity.class);
                it.putExtra("formId",formItem.getId());
                getContext().startActivity(it);
            }
        });
        checkFormState(viewhandler,formItem);
    }

    public void changeEditState(){

        for(int i = 0; i < views.size();i++) {
            viewhandler = (Viewhandler) views.get(i).getTag();
            viewhandler.forms_item_again.setVisibility(View.GONE);
            viewhandler.forms_item_pay.setVisibility(View.GONE);
            viewhandler.forms_item_confirm.setVisibility(View.GONE);
            viewhandler.forms_item_goComment.setVisibility(View.GONE);
            viewhandler.forms_item_cancel.setVisibility(View.GONE);
            viewhandler.forms_item_back.setVisibility(View.GONE);

            Form form = getItem(i);
            if(form.getFormState().equals(APPCONST.WAIT_COMMENT)||
                    form.getFormState().equals(APPCONST.CANCEL)||
                    form.getFormState().equals(APPCONST.FINISH)) {
                viewhandler.forms_item_delete.setVisibility(View.VISIBLE);
            }
        }
    }

    public void changeFinishState(){
        for(int i = 0; i < views.size();i++){
            viewhandler = (Viewhandler) views.get(i).getTag();
            checkFormState(viewhandler,getItem(i));
        }
    }

    private void checkFormState(Viewhandler viewhandler,Form form){
        viewhandler.forms_item_delete.setVisibility(View.GONE);
        viewhandler.forms_item_again.setVisibility(View.VISIBLE);
        if( form.getFormState().equals(APPCONST.WAIT_PAY)){
            viewhandler.forms_item_pay.setVisibility(View.VISIBLE);
            viewhandler.forms_item_confirm.setVisibility(View.GONE);
            viewhandler.forms_item_goComment.setVisibility(View.GONE);
            viewhandler.forms_item_cancel.setVisibility(View.VISIBLE);
            viewhandler.forms_item_back.setVisibility(View.GONE);
        }else if(form.getFormState().equals(APPCONST.WAIT_ARRIVED)){
            viewhandler.forms_item_pay.setVisibility(View.GONE);
            viewhandler.forms_item_confirm.setVisibility(View.VISIBLE);
            viewhandler.forms_item_goComment.setVisibility(View.GONE);
            viewhandler.forms_item_cancel.setVisibility(View.GONE);
            viewhandler.forms_item_back.setVisibility(View.VISIBLE);
        }else if(form.getFormState().equals(APPCONST.WAIT_ACCEPT)){
            viewhandler.forms_item_pay.setVisibility(View.GONE);
            viewhandler.forms_item_confirm.setVisibility(View.GONE);
            viewhandler.forms_item_goComment.setVisibility(View.GONE);
            viewhandler.forms_item_cancel.setVisibility(View.VISIBLE);
            viewhandler.forms_item_back.setVisibility(View.GONE);
        }else if(form.getFormState().equals(APPCONST.WAIT_COMMENT)){
            viewhandler.forms_item_pay.setVisibility(View.GONE);
            viewhandler.forms_item_confirm.setVisibility(View.GONE);
            viewhandler.forms_item_goComment.setVisibility(View.VISIBLE);
            viewhandler.forms_item_cancel.setVisibility(View.GONE);
            viewhandler.forms_item_back.setVisibility(View.GONE);
        }else if(form.getFormState().equals(APPCONST.FINISH)){
            viewhandler.forms_item_pay.setVisibility(View.GONE);
            viewhandler.forms_item_confirm.setVisibility(View.GONE);
            viewhandler.forms_item_goComment.setVisibility(View.GONE);
            viewhandler.forms_item_cancel.setVisibility(View.GONE);
            viewhandler.forms_item_back.setVisibility(View.GONE);
        }else if(form.getFormState().equals(APPCONST.CANCEL)){
            viewhandler.forms_item_pay.setVisibility(View.GONE);
            viewhandler.forms_item_confirm.setVisibility(View.GONE);
            viewhandler.forms_item_goComment.setVisibility(View.GONE);
            viewhandler.forms_item_cancel.setVisibility(View.GONE);
            viewhandler.forms_item_back.setVisibility(View.GONE);
        }else if(form.getFormState().equals(APPCONST.WAIT_BACK)){
            viewhandler.forms_item_pay.setVisibility(View.GONE);
            viewhandler.forms_item_confirm.setVisibility(View.GONE);
            viewhandler.forms_item_goComment.setVisibility(View.GONE);
            viewhandler.forms_item_cancel.setVisibility(View.GONE);
            viewhandler.forms_item_back.setVisibility(View.GONE);
        }
    }

    private void updatePicture(final String path, final int position) {
        FormPresenter fp = new FormPresenter();
        fp.getPicture(path, new HttpCallbackListener() {
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
                Log.d("ShopListItem", e.toString());
                Message msg = new Message();
                msg.what = -1;
                msg.arg1 = position;
                msg.obj = e.toString();
                handler.sendMessage(msg);
            }
        });

    }

    private String parseFormState(String state){

        if(state.equals(APPCONST.WAIT_ACCEPT)) return "待接单";
        else if(state.equals(APPCONST.WAIT_PAY)) return "待支付";
        else if(state.equals(APPCONST.FINISH)) return "订单完成";
        else if(state.equals(APPCONST.CANCEL)) return "已取消";
        else if(state.equals(APPCONST.WAIT_ARRIVED)) return "待送达";
        else if(state.equals(APPCONST.WAIT_COMMENT)) return "待评价";
        else if(state.equals(APPCONST.WAIT_BACK))  return "待退单";
        return "";
    }

    class Viewhandler {
        TextView forms_item_shop_name;
        TextView forms_item_price;
        ImageView forms_item_icon;
        TextView forms_item_time;
        TextView forms_item_state;
        Button forms_item_pay;
        Button forms_item_again;
        Button forms_item_confirm;
        Button forms_item_goComment;
        Button forms_item_delete;
        Button forms_item_cancel;
        Button forms_item_back;


    }

}
