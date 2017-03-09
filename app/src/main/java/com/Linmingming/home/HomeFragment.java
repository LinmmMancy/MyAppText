package com.Linmingming.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Linmingming.R;
import com.Linmingming.base.BaseFragment;
import com.Linmingming.home.adapter.HomeAdapter;
import com.Linmingming.home.bean.HomeBean;
import com.Linmingming.utils.Constants;
import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;


/**
 * Created by Mancy on 2017/3/9.
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.ll_main_scan)
    LinearLayout llMainScan;
    @InjectView(R.id.tv_search_home)
    TextView tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;

    private TextView textView;

    private HomeAdapter adapter;

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_home, null);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "initData: 111111");

        getDataFromNet();

    }

    private void getDataFromNet() {


        OkHttpUtils
                .get()
                //联网地址
                .url(Constants.HOME_URL)
                .id(100)//http,
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功==");
                        processData(response);

                    }


                });
    }

    private void processData(String json) {

        HomeBean homeBean = JSON.parseObject(json, HomeBean.class);

        Log.e("TAG", "processData: " + homeBean.getResult().getHot_info().get(0).getName());

        adapter = new HomeAdapter(context, homeBean.getResult());

        rvHome.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(context, 1);
        rvHome.setLayoutManager(manager);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
