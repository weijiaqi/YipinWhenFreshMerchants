package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderInfoEntity;
import com.product.as.merchants.entity.OrderListEntity;
import com.product.as.merchants.util.GlideUtils;

import java.util.List;

public class ToBeShippedAdapter extends RecycleAdapter<OrderInfoEntity.DataBean.ListBean> {
    public ToBeShippedAdapter(Context context, int layoutId, List<OrderInfoEntity.DataBean.ListBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, OrderInfoEntity.DataBean.ListBean dataBean) {
        ImageView pic = holder.getConvertView().findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(dataBean.getPro_pic(),pic);


    }
}

