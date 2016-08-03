package com.zhao.buyer.activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.zhao.buyer.Database.BuyerDatabaseHelper;
import com.zhao.buyer.R;
import com.zhao.buyer.httpconnection.HttpCallbackListener;
import com.zhao.buyer.globalvariable.Globalvariable;
import com.zhao.buyer.presenter.LoginPresenter;

import java.io.InputStream;


public class LoginActivity extends BaseActivity {
    private ProgressBar pb;
    private EditText account;
    private  EditText password;
    private  final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    saveLoginState();
                    finish();
                    break;
                case 2:
                    pb.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    pb.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button log = (Button)findViewById(R.id.login);
        Button reg = (Button)findViewById(R.id.register);
        Button back = (Button)findViewById(R.id.title_back);
        TextView titletext = (TextView)findViewById(R.id.title_text);
        titletext.setText("登录");
        pb = (ProgressBar)findViewById(R.id.progress_bar);
        pb.setVisibility(View.GONE);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = (EditText) findViewById(R.id.account);
                password = (EditText) findViewById(R.id.password);
                String acc = account.getText().toString();
                String pas = password.getText().toString();
                if (acc.equals("") || pas.equals(""))
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
//                else if(acc.length() < 6 || pas.length() < 6){
//                    Toast.makeText(LoginActivity.this, "账号或密码不能小于6位", Toast.LENGTH_SHORT).show();
//                }
                else{
                    pb.setVisibility(View.VISIBLE);
                    new LoginPresenter().loginCheck(acc, pas, new HttpCallbackListener() {
                        @Override
                        public void onFinish(Bitmap bm){}
                        @Override
                        public void onFinish(InputStream in){}
                        @Override
                        public void onFinish(String response) {
                            if(response.equals("error")){
                                Message msg = new Message();
                                msg.obj = "用户名或密码错误";
                                msg.what = 2;
                                handler.sendMessage(msg);
                            }else {
                                Message msg = new Message();
                                msg.obj = "登陆成功";
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }

                        }

                        @Override
                        public void onError(Exception e) {
                            Message msg = new Message();
                            msg.obj = "-1";
                            msg.what = -1;
                            handler.sendMessage(msg);
                            Log.d("Http", e.toString());

                        }
                    });
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText account = (EditText) findViewById(R.id.account);
                EditText password = (EditText) findViewById(R.id.password);
                String acc = account.getText().toString();
                String pas = password.getText().toString();
                if (acc.equals("") || acc.equals(""))
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                else {
                    pb.setVisibility(View.VISIBLE);
                    new LoginPresenter().register(acc, pas, new HttpCallbackListener() {
                        @Override
                        public void onFinish(Bitmap bm) {
                        }

                        @Override
                        public void onFinish(InputStream in) {
                        }

                        @Override
                        public void onFinish(String response) {
                            Message msg = new Message();
                            msg.obj = response;
                            msg.what = 2;
                            handler.sendMessage(msg);

                        }

                        @Override
                        public void onError(Exception e) {
                            Message msg = new Message();
                            msg.obj = "-1";
                            msg.what = -1;
                            handler.sendMessage(msg);
                            Log.d("Http", e.toString());

                        }
                    });
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void saveLoginState(){
        Globalvariable.LOGIN_STATE = true;
        Globalvariable.ACCOUNT = account.getText().toString();
        Globalvariable.PASSWROD = password.getText().toString();
        BuyerDatabaseHelper dbh = new BuyerDatabaseHelper(this,"Buyer.db",null,1);
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.execSQL("delete from User");
        db.execSQL("insert into User(account,password) values(?,?)",
                new String[]{Globalvariable.ACCOUNT, Globalvariable.PASSWROD});
    }
}
