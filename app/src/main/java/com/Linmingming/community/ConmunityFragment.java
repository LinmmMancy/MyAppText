package com.Linmingming.community;

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
        return textView;

    }

    @Override
    public void initData() {
        super.initData();

        textView.setText("我是conm");

    }
}
