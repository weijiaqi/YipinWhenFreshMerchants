package com.product.as.merchants.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.ExpressListEntity;

import java.util.List;

public class SelectDriverAdapter extends RecycleAdapter<ExpressListEntity.DataBean> {
    private Context context;
    public SelectDriverAdapter(Context context, int layoutId, List<ExpressListEntity.DataBean> datas) {
        super(context, layoutId, datas);
        this.context=context;
    }

    @Override
    public void convert(RecyclerViewHolder holder, ExpressListEntity.DataBean walletEntity) {
        holder.setText(R.id.driver, walletEntity.getNick_name());
    }
}

