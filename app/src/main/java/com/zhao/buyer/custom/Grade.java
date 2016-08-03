package com.zhao.buyer.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhao.buyer.R;

import java.util.ArrayList;

/**
 * Created by zhao on 2016/5/7.
 */
public class Grade extends LinearLayout {
    private ImageView grade1;
    private ImageView grade2;
    private ImageView grade3;
    private ImageView grade4;
    private ImageView grade5;
    private ArrayList<ImageView> grades;

    public Grade(Context context, AttributeSet attrs) {
        super(context, attrs);
        grades = new ArrayList<ImageView>();
        LayoutInflater.from(context).inflate(R.layout.custom_grade,this);
        grades.add((ImageView)findViewById(R.id.comment_item_grade1));
        grades.add((ImageView)findViewById(R.id.comment_item_grade2));
        grades.add((ImageView)findViewById(R.id.comment_item_grade3));
        grades.add((ImageView)findViewById(R.id.comment_item_grade4));
        grades.add((ImageView)findViewById(R.id.comment_item_grade5));

    }

    public void setGrade(int grade){
        int i=0;
       while(i < grade  && i < grades.size()){
           grades.get(i++).setImageResource(R.drawable.uj);
       }
        while( i < grades.size()){
            grades.get(i++).setImageResource(R.drawable.ui);
        }

    }

}
