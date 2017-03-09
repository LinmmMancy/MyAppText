package com.Linmingming.community.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.Linmingming.R;
import com.Linmingming.base.BaseFragment;
import com.Linmingming.community.adapter.NewPostListViewAdapter;
import com.Linmingming.community.bean.NewPostBean;
import com.Linmingming.utils.CacheUtils;
import com.Linmingming.utils.Constants;
import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by Mancy on 2017/3/9.
 */

public class NerFragment extends BaseFragment {
    @InjectView(R.id.lv_new_post)
    ListView lvNewPost;
    @InjectView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    private NewPostListViewAdapter adapter;



    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_news_post, null);
        ButterKnife.inject(this, view);
        return view;

    }

    @Override
    public void initData() {
        super.initData();
        String savaJson = CacheUtils.getString(context, Constants.NEW_POST_URL);

        if (!TextUtils.isEmpty(savaJson)) {
            processData(savaJson);
        }

        getDataFromNet();
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
                .url(Constants.NEW_POST_URL)
                .id(100)//http,
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "hotfragment联网成功==");
                        CacheUtils.putString(context, Constants.NEW_POST_URL, response);
                        processData(response);

                        swiperefreshlayout.setRefreshing(false);

                    }


                });


    }

    private void processData(String json) {

        NewPostBean bean = JSON.parseObject(json, NewPostBean.class);
        List<NewPostBean.ResultEntity> result = bean.getResult();
        if (result != null && result.size() > 0) {

            //设置适配器
            adapter = new NewPostListViewAdapter(context, result);
            lvNewPost.setAdapter(adapter);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
