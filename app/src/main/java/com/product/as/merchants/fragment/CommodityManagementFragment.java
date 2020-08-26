package com.product.as.merchants.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.meikoz.core.base.BaseFragment;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.CommodityGoodsListUpdateAdapter;
import com.product.as.merchants.adapter.GoodsListUpdateAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsListEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.pager.BasePager;
import com.product.as.merchants.ui.ProductDetailsActivity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.view.NoScrollViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//商品管理
public class CommodityManagementFragment extends BaseFragment implements NoticeObserver.Observer {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.search)
    EditText search;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.rcypayall)
    RecyclerView rcypayall;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private CommodityGoodsListUpdateAdapter goodsListAdapter;

    int page = 1;
    int currentPosition=0;
    List<GoodsListEntity.DataBean> dataBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.commodity_fragment;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) throws IOException {
        NoticeObserver.getInstance().addObserver(this);
        ivBack.setVisibility(View.GONE);
        tvTitles.setText("商品管理");
        rcypayall.setLayoutManager(new LinearLayoutManager(mContext));
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

                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    try {
                        currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
                        Log.e("=====currentPosition", "" + currentPosition);
                    } catch (Exception e) {
                    }
                }

            }
        });

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
                        goodsListAdapter = new CommodityGoodsListUpdateAdapter(mContext, dataBeanList);
                        rcypayall.setAdapter(goodsListAdapter);
                    }
                    goodsListAdapter.notifyDataSetChanged();
                    goodsListAdapter.setOnItemClickListener((view, position) -> {
                        currentPosition=position;
                        Bundle bundle = new Bundle();
                        bundle.putInt("products_id", dataBeanList.get(position).getProducts_id());
                        JumpActivityUtil.launchActivity(mContext, ProductDetailsActivity.class, bundle);
                    });

                } else {
                    if (page == 1 || page == 0) {
                        goodsListAdapter = new CommodityGoodsListUpdateAdapter(mContext, dataBeanList);
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
            goodslists(page, 0, name, String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
        } else if (what == Constants.GOODSUPDATE) {
            String name = (String) t;
            String[] string = name.split("&");
            dataBeanList.get(currentPosition).setProduct_name(string[0]);
            dataBeanList.get(currentPosition).setBalance(string[1]);
            dataBeanList.get(currentPosition).setUnit(string[2]);
            dataBeanList.get(currentPosition).setOrigin(string[3]);
            goodsListAdapter.notifyItemChanged(currentPosition);

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }
}
