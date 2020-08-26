package com.product.as.merchants.adapter;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.GoodsListEntity;
import com.product.as.merchants.ui.ProductDetailsActivity;
import com.product.as.merchants.util.GlideUtils;
import com.product.as.merchants.util.JumpActivityUtil;


import java.util.List;

public class GoodsListAdapter extends RecycleAdapter<GoodsListEntity.DataBean> {
    public GoodsListAdapter(Context context, int layoutId, List<GoodsListEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(RecyclerViewHolder holder, GoodsListEntity.DataBean dataBean) {
        holder.setText(R.id.name, dataBean.getProduct_name());
        holder.setText(R.id.retailprice, "结算价 : " + dataBean.getBalance());
        holder.setText(R.id.Singleweight, "规格 : " + dataBean.getUnit());
        holder.setText(R.id.Specifications, "产地 : " + dataBean.getOrigin());
        ImageView pic = holder.getConvertView().findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(dataBean.getIcon(), pic);
    }
}
