package com.product.as.merchants.adapter;

import android.content.Context;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;


import java.util.List;

public class PurchaseDetailsAdapter  extends RecycleAdapter<ProductOrderEntity.DataBean> {
    public PurchaseDetailsAdapter(Context context, int layoutId, List<ProductOrderEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, ProductOrderEntity.DataBean dataBean) {
        holder.setText(R.id.name,dataBean.getRec_name());
        holder.setText(R.id.phone,dataBean.getRec_tel());
        holder.setText(R.id.address, "地址: "+dataBean.getRec_add());
        holder.setText(R.id.product_name, dataBean.getProduct_name());
        holder.setText(R.id.number, "X"+ dataBean.getSale_amount());
    }
}


