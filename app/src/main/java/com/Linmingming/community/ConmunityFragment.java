package com.Linmingming.community;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.Linmingming.base.BaseFragment;

/**
 * Created by Mancy on 2017/3/9.
 */

public class ConmunityFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {

        textView = new TextView(context);

        textView.setTextColor(Color.RED);

        textView.setTextSize(30);

        textView.setGravity(Gravity.CENTER);

        return textView;

    }

    @Override
    public void initData() {
        super.initData();

        textView.setText("我是conm");

    }
}
