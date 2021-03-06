package com.Linmingming.home;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Linmingming.R;
import com.Linmingming.base.BaseFragment;
import com.Linmingming.home.adapter.HomeAdapter;
import com.Linmingming.home.bean.HomeBean;
import com.Linmingming.utils.CacheUtils;
import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

import static com.Linmingming.utils.Constants.HOME_URL;


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
    @InjectView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;


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
        String savaJson = CacheUtils.getString(context, HOME_URL);
        if (!TextUtils.isEmpty(savaJson)) {
            processData(savaJson);
        }

        super.initData();
        getDataFromNet();
        Log.e("TAG", "initData: 111111");
        swiperefreshlayout.setDistanceToTriggerSync(100);
        swiperefreshlayout.setColorSchemeColors(Color.GREEN, Color.RED);
        swiperefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet();
            }
        });


    }


    private void getDataFromNet() {


        OkHttpUtils
                .get()
                //联网地址
                .url(HOME_URL)
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
                        CacheUtils.putString(context, HOME_URL, response);
                        processData(response);
                        swiperefreshlayout.setRefreshing(false);


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
