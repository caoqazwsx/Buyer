package com.zhao.buyer.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhao.buyer.R;
import com.zhao.buyer.custom.DownUpListView;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.itemmodel.Comment;
import com.zhao.buyer.itemmodel.CommentItemAdapter;
import com.zhao.buyer.presenter.MinePresenter;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

public class MyCommentActivity extends BaseActivity {

    private TextView title;
    private Button btn_back;
    private ProgressBar progressBar;
    private DownUpListView listView;




    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setVisibility(View.GONE);
                    listView.onRefreshComplete();
                    CommentItemAdapter commentItemAdapter = new CommentItemAdapter(MyCommentActivity.this,
                            R.layout.listview_item_comment,(ArrayList<Comment>)msg.obj ,false);
                    listView.setAdapter(commentItemAdapter);
                    //Utility.setListViewHeightBasedOnChildren(listView);
                    break;
                case -1:
                    Toast.makeText(MyCommentActivity.this,"请检查你的网络",Toast.LENGTH_SHORT).show();
                    Log.d("Comment","error init");
                    break;
                case -2:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MyCommentActivity.this, "无内容", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        title = (TextView)findViewById(R.id.title_text);
        btn_back = (Button)findViewById(R.id.title_back);
        listView = (DownUpListView) findViewById(R.id.myComment_list);
        progressBar = (ProgressBar)findViewById(R.id.myComment_progressBar);
        title.setText("我的评价");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setonRefreshListener(new DownUpListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });
        init();
    }

    public void init() {
        MinePresenter mp = new MinePresenter();
        mp.getMyCommentList(new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    ArrayList<Comment>  comments = new ArrayList<Comment>();
                    if (ja.length() != 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            comments.add(new Gson().fromJson(ja.getString(i), Comment.class));
                        }
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = comments;
                            handler.sendMessage(msg);
                        }else{
                            Message msg = new Message();
                            msg.what = -2;
                            handler.sendMessage(msg);
                        }
                    }catch(Exception e){
                        Log.d("MyComment", e.toString());
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
                Log.d("MyComment", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }
        });
    }
}
