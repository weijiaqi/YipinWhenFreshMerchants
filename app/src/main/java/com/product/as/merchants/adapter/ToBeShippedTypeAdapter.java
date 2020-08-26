package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderInfoEntity;
import com.product.as.merchants.util.GlideUtils;

import java.util.List;

public class ToBeShippedTypeAdapter extends RecycleAdapter<OrderInfoEntity.DataBean.ListBean> {
    public ToBeShippedTypeAdapter(Context context, int layoutId, List<OrderInfoEntity.DataBean.ListBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, OrderInfoEntity.DataBean.ListBean dataBean) {
        holder.setText(R.id.name, dataBean.getProduct_name());
        holder.setText(R.id.number, "x" + dataBean.getSale_amount());

        holder.setText(R.id.balance, "¥"+dataBean.getPro_price());

    }
}


