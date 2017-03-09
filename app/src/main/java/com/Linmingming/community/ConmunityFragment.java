package com.Linmingming.community;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Linmingming.R;
import com.Linmingming.base.BaseFragment;
import com.Linmingming.community.adapter.CommunityViewPagerAdapter;
import com.Linmingming.community.fragment.HotFragment;
import com.Linmingming.community.fragment.NerFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mancy on 2017/3/9.
 */

public class ConmunityFragment extends BaseFragment {


    ImageButton ibCommunityMessage;
    @InjectView(R.id.tablayout)
    TabLayout tablayout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    private TextView textView;

    private ArrayList<BaseFragment> fragments;


    private CommunityViewPagerAdapter adapter;

    @Override
    public View initView() {


        View view = View.inflate(context, R.layout.fragment_community, null);
        ButterKnife.inject(this, view);
        return view;

    }

    @Override
    public void initData() {
        super.initData();


        Log.e("TAG", "initData: 初始化完成");


        initFragment();
        adapter = new CommunityViewPagerAdapter(getFragmentManager(), fragments);

        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

    private void initFragment() {

        fragments = new ArrayList<>();

        fragments.add(new HotFragment());
        fragments.add(new NerFragment());

        fragments.add(new HotFragment());
        fragments.add(new NerFragment());


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
