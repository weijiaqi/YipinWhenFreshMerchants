package com.product.as.merchants.adapter;

import android.content.Context;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderInfoEntity;
import com.product.as.merchants.entity.OrderListsEntity;

import java.util.List;

public class AllToBeShippedTypeAdapter extends RecycleAdapter<OrderListsEntity.DataBean.ListBean> {
    public AllToBeShippedTypeAdapter(Context context, int layoutId, List<OrderListsEntity.DataBean.ListBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, OrderListsEntity.DataBean.ListBean dataBean) {
        holder.setText(R.id.name, dataBean.getProduct_name());
        holder.setText(R.id.number, "x" + dataBean.getSale_amount());

        holder.setText(R.id.balance, "¥"+dataBean.getPro_price());

    }
}



