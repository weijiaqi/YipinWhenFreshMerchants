package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsListEntity;
import com.product.as.merchants.entity.GoodsStatusEntity;

import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.GlideUtils;
import com.product.as.merchants.util.NoticeObserver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffShelfAdapter extends RecycleAdapter<GoodsListEntity.DataBean> {
    public OffShelfAdapter(Context context, int layoutId, List<GoodsListEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(RecyclerViewHolder holder, GoodsListEntity.DataBean dataBean) {
        holder.setText(R.id.name, dataBean.getProduct_name());
        holder.setText(R.id.retailprice, "结算价 : " + dataBean.getBalance());
        holder.setText(R.id.Singleweight, "规格 : " + dataBean.getUnit());
        holder.setText(R.id.Specifications, "产地 : " + dataBean.getOrigin());
        ImageView pic = holder.getConvertView().findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(dataBean.getIcon(), pic);
        TextView Reinstall = holder.getConvertView().findViewById(R.id.Reinstall);
        Reinstall.setOnClickListener(v -> {
            ApiInterfaceTwo.ApiFactory.createApi().goodsstatus(dataBean.getProducts_id(), 1).enqueue(new Callback<GoodsStatusEntity>() {
                @Override
                public void onResponse(Call<GoodsStatusEntity> call, Response<GoodsStatusEntity> response) {
                    if (response.body().getFlag() == 1) {
                        NoticeObserver.getInstance().notifyObservers(Constants.OnShelves);
                    }
                }
                @Override
                public void onFailure(Call<GoodsStatusEntity> call, Throwable t) {

                }
            });

        });
    }
}
