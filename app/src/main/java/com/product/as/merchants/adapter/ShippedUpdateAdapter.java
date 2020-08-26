package com.product.as.merchants.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.product.as.merchants.R;
import com.product.as.merchants.entity.OrderListEntity;

import java.util.List;


public class ShippedUpdateAdapter extends RecyclerView.Adapter<ShippedUpdateAdapter.MyViewHolder> implements View.OnClickListener{


    private OnItemClickListener mOnItemClickListener;
    private List<OrderListEntity.DataBean> dataBeans;
    private Context mContext;

    public ShippedUpdateAdapter(Context context, List<OrderListEntity.DataBean> list) {
        mContext = context;
        this.dataBeans = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //引入item布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shaped, parent, false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new ShippedUpdateAdapter.MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (dataBeans.get(position).getGroup_id() == 0) {
            holder.purchase.setBackgroundResource(R.drawable.bg_5green_border);
            holder.purchase.setText("直购");
        } else {
            holder.purchase.setBackgroundResource(R.drawable.bg_5red_border);
            holder.purchase.setText("团购");
        }
        if (dataBeans.get(position).getIs_print_ticket()==1){
            holder.Printstatus.setTextColor(Color.parseColor("#2BA0FE"));
            holder.Printstatus.setText("已打印小票");
        }else {
            holder.Printstatus.setTextColor(Color.parseColor("#E80000"));
            holder.Printstatus.setText("未打印小票");
        }
        holder.Ordernumber.setText("订单号：" + dataBeans.get(position).getOrder_no());
        holder.dates.setText("支付时间:" + dataBeans.get(position).getPay_time());
        holder.product_name.setText(dataBeans.get(position).getRec_name()+"     累计订单:"+dataBeans.get(position).getOrder_count());
        holder.address.setText(dataBeans.get(position).getRec_add());
        holder.Commodity.setText("件数:" + dataBeans.get(position).getCount());
        holder.Actual.setText("实付:￥:" + dataBeans.get(position).getOrder_price());

        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return dataBeans != null ? dataBeans.size() : 0;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView purchase;
        TextView Ordernumber;
        TextView dates;
        TextView product_name;
        TextView address;
        TextView Commodity;
        TextView Actual;

        RelativeLayout rlall;
        ImageView Fruits;
        ImageView Vegetables;
        TextView Printstatus;
        public MyViewHolder(View itemView) {
            super(itemView);
            purchase = itemView.findViewById(R.id.purchase);
            Ordernumber = itemView.findViewById(R.id.Ordernumber);
            dates = itemView.findViewById(R.id.dates);
            product_name = itemView.findViewById(R.id.product_name);
            address = itemView.findViewById(R.id.address);
            Commodity = itemView.findViewById(R.id.Commodity);
            Actual = itemView.findViewById(R.id.Actual);

            rlall = itemView.findViewById(R.id.rlall);
            Fruits = itemView.findViewById(R.id.Fruits);
            Vegetables = itemView.findViewById(R.id.Vegetables);
            Printstatus= itemView.findViewById(R.id.Print_status);
        }
    }





    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void refresh(List<OrderListEntity.DataBean> datas) {
        this.dataBeans = datas;
        notifyDataSetChanged();
    }
}
