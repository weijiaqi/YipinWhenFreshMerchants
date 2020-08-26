package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderListEntity;
import com.product.as.merchants.util.GlideUtils;

import java.util.List;

public class ShippedPicAdapter extends RecycleAdapter<OrderListEntity.DataBean.ListBean> {
    public ShippedPicAdapter(Context context, int layoutId, List<OrderListEntity.DataBean.ListBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder,OrderListEntity.DataBean.ListBean dataBean) {
        ImageView pic = holder.getConvertView().findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(dataBean.getPro_pic(),pic);
        holder.setText(R.id.name,dataBean.getProduct_name());

    }
}

