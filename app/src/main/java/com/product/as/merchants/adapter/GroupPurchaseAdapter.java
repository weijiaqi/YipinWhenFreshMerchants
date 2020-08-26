package com.product.as.merchants.adapter;

import android.content.Context;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.GroupInfoEntity;

import java.util.List;

public class GroupPurchaseAdapter extends RecycleAdapter<GroupInfoEntity.DataBean.ListBean> {
    public GroupPurchaseAdapter(Context context, int layoutId, List<GroupInfoEntity.DataBean.ListBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, GroupInfoEntity.DataBean.ListBean dataBean) {
        holder.setText(R.id.Number, dataBean.getProduct_name()+" "+dataBean.getSale_amount()+"件");
        holder.setText(R.id.sum, "¥"+dataBean.getPro_price());
    }
}



