package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.CateListEntity;
import com.product.as.merchants.entity.OrderProcureEntity;
import com.product.as.merchants.util.GlideUtils;

import java.util.List;

public class LecelThreeAdapter extends RecycleAdapter<CateListEntity.DataBean> {
    public LecelThreeAdapter(Context context, int layoutId, List<CateListEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(RecyclerViewHolder holder, CateListEntity.DataBean dataBean) {
        holder.setText(R.id.name, dataBean.getCata_name());
    }
}
