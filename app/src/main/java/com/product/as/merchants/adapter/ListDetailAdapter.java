package com.product.as.merchants.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderDeliveryEntity;
import com.product.as.merchants.ui.UpdateDriverActivity;
import com.product.as.merchants.ui.UserInformationActivity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.ZXingUtils;

import java.util.Arrays;
import java.util.List;

public class ListDetailAdapter extends RecycleAdapter<OrderDeliveryEntity.DataBean> {
    public ListDetailAdapter(Context context, int layoutId, List<OrderDeliveryEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, OrderDeliveryEntity.DataBean dataBean) {
        TextView hor = holder.getConvertView().findViewById(R.id.purchase);
        if (dataBean.getOrder_type() == 0) {
            hor.setBackgroundResource(R.drawable.bg_5green_border);
            hor.setText("直购");
        } else {
            hor.setBackgroundResource(R.drawable.bg_5red_border);
            hor.setText("团购");
        }
        holder.setText(R.id.groupno,"订单号:"+dataBean.getGroup_no());
        TextView rec_name = holder.getConvertView().findViewById(R.id.rec_name);
        rec_name.setText(dataBean.getRec_name());
        rec_name.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putString("groupno",dataBean.getGroup_no());
            JumpActivityUtil.launchActivity(mContext, UserInformationActivity.class,bundle);
        });

        TextView rec_tel = holder.getConvertView().findViewById(R.id.rec_tel);
        TextView copyaddress = holder.getConvertView().findViewById(R.id.copyaddress);

        rec_tel.setText(dataBean.getRec_tel());
        rec_tel.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + dataBean.getRec_tel());
            intent.setData(data);
            mContext.startActivity(intent);
        });
        List<String> split = Arrays.asList(dataBean.getDeli_com().split(","));
        RecyclerView recyclerView = holder.getConvertView().findViewById(R.id.rcydetailed);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DeliComAdapter deliComAdapter = new DeliComAdapter(mContext, R.layout.item_delicom, split);
        recyclerView.setAdapter(deliComAdapter);
        holder.setText(R.id.address, "地址：" + dataBean.getRec_add());
        copyaddress.setOnClickListener(v -> {
            ZXingUtils.copyAddress(mContext, dataBean.getRec_add());
        });
        holder.setText(R.id.distribution, "发货人信息： 姓名: " + dataBean.getSaler_user_name() + "   电话: " + dataBean.getSaler_user_phone());
        holder.setText(R.id.area,"区域: "+dataBean.getRec_area());
        TextView distr=holder.getConvertView().findViewById(R.id.Distributor);
        distr.setOnClickListener(view -> {
            Bundle  bundle=new Bundle();
            bundle.putString("group_no",dataBean.getGroup_no());
            bundle.putString("saler_id",dataBean.getSaler_id()+"");
            bundle.putString("rec_area",dataBean.getRec_area());
            bundle.putString("deli_name",dataBean.getDeli_name());
            bundle.putString("code",dataBean.getCode());
            JumpActivityUtil.launchActivity(mContext, UpdateDriverActivity.class,bundle);
        });
        holder.setText(R.id.Distributor,"配送人:"+dataBean.getDeli_name());
        holder.setText(R.id.code,dataBean.getCode());
        TextView textView = holder.getConvertView().findViewById(R.id.status);
        int status = dataBean.getStatus();
        switch (status) {
            case 3:
                textView.setBackgroundResource(R.drawable.bg_12empty_border);
                textView.setTextColor(mContext.getResources().getColor(R.color.color_27));
                textView.setText("已送达");
                break;
            case 2:
                textView.setBackgroundResource(R.drawable.bg_12blue_border);
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setText("确认送达");
                break;
        }
    }
}


