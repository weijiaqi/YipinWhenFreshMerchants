package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.AccountHisToryEntity;
import com.product.as.merchants.entity.OrderInfoEntity;
import com.product.as.merchants.util.GlideUtils;

import java.util.List;

public class AccountAssetsAdapter extends RecycleAdapter<AccountHisToryEntity.DataBean> {
    public AccountAssetsAdapter(Context context, int layoutId, List<AccountHisToryEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, AccountHisToryEntity.DataBean dataBean) {
        holder.setText(R.id.datetime,dataBean.getDay());
        holder.setText(R.id.price,dataBean.getValue());
        holder.setText(R.id.type,dataBean.getType());
    }
}


