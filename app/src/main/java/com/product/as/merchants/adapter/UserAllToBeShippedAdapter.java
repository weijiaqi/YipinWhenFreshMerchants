package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderListsEntity;
import com.product.as.merchants.entity.PlatformListEntity;
import com.product.as.merchants.util.GlideUtils;

import java.util.List;

public class UserAllToBeShippedAdapter extends RecycleAdapter<PlatformListEntity.DataBean.ListBean> {
    public UserAllToBeShippedAdapter(Context context, int layoutId, List<PlatformListEntity.DataBean.ListBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, PlatformListEntity.DataBean.ListBean dataBean) {
        ImageView pic = holder.getConvertView().findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(dataBean.getPro_pic(), pic);


    }
}
