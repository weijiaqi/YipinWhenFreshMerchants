package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.TextView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderListsEntity;
import com.product.as.merchants.entity.PlatformListEntity;

import java.util.List;

public class UserOrderAdapter extends RecycleAdapter<PlatformListEntity.DataBean> {
    public UserOrderAdapter(Context context, int layoutId, List<PlatformListEntity.DataBean> datas) {
        super(context, layoutId, datas);

    }


    @Override
    public void convert(RecyclerViewHolder holder, PlatformListEntity.DataBean dataBean) {
        TextView hor = holder.getConvertView().findViewById(R.id.purchase);
        if (dataBean.getOrder_type() == 0) {
            hor.setBackgroundResource(R.drawable.bg_5green_border);
            hor.setText("直购");
        } else {
            hor.setBackgroundResource(R.drawable.bg_5red_border);
            hor.setText("团购");
        }
        holder.setText(R.id.code,dataBean.getCode());
        holder.setText(R.id.Ordernumber,"订单号：" + dataBean.getOrder_no());
        holder.setText(R.id.dates,"支付时间:" + dataBean.getPay_time());
        holder.setText(R.id.product_name,dataBean.getRec_name()+"     累计订单:"+dataBean.getCount());
        holder.setText(R.id.area, "区域: "+dataBean.getRec_area());
        holder.setText(R.id.address, "地址: "+dataBean.getRec_add());
        holder.setText(R.id.Commodity,"件数:" + dataBean.getCount());
        holder.setText(R.id.Actual,"实付:￥:" + dataBean.getString_real_balance());
    }
}
