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


public class OrderHasUpdateAdapter extends RecyclerView.Adapter<OrderHasUpdateAdapter.MyViewHolder>  {


    private ButtonInterface  buttonInterface;
    private RelativeLayoutInterface relativeLayoutInterface;
    private OnItemClickListener mOnItemClickListener;
    private List<OrderListEntity.DataBean> dataBeans;
    private Context mContext;

    public OrderHasUpdateAdapter(Context context, List<OrderListEntity.DataBean> list) {
        mContext = context;
        this.dataBeans = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //引入item布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderhasend, parent, false);
        return new OrderHasUpdateAdapter.MyViewHolder(view);
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
        if (dataBeans.get(position).getIs_print_fruits() == 0) {
            holder.Fruits.setImageResource(R.mipmap.print);
        } else {
            holder.Fruits.setImageResource(R.mipmap.grayprint);
        }
        if (dataBeans.get(position).getIs_print_vegetables() == 0) {
            holder.Vegetables.setImageResource(R.mipmap.print);
        } else {
            holder.Vegetables.setImageResource(R.mipmap.grayprint);
        }
        holder.rlFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonInterface!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                    buttonInterface.onclick(view,position);
                }

            }
        });
        holder.rlVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(relativeLayoutInterface!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                    relativeLayoutInterface.onclick(view,position);
                }

            }
        });
        holder.rlall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                    mOnItemClickListener.onItemClick(view,position);
                }
            }
        });
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return dataBeans.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView purchase;
        TextView Ordernumber;
        TextView dates;
        TextView product_name;
        TextView address;
        TextView Commodity;
        TextView Actual;
        RelativeLayout rlFruits;
        RelativeLayout rlVegetables;
        RelativeLayout rlall;
        ImageView   Fruits;
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
            rlFruits = itemView.findViewById(R.id.rlFruits);
            rlVegetables = itemView.findViewById(R.id.rlVegetables);
            rlall= itemView.findViewById(R.id.rlall);
            Fruits=itemView.findViewById(R.id.Fruits);
            Vegetables=itemView.findViewById(R.id.Vegetables);
            Printstatus=itemView.findViewById(R.id.Print_status);
        }
    }

    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }
    public void ReleaySetOnclick(RelativeLayoutInterface buttonInterface){
        this.relativeLayoutInterface=buttonInterface;
    }
    public void SetItemOnClick(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }
    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        void onclick( View view,int position);
    }
    public interface RelativeLayoutInterface{
        void onclick( View view,int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void refresh(List<OrderListEntity.DataBean> datas) {
        this.dataBeans = datas;
        notifyDataSetChanged();
    }
}
