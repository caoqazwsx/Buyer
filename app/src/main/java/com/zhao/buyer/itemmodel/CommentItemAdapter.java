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
import com.zhao.buyer.custom.Grade;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.common.Utility;
import com.zhao.buyer.presenter.ShopPresenter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2016/5/7.
 */
public class CommentItemAdapter extends ArrayAdapter<Comment> {

   private boolean isHideAccount;
    private int resourceId;
    private Viewhandler viewhandler;
    private List<View> views = new ArrayList<View>();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;
                case -1:

                    break;

            }
        }
    };
    public CommentItemAdapter(Context context, int textViewResourceId,
                           ArrayList<Comment> objects,boolean isHideAccount) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.isHideAccount = isHideAccount;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Comment comment = getItem(position);
        View view;
        if(views.size() <= position){
         //   Log.d("foodlist", "position= " + position);
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            views.add(position,view);
            // Log.d("ShopListItem", "" + (position - start) + "  size =" + views.size());
            viewhandler = new Viewhandler();
            viewhandler.account = (TextView)view.findViewById(R.id.comment_item_account);
            viewhandler.icon = (ImageView)view.findViewById(R.id.comment_item_icon);
            viewhandler.comment_text = (TextView)view.findViewById(R.id.comment_item_text);
            viewhandler.comment_food= (TextView)view.findViewById(R.id.comment_item_food);
            viewhandler.comment_time= (TextView)view.findViewById(R.id.comment_item_time);
            viewhandler.comment_reply = (TextView)view.findViewById(R.id.comment_item_reply);
            viewhandler.grade = (Grade)view.findViewById(R.id.comment_item_grade);
            init(viewhandler,position);
            view.setTag(viewhandler);
        }
        else{
         //   Log.d("list","positionfood= "+position);
            view = views.get(position);
            viewhandler = (Viewhandler)view.getTag();
        }
        return view;
    }

    private void init(Viewhandler viewhandler,int pos){
        final Comment comment = getItem(pos);
        //final int position = pos;
        viewhandler.grade.setGrade(comment.getShopGrade());
        if(isHideAccount) {
            viewhandler.account.setText(Utility.hideAccount(comment.getCommentAccount()));
        }else {
            viewhandler.account.setText(comment.getCommentAccount());
        }
        viewhandler.comment_food.setText(comment.getFood());
        viewhandler.comment_time.setText(comment.getCommentTime());
        viewhandler.comment_text.setText(comment.getCommentText());
        if(!comment.getReply().equals("")){
            viewhandler.comment_reply.setVisibility(View.VISIBLE);
            viewhandler.comment_reply.setText("商家回复："+comment.getReply());
        }else{
            viewhandler.comment_reply.setVisibility(View.GONE);
        }

    }
    private void updatePicture(String path, final int position){
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
    class Viewhandler{
        TextView account;
        ImageView icon;
        TextView comment_text;
        TextView comment_food;
        TextView comment_time;
        TextView comment_reply;
        Grade grade;

    }

}
