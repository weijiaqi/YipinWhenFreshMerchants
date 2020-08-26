package com.product.as.merchants.pager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.OffShelfAdapter;

import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsListEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.PlatformProductDetailsActivity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * 已下架
 */
public class OffShelfPager extends BasePager  implements NoticeObserver.Observer{
    private RecyclerView rcyoffshelf;
    private SmartRefreshLayout refreshLayout;
    int page = 1;
    private OffShelfAdapter offShelfAdapter;
    private TextView count;
    List<GoodsListEntity.DataBean> dataBeanList = new ArrayList<>();
    private String sid="";
    int currentPosition=0;
    public OffShelfPager(Context context) {
        super(context);
    }

    @Override
    public View initView(LayoutInflater layoutInflater) {
        NoticeObserver.getInstance().addObserver(this);
        View view = layoutInflater.inflate(R.layout.offshelf_pager, null);
        rcyoffshelf = view.findViewById(R.id.rcyoffshelf);
        rcyoffshelf.setLayoutManager(new LinearLayoutManager(context));
        refreshLayout = view.findViewById(R.id.refreshLayout);
        count=view.findViewById(R.id.count);
        goodslists(page, 3, "", sid);

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            goodslists(page, 3, "", sid);
            refreshLayout.finishRefresh();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            goodslists(page, 3, "", sid);
            if (dataBeanList.size() >= 0) {
                rcyoffshelf.smoothScrollToPosition(dataBeanList.size());
            }
            refreshLayout.finishLoadMore();
        });

        rcyoffshelf.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    count.setText("已下架: "+response.body().getCount());
                    if (page == 1) {
                        dataBeanList.clear();
                    }

                    if (response.body().getData()!=null&& response.body().getData().size() > 0) {
                        dataBeanList.addAll(response.body().getData());
                        if (offShelfAdapter == null) {
                            offShelfAdapter = new OffShelfAdapter(context, R.layout.item_offshelf, dataBeanList);
                            rcyoffshelf.setAdapter(offShelfAdapter);
                        }
                        offShelfAdapter.notifyDataSetChanged();
                        offShelfAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                                currentPosition=position;
                                Bundle bundle = new Bundle();
                                bundle.putInt("status",2);
                                bundle.putInt("products_id", dataBeanList.get(position).getProducts_id());
                                JumpActivityUtil.launchActivity(context, PlatformProductDetailsActivity.class, bundle);
                            }

                            @Override
                            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                                return false;
                            }
                        });

                    } else {
                        if (page == 1 || page == 0) {
                            offShelfAdapter = new OffShelfAdapter(context, R.layout.item_offshelf, dataBeanList);
                            rcyoffshelf.setAdapter(offShelfAdapter);
                        }
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
        if (what == Constants.OnShelves) {
            refreshLayout.autoRefresh();
        }else   if (what == Constants.SEARCHKeyword) {
            String name = (String) t;
            goodslists(page, 3, name, sid);

        }else if (what==Constants.SALERLIST){
            String name = (String) t;
            sid = name.substring(0, name.indexOf("&"));
            page=1;
            goodslists(page, 3, "", sid);
        }else if (what == Constants.GOODSUPDATE3) {
            String name = (String) t;
            String[] string = name.split("&");
            dataBeanList.get(currentPosition).setProduct_name(string[0]);
            dataBeanList.get(currentPosition).setBalance(string[1]);
            dataBeanList.get(currentPosition).setUnit(string[2]);
            dataBeanList.get(currentPosition).setOrigin(string[3]);
            offShelfAdapter.notifyItemChanged(currentPosition);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }

}
