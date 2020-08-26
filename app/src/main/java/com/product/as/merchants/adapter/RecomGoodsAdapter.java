package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.RecomGoodsEntity;
import com.product.as.merchants.util.GlideUtils;

import java.util.List;

public class RecomGoodsAdapter extends RecycleAdapter<RecomGoodsEntity.DataBean> {
    public RecomGoodsAdapter(Context context, int layoutId, List<RecomGoodsEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(RecyclerViewHolder holder, RecomGoodsEntity.DataBean dataBean) {
        ImageView pic = holder.getConvertView().findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(dataBean.getProduct_img(),pic);
        holder.setText(R.id.name, dataBean.getProduct_name());
    }
}
