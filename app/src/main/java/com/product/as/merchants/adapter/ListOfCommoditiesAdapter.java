package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.DeliveryEntity;
import com.product.as.merchants.util.GlideUtils;


import java.util.List;

public class ListOfCommoditiesAdapter extends RecycleAdapter<DeliveryEntity.DataBean> {
    public ListOfCommoditiesAdapter(Context context, int layoutId, List<DeliveryEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(RecyclerViewHolder holder, DeliveryEntity.DataBean dataBean) {
        ImageView pic = holder.getConvertView().findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(dataBean.getPro_pic(), pic);
        holder.setText(R.id.name, dataBean.getProduct_name());
        holder.setText(R.id.number, "x"+dataBean.getCount());

        holder.setText(R.id.region, dataBean.getOrigin()+"  "+dataBean.getUnit());
        holder.setText(R.id.product_name, "描述:"+dataBean.getDetail());
    }
}
