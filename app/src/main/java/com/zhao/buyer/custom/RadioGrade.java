package com.zhao.buyer.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhao.buyer.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zhao on 2016/5/7.
 */
public class RadioGrade extends LinearLayout {
    private ImageButton btn_grade1;
    private ImageButton btn_grade2;
    private ImageButton btn_grade3;
    private ImageButton btn_grade4;
    private ImageButton btn_grade5;
    private int grade;
    private OnChangeGradeListener onChangeGradeListener;
   // private ArrayList<ImageView> grades;


    public RadioGrade(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.custom_radio_grade,this);
        btn_grade1 = (ImageButton)findViewById(R.id.grade1);
        btn_grade2 = (ImageButton)findViewById(R.id.grade2);
        btn_grade3 = (ImageButton)findViewById(R.id.grade3);
        btn_grade4 = (ImageButton)findViewById(R.id.grade4);
        btn_grade5 = (ImageButton)findViewById(R.id.grade5);
        grade = 0;
        btn_grade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade  = 1;
                btn_grade1.setImageResource(R.drawable.uj);
                btn_grade2.setImageResource(R.drawable.ui);
                btn_grade3.setImageResource(R.drawable.ui);
                btn_grade4.setImageResource(R.drawable.ui);
                btn_grade5.setImageResource(R.drawable.ui);
                onChangeGradeListener.onChange(grade);

            }
        });
        btn_grade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade  = 2;
                btn_grade1.setImageResource(R.drawable.uj);
                btn_grade2.setImageResource(R.drawable.uj);
                btn_grade3.setImageResource(R.drawable.ui);
                btn_grade4.setImageResource(R.drawable.ui);
                btn_grade5.setImageResource(R.drawable.ui);
                onChangeGradeListener.onChange(grade);


            }
        });
        btn_grade3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade  = 3;
                btn_grade1.setImageResource(R.drawable.uj);
                btn_grade2.setImageResource(R.drawable.uj);
                btn_grade3.setImageResource(R.drawable.uj);
                btn_grade4.setImageResource(R.drawable.ui);
                btn_grade5.setImageResource(R.drawable.ui);
                onChangeGradeListener.onChange(grade);

            }
        });
        btn_grade4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade  = 4;
                btn_grade1.setImageResource(R.drawable.uj);
                btn_grade2.setImageResource(R.drawable.uj);
                btn_grade3.setImageResource(R.drawable.uj);
                btn_grade4.setImageResource(R.drawable.uj);
                btn_grade5.setImageResource(R.drawable.ui);
                onChangeGradeListener.onChange(grade);


            }
        });
        btn_grade5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade  = 5;
                btn_grade1.setImageResource(R.drawable.uj);
                btn_grade2.setImageResource(R.drawable.uj);
                btn_grade3.setImageResource(R.drawable.uj);
                btn_grade4.setImageResource(R.drawable.uj);
                btn_grade5.setImageResource(R.drawable.uj);
                onChangeGradeListener.onChange(grade);

            }
        });

    }

    public void setOnChangeGradeListener(OnChangeGradeListener onChangeGradeListener){
        this.onChangeGradeListener = onChangeGradeListener;
    }

    public interface OnChangeGradeListener {

        public void onChange(int grade);

    }
}
