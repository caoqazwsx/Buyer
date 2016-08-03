package com.zhao.buyer.fragment.homefragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhao.buyer.activity.FormInfoActivity;
import com.zhao.buyer.R;
import com.zhao.buyer.custom.DownUpListView;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.itemmodel.*;
import com.zhao.buyer.presenter.FormPresenter;
import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;


public class FormFragment extends Fragment {
    private ArrayList<Form> formitems;
    private View rootView;
    private DownUpListView listView;
    // private ListView listView;
    private ProgressBar progressBar;
    private Button btn_edit;
    private  FormItemAdapter formItemAdapter;
    private boolean edit_state;
    //boolean lastState;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    listView.onRefreshComplete();
                    formItemAdapter = new FormItemAdapter(getContext(), R.layout.listview_item_form, formitems);
                    progressBar.setVisibility(View.GONE);
                    listView.setAdapter(formItemAdapter);
                    listView.setDividerHeight(10);
                   // Log.d("FormFragment", "success init");
                    break;
                case -1:
                    listView.onRefreshComplete();
                    Toast.makeText(getActivity(), "请检查你的网络", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(getActivity(), "无订单", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edit_state = false;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null){
            Log.d("FormFragment","rootview == null");
            View view = inflater.inflate(R.layout.fragment_form, container, false);
            progressBar = (ProgressBar)view.findViewById(R.id.progress_bar_formlist);
            listView = (DownUpListView) view.findViewById(R.id.list_forms);
            btn_edit = (Button)view.findViewById(R.id.form_edit);
            progressBar.setVisibility(View.VISIBLE);
            formitems = new ArrayList<Form>();
            listView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if(formItemAdapter != null && formitems.size()!=0) {
                        checkEditState();
                    }
                }
            });
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edit_state){
                        btn_edit.setText("编辑");
                        edit_state = false;
                        formItemAdapter.changeFinishState();
                    }else{
                        btn_edit.setText("完成");
                        edit_state = true;
                        formItemAdapter.changeEditState();
                    }
                }
            });
            listView.setonRefreshListener(new DownUpListView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    init();
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(getActivity(), FormInfoActivity.class);
                   // it.putExtra("shopName",formitems.get(position-1).getShopName());
                    it.putExtra("formId", formitems.get(position-1).getId());
                    getActivity().startActivity(it);
                }
            });

            rootView=view;
        }
        else{
            Log.d("FormFragment","rootview != null");
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup)rootView.getParent();
            if(parent!=null){
                Log.d("FormFragment","parent != null");
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    //订单模块可编辑，此方法用于检测处于状态并对UI加以改变
    public void checkEditState() {

        if (edit_state) {
            btn_edit.setText("完成");
            formItemAdapter.changeEditState();
        } else {
            btn_edit.setText("编辑");
            formItemAdapter.changeFinishState();
        }

    }

    public void init() {
        formitems.clear();
        listView.setAdapter(null);
        FormPresenter fp = new FormPresenter();//通过服务器端获取订单信息
        fp.getFormList(new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (response != null) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONArray ja2 = new JSONArray(ja.get(i).toString());
                            formitems.add(new Form(ja2.getInt(0), Long.parseLong(ja2.get(1).toString()), ja2.get(2).toString()
                                    , ja2.get(3).toString(), Double.parseDouble(ja2.get(4).toString()), ja2.get(5).toString(), ja2.get(6).toString()));
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);

                    } catch (Exception e) {
                        Log.d("FormFragment", e.toString());
                        Message msg = new Message();
                        msg.what = -1;
                        handler.sendMessage(msg);
                    }
                } else {
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
                Log.d("FormFragment", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }
        });

    }
    @Override
    public void onResume(){
        if(!isHidden()) {
            //listView.setAdapter(null);
            init();
        }
        super.onResume();
    }


}
