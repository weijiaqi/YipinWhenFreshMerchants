package com.product.as.merchants.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderProcureEntity;
import com.product.as.merchants.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class PurchaselistCheckAdapter extends RecyclerView.Adapter<PurchaselistCheckAdapter.MyViewHolder> {
    private Context context;
    private List<OrderProcureEntity.DataBean> dataBeanList;


    public PurchaselistCheckAdapter(Context context, List<OrderProcureEntity.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_purchaselist, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GlideUtils.loadBigImgWithGlide(dataBeanList.get(position).getPro_pic(), holder.pic);
        holder.name.setText(dataBeanList.get(position).getProduct_name());
        holder.number.setText("x" + dataBeanList.get(position).getCount());
        if (dataBeanList.get(position).getIs_replenish() == 1) {
            holder.replenishment.setText("(补货)");
        }
        holder.region.setText(dataBeanList.get(position).getOrigin() + "  " + dataBeanList.get(position).getUnit());
        holder.product_name.setText("描述:" + dataBeanList.get(position).getDetail());
        switch (dataBeanList.get(position).getIs_choice()) {
            case 0:
                holder.status.setBackgroundResource(R.drawable.bg_12blue_border);
                holder.status.setTextColor(context.getResources().getColor(R.color.white));
                holder.status.setText("确认备货");
                break;
            default:
                holder.status.setBackgroundResource(R.drawable.bg_12empty_border);
                holder.status.setTextColor(context.getResources().getColor(R.color.color_27));
                holder.status.setText("已备货");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }


    /**
     * ViewHolder的类，用于缓存控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView pic;
        TextView name;
        TextView number;
        TextView replenishment;
        TextView region;
        TextView product_name;
        TextView status;

        public MyViewHolder(View view) {
            super(view);
            pic = view.findViewById(R.id.pic);
            name = view.findViewById(R.id.name);
            number = view.findViewById(R.id.number);
            replenishment = view.findViewById(R.id.replenishment);
            region = view.findViewById(R.id.region);
            product_name = view.findViewById(R.id.product_name);
            status = view.findViewById(R.id.status);
        }

    }
}

