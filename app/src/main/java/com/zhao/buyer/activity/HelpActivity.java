package com.zhao.buyer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhao.buyer.R;

public class HelpActivity extends BaseActivity {
    private TextView title;
    private Button btn_back;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        title = (TextView)findViewById(R.id.title_text);
        btn_back = (Button)findViewById(R.id.title_back);
        btn_exit = (Button)findViewById(R.id.help_exit);
        title.setText("帮助");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManage.finishAllActivites();
            }
        });

    }
}
