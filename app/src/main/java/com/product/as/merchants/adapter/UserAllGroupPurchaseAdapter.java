package com.product.as.merchants.adapter;

import android.content.Context;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderListsEntity;
import com.product.as.merchants.entity.PlatformListEntity;

import java.util.List;

public class UserAllGroupPurchaseAdapter extends RecycleAdapter<PlatformListEntity.DataBean.ListBean> {
    public UserAllGroupPurchaseAdapter(Context context, int layoutId, List<PlatformListEntity.DataBean.ListBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, PlatformListEntity.DataBean.ListBean dataBean) {
        holder.setText(R.id.Number, dataBean.getProduct_name()+" "+dataBean.getSale_amount()+"件");
        holder.setText(R.id.sum, "¥"+dataBean.getPro_price());
    }
}
