package com.product.as.merchants.adapter;

import android.content.Context;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.ExpressEntity;


import java.util.List;

public class UpdateDriverAdapter extends RecycleAdapter<ExpressEntity.DataBean> {
    public UpdateDriverAdapter(Context context, int layoutId, List<ExpressEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, ExpressEntity.DataBean dataBean) {
        holder.setText(R.id.name, dataBean.getNick_name());
        holder.setText(R.id.phone, "x" + dataBean.getMobile());
    }
}



