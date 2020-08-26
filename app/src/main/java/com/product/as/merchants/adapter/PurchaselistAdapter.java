package com.product.as.merchants.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderEnsureEntity;
import com.product.as.merchants.entity.OrderProcureEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.GlideUtils;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaselistAdapter extends RecycleAdapter<OrderProcureEntity.DataBean> {
    public PurchaselistAdapter(Context context, int layoutId, List<OrderProcureEntity.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(RecyclerViewHolder holder, OrderProcureEntity.DataBean dataBean) {
        ImageView pic = holder.getConvertView().findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(dataBean.getPro_pic(), pic);
        holder.setText(R.id.name, dataBean.getProduct_name());
        holder.setText(R.id.number, "x" + dataBean.getCount());
        TextView replenishment = holder.getConvertView().findViewById(R.id.replenishment);
        if (dataBean.getIs_replenish() == 1) {
            replenishment.setText("(补货)");
        }
        holder.setText(R.id.region, dataBean.getOrigin());
        holder.setText(R.id.product_name, dataBean.getUnit());
//        ImageView Printer = holder.getConvertView().findViewById(R.id.Printer);
//        Printer.setOnClickListener(v -> {
//            NoticeObserver.getInstance().notifyObservers(Constants.Chuandi4,dataBean);
//        });
        TextView textView = holder.getConvertView().findViewById(R.id.status);
        int status = dataBean.getIs_choice();
        if (status == 0) {
            textView.setBackgroundResource(R.drawable.bg_12blue_border);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setText("确认备货");
        } else {
            textView.setBackgroundResource(R.drawable.bg_12empty_border);
            textView.setTextColor(mContext.getResources().getColor(R.color.color_27));
            textView.setText("已备货");
        }
        textView.setOnClickListener(c->{
            if (status==0){
                DialogUtils.getInstance().showSimpleDialog(mContext, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                    utils.setCancelable(false);
                    Button cofim = contentView.findViewById(R.id.submit);
                    Button exit = contentView.findViewById(R.id.cancel);
                    TextView content = contentView.findViewById(R.id.content);
                    content.setText("是否确认备货?");
                    exit.setOnClickListener(v2 -> {
                        utils.close();
                    });
                    cofim.setOnClickListener(v2 -> {
                        orderupdate_procure(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), dataBean.getDay(), dataBean.getProducts_id(), dataBean.getIs_replenish());
                        utils.close();
                    });
                });

            }

        });
    }

    public void orderupdate_procure(String sid, String date, String proid, int is_replenish) {
        ApiInterfaceTwo.ApiFactory.createApi().orderupdate_procure(sid, date, proid, is_replenish).enqueue(new Callback<OrderEnsureEntity>() {
            @Override
            public void onResponse(Call<OrderEnsureEntity> call, Response<OrderEnsureEntity> response) {
                if (response.body().getFlag() == 1) {
                    ToastUtil.show(response.body().getMsg(), 200);
                    NoticeObserver.getInstance().notifyObservers(Constants.BEIHUO);
                }
            }

            @Override
            public void onFailure(Call<OrderEnsureEntity> call, Throwable t) {

            }
        });
    }

}