package com.zhao.buyer.fragment.shopfragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhao.buyer.R;
import com.zhao.buyer.activity.ShopActivity;
import com.zhao.buyer.custom.Grade;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.common.Utility;
import com.zhao.buyer.itemmodel.Comment;
import com.zhao.buyer.itemmodel.CommentItemAdapter;
import com.zhao.buyer.presenter.CommentPresenter;
import com.zhao.buyer.presenter.ShopPresenter;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;


public class CommentFragment extends Fragment {
    private int shopId;
    private View rootView;

    private LinearLayout context;
    private ProgressBar progressBar;

    private ListView listView;
    private TextView score;
    private TextView sendScore;
    private TextView shopScore;
    private Grade sendGrade;
    private Grade shopGrade;

    private double sumSendScore;
    private double sumShopScore;
    private double length;

    private CheckBox checkBox;
    private boolean check;

    private ArrayList<Comment> commentsAll;
    private ArrayList<Comment> commentsContext;
    private CommentItemAdapter commentItemAdapter;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updateList();
                    progressBar.setVisibility(View.GONE);
                    context.setVisibility(View.VISIBLE);
                    initScore();
                    break;
                case -1:
                    Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                    Log.d("Comment","error init");
                    break;
                case -2:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "本店无此内容", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       if(rootView == null){
           rootView = inflater.inflate(R.layout.fragment_comment, container, false);
           listView = (ListView) rootView.findViewById(R.id.comment_list);
           score = (TextView) rootView.findViewById(R.id.score);
           checkBox = (CheckBox) rootView.findViewById(R.id.checkBox);
           shopScore = (TextView)rootView.findViewById(R.id.comment_shopScore);
           sendScore = (TextView)rootView.findViewById(R.id.comment_sendScore);
           shopGrade = (Grade)rootView.findViewById(R.id.comment_shopGrade);
           sendGrade = (Grade)rootView.findViewById(R.id.comment_sendGrade);
           context = (LinearLayout)rootView.findViewById(R.id.comment_context);
           progressBar = (ProgressBar)rootView.findViewById(R.id.comment_progressbar);
           check = true;
           commentsAll = new ArrayList<Comment>();
           commentsContext = new ArrayList<Comment>();
           checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                       check = isChecked;
                       updateList();
               }
           });
       }else {
           // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
           ViewGroup parent = (ViewGroup) rootView.getParent();
           if (parent != null) {
               parent.removeView(rootView);
           }
       }
        init();
        return rootView;
    }

    public void init() {
        context.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        commentsAll.clear();
        commentsContext.clear();
        listView.setAdapter(null);
        ShopPresenter sp = new ShopPresenter();
        ShopActivity sa = (ShopActivity) getActivity();
        shopId = sa.getShopId();
        sumSendScore = 0;
        sumShopScore = 0;
        length = 0;
        CommentPresenter cp = new CommentPresenter();
        cp.getCommentList(shopId, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                try {
                    JSONArray ja = new JSONArray(response);
                    if (ja.length() != 0) {
                        Log.d("Comment", ja.toString());
                        //ArrayList<Comment> comments = new ArrayList<Comment>();
                        length = ja.length();
                        for (int i = 0; i < ja.length(); i++) {
                            Comment comment = new Gson().fromJson(ja.getString(i), Comment.class);
                            sumSendScore += (double) comment.getSendGrade();
                            sumShopScore += (double) comment.getShopGrade();
                            commentsAll.add(comment);
                            if (!comment.getCommentText().equals("")) {
                                commentsContext.add(comment);
                            }
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = -2;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Log.d("CommentFragment", e.toString());
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
                Log.d("CommentFragment", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);

            }
        });
    }

    private void initScore(){
        sendScore.setText(sumSendScore/length+"");
        shopScore.setText(sumShopScore/length+"");
        score.setText((sumShopScore+sumSendScore)/length/2+"");
        sendGrade.setGrade((int)(sumSendScore/length));
        shopGrade.setGrade((int)(sumShopScore/length));

    }

    private void updateList(){
        if(check){
            commentItemAdapter = new CommentItemAdapter(getContext(),R.layout.listview_item_comment,
                    commentsContext,true);
        }else {
            commentItemAdapter = new CommentItemAdapter(getContext(),R.layout.listview_item_comment,
                    commentsAll,true);
        }
        listView.setAdapter(commentItemAdapter);
        Utility.setListViewHeightBasedOnChildren(listView);
    }


}