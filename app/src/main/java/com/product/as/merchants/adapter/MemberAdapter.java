package com.product.as.merchants.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.UserListEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.SharedPreferencesUtils;

import java.util.List;

public class MemberAdapter extends RecycleAdapter<UserListEntity.DataBean> {
    public MemberAdapter(Context context, int layoutId, List<UserListEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, UserListEntity.DataBean dataBean) {
        holder.setText(R.id.phone, dataBean.getMobile());
        ImageView seting=holder.getConvertView().findViewById(R.id.seeting);
        String roleid = String.valueOf(SharedPreferencesUtils.getSp(Constants.ROLEID, ""));
        if (!roleid.equals("1")){
            seting.setVisibility(View.GONE);
        }
    }
}


