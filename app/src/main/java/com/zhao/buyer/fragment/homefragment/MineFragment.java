package com.zhao.buyer.fragment.homefragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhao.buyer.Database.BuyerDatabaseHelper;
import com.zhao.buyer.activity.ChargeActivity;
import com.zhao.buyer.activity.HelpActivity;
import com.zhao.buyer.activity.LoginActivity;
import com.zhao.buyer.activity.MyAddressActivity;
import com.zhao.buyer.activity.MyCollectActivity;
import com.zhao.buyer.activity.MyCommentActivity;
import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.presenter.MinePresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;


public class MineFragment extends Fragment {
    private View rootView;

    private TextView account;
    private Button logout;
    private Button login;

    private TextView balance;
    private TextView charge;
    private LinearLayout updBalance;

    private LinearLayout myComment;
    private LinearLayout myCollect;
    private LinearLayout myAddress;

    private LinearLayout help;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    balance.setText("￥" + (String) msg.obj);
                    break;
                case -1:
                    balance.setText("00.00");
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            View view = inflater.inflate(R.layout.fragment_mine, container, false);
            account = (TextView) view.findViewById(R.id.mine_account);
            logout = (Button) view.findViewById(R.id.mine_logout);
            login = (Button) view.findViewById(R.id.mine_login);
            balance = (TextView) view.findViewById(R.id.mine_balance);
            charge = (TextView) view.findViewById(R.id.mine_charge);
            updBalance = (LinearLayout) view.findViewById(R.id.mine_updBalance);
            myComment = (LinearLayout) view.findViewById(R.id.mine_comment);
            myCollect = (LinearLayout) view.findViewById(R.id.mine_collect);
            myAddress = (LinearLayout) view.findViewById(R.id.mine_address);
            help = (LinearLayout) view.findViewById(R.id.mine_help);
            updBalance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            });
            charge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (APPCONST.LOGIN_STATE) {
                        Intent it = new Intent(getActivity(), ChargeActivity.class);
                        getActivity().startActivity(it);
                    } else {
                        Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            myComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (APPCONST.LOGIN_STATE) {
                        Intent it = new Intent(getActivity(), MyCommentActivity.class);
                        getActivity().startActivity(it);
                    } else {
                        Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            myCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (APPCONST.LOGIN_STATE) {
                        Intent it = new Intent(getActivity(), MyCollectActivity.class);
                        getActivity().startActivity(it);
                    } else {
                        Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            myAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (APPCONST.LOGIN_STATE) {
                        Intent it = new Intent(getActivity(), MyAddressActivity.class);
                        it.putExtra("state", "show");
                        getActivity().startActivity(it);
                    } else {
                        Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(getActivity(), HelpActivity.class);
                    getActivity().startActivity(it);
                }
            });
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APPCONST.LOGIN_STATE = false;
                    APPCONST.ACCOUNT = null;
                    APPCONST.PASSWROD = null;
                    checkLoginState();
                    BuyerDatabaseHelper dbh = new BuyerDatabaseHelper(getContext(), "Buyer.db", null, 1);
                    SQLiteDatabase db = dbh.getWritableDatabase();
                    db.execSQL("delete from User");
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivity(it);
                }
            });
            rootView = view;
        } else {
            Log.d("mineFragment", "rootview != null");
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                Log.d("mineFragment", "parent != null");
                parent.removeView(rootView);
            }
        }
        //init();
        return rootView;
    }


    public void init() {

        MinePresenter mp = new MinePresenter();
        mp.getBalance(new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                try {
                    JSONArray ja = new JSONArray(response);
                    if (ja.length() != 0) {
                        JSONObject jo = ja.getJSONObject(0);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = jo.getString("balance");
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = -1;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Log.d("mineFragment1", e.toString());
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
                Log.d("mineFragment2", e.toString());
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);

            }
        });
    }

    public boolean checkLoginState() {
        if (APPCONST.LOGIN_STATE) {
            account.setText(APPCONST.ACCOUNT);
            logout.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            return true;
        } else {
            account.setText("未登录");
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            return false;
            // logout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        if (!isHidden()) {
            if (checkLoginState()) init();
            else balance.setText("00.0");
        }
        super.onResume();
    }
}
