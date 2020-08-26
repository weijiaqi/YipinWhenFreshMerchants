package com.product.as.merchants.adapter;

import android.content.Context;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.AccountHisToryEntity;
import com.product.as.merchants.entity.DeliveryOrderEntity;

import java.util.List;

public class DeliveryOrderAdapter extends RecycleAdapter<DeliveryOrderEntity.DataBean> {
    public DeliveryOrderAdapter(Context context, int layoutId, List<DeliveryOrderEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, DeliveryOrderEntity.DataBean dataBean) {
        holder.setText(R.id.name, dataBean.getDeli_name());


    }
}
