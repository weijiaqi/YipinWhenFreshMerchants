package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.TextView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.entity.UserSearchEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.SharedPreferencesUtils;

import java.util.List;

public class AddMemberAdapter extends RecycleAdapter<UserSearchEntity.DataBean> {
    public AddMemberAdapter(Context context, int layoutId, List<UserSearchEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(RecyclerViewHolder holder, UserSearchEntity.DataBean dataBean) {

        holder.setText(R.id.phone, dataBean.getMobile());
        TextView add = holder.getConvertView().findViewById(R.id.add);

    }
}

