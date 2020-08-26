package com.product.as.merchants.pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.tools.Constant;
import com.meikoz.core.adapter.RecycleAdapter;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.GoodsListAdapter;
import com.product.as.merchants.adapter.GoodsListUpdateAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsListEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.PlatformProductDetailsActivity;
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
public class PlatformCommodityPayAllPager extends BasePager implements NoticeObserver.Observer {

    private RecyclerView rcypayall;
    private SmartRefreshLayout refreshLayout;
    private GoodsListUpdateAdapter goodsListAdapter;
    private TextView count;
    int page = 1;
    int currentPosition=0;
    List<GoodsListEntity.DataBean> dataBeanList = new ArrayList<>();
     private String sid="";


    public PlatformCommodityPayAllPager(Context context) {
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
        goodslists(page, 0, "", sid);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            goodslists(page, 0, "", sid);
            refreshLayout.finishRefresh();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            goodslists(page, 0, "", sid);
            if (dataBeanList.size() >= 0) {
                rcypayall.smoothScrollToPosition(dataBeanList.size());
            }
            refreshLayout.finishLoadMore();
        });
        rcypayall.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    try {
                        currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
                        Log.e("=====currentPosition", "" + currentPosition);
                    } catch (Exception e) {
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
                    }
                    goodsListAdapter.notifyDataSetChanged();
                    goodsListAdapter.setOnItemClickListener((view, position) -> {
                        currentPosition=position;
                        Bundle bundle = new Bundle();
                        bundle.putInt("status", 0);
                        bundle.putInt("products_id", dataBeanList.get(position).getProducts_id());
                        JumpActivityUtil.launchActivity(context, PlatformProductDetailsActivity.class, bundle);
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
        if (what == Constants.SEARCHKeyword) {
            String name = (String) t;
            goodslists(page, 0, name, sid);
        } else if (what == Constants.GOODSUPDATE) {
            String name = (String) t;
            String[] string = name.split("&");
            dataBeanList.get(currentPosition).setProduct_name(string[0]);
            dataBeanList.get(currentPosition).setBalance(string[1]);
            dataBeanList.get(currentPosition).setUnit(string[2]);
            dataBeanList.get(currentPosition).setOrigin(string[3]);
            goodsListAdapter.notifyItemChanged(currentPosition);

        }else if (what==Constants.SALERLIST){
            String name = (String) t;
             sid = name.substring(0, name.indexOf("&"));
             page=1;
            goodslists(page, 0, "", sid);
        }else if (what== Constants.PlatformCommodityPayAll){
            Integer number = (Integer) t;
            dataBeanList.get(currentPosition).setPro_status(number);
            goodsListAdapter.notifyItemChanged(currentPosition);
        }
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }
}
