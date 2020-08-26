package com.product.as.merchants.adapter;

import android.content.Context;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;

import com.product.as.merchants.entity.SalerListEntity;

import java.util.List;

public class SalerListAdapter extends RecycleAdapter<SalerListEntity.DataBean> {
    private Context context;
    public SalerListAdapter(Context context, int layoutId, List<SalerListEntity.DataBean> datas) {
        super(context, layoutId, datas);
        this.context=context;
    }

    @Override
    public void convert(RecyclerViewHolder holder, SalerListEntity.DataBean walletEntity) {
        holder.setText(R.id.name, walletEntity.getName());
    }
}

