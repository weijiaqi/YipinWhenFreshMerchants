package com.product.as.merchants.adapter;


import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;


import com.product.as.merchants.R;

import com.product.as.merchants.entity.OrderListEntity;

import java.util.List;

public class ShippedAdapter extends RecycleAdapter<OrderListEntity.DataBean> {
    public ShippedAdapter(Context context, int layoutId, List<OrderListEntity.DataBean> datas) {
        super(context, layoutId, datas);

    }


    @Override
    public void convert(RecyclerViewHolder holder, OrderListEntity.DataBean dataBean) {
        TextView hor = holder.getConvertView().findViewById(R.id.purchase);
        if (dataBean.getGroup_id() == 0) {
            hor.setBackgroundResource(R.drawable.bg_5green_border);
            hor.setText("直购");
        } else {
            hor.setBackgroundResource(R.drawable.bg_5red_border);
            hor.setText("团购");
        }
        TextView Printstatus = holder.getConvertView().findViewById(R.id.Print_status);
        if (dataBean.getIs_print_ticket()==1){
            Printstatus.setTextColor(Color.parseColor("#2BA0FE"));
            Printstatus.setText("已打印小票");
        }else {
            Printstatus.setTextColor(Color.parseColor("#E80000"));
            Printstatus.setText("未打印小票");
        }
        holder.setText(R.id.Ordernumber,"订单号：" + dataBean.getOrder_no());
        holder.setText(R.id.dates,"支付时间:" + dataBean.getPay_time());
        holder.setText(R.id.product_name,dataBean.getRec_name()+"     累计订单:"+dataBean.getOrder_count());
        holder.setText(R.id.area, "区域: "+dataBean.getRec_area());
        holder.setText(R.id.address, "地址: "+dataBean.getRec_add());
        holder.setText(R.id.Commodity,"件数:" + dataBean.getCount());
        holder.setText(R.id.Actual,"实付:￥:" + dataBean.getOrder_price());
    }
}
