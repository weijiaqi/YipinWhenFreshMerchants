package com.product.as.merchants.pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.GoodsListAdapter;
import com.product.as.merchants.adapter.GoodsListUpdateAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsListEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.ProductDetailsActivity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//商品全部
public class PayAllPager extends BasePager implements NoticeObserver.Observer {

    private RecyclerView rcypayall;
    private SmartRefreshLayout refreshLayout;
    private GoodsListUpdateAdapter goodsListAdapter;
    private TextView count;
    int page = 1;
    private int lastPosition = 0;
    private int lastOffset = 0;
    List<GoodsListEntity.DataBean> dataBeanList = new ArrayList<>();

    public PayAllPager(Context context) {
        super(context);
    }

    @SuppressLint("NewApi")
    @Override
    public View initView(LayoutInflater layoutInflater) {
        NoticeObserver.getInstance().addObserver(this);
        View view = layoutInflater.inflate(R.layout.payall_pager, null);
        rcypayall = view.findViewById(R.id.rcypayall);
        rcypayall.setLayoutManager(new LinearLayoutManager(context));
        refreshLayout = view.findViewById(R.id.refreshLayout);
        count = view.findViewById(R.id.count);
        goodslists(page, 0, "", String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            goodslists(page, 0, "", String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
            refreshLayout.finishRefresh();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            goodslists(page, 0, "", String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
            if (dataBeanList.size() >= 0) {
                rcypayall.smoothScrollToPosition(dataBeanList.size());
            }
            refreshLayout.finishLoadMore();
        });
        rcypayall.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (rcypayall.getLayoutManager() != null) {
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) rcypayall.getLayoutManager();
                    View topView = mLayoutManager.getChildAt(0); //获取第一个可视的item
                    if (topView != null) {
                        lastOffset = topView.getTop();
                        lastPosition = mLayoutManager.getPosition(topView);
                    }

                }

            }
        });


        return view;
    }



    @Override
    public <DATA> void initData(int type, DATA data) {

    }

    public void goodslists(int page, int pro_status, String product_name, String saler_id) {
        ApiInterfaceTwo.ApiFactory.createApi().goodslists(page, pro_status, product_name, saler_id).enqueue(new Callback<GoodsListEntity>() {
            @Override
            public void onResponse(Call<GoodsListEntity> call, Response<GoodsListEntity> response) {
                if (response.body().getFlag() == 1) {
                    if (page == 1) {
                        dataBeanList.clear();
                    }
                    count.setText("全部: " + response.body().getCount());
                    dataBeanList.addAll(response.body().getData());
                    if (goodsListAdapter == null) {
                        goodsListAdapter = new GoodsListUpdateAdapter(context, dataBeanList);
                        rcypayall.setAdapter(goodsListAdapter);
                    } else {
                        goodsListAdapter.notifyDataSetChanged();
                    }
                    goodsListAdapter.setOnItemClickListener((view, position) -> {
                        Bundle bundle = new Bundle();
                        bundle.putInt("products_id", dataBeanList.get(position).getProducts_id());
                        JumpActivityUtil.launchActivity(context, ProductDetailsActivity.class, bundle);
                    });

                } else {
                    if (page == 1 || page == 0) {
                        goodsListAdapter = new GoodsListUpdateAdapter(context, dataBeanList);
                        rcypayall.setAdapter(goodsListAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GoodsListEntity> call, Throwable t) {

            }
        });
    }

    @Override
    public <T> void update(int what, T t) {
//        if (what == Constants.SEARCHKeyword) {
//            String name = (String) t;
//            goodslists(page, 0, name, String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
//        } else if (what == Constants.GOODSUPDATE) {
//           refreshLayout.autoRefresh();
//        }
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }
}
