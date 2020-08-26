package com.product.as.merchants.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsListEntity;
import com.product.as.merchants.entity.GoodsStatusEntity;
import com.product.as.merchants.util.GlideUtils;
import com.product.as.merchants.util.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommodityGoodsListUpdateAdapter extends RecyclerView.Adapter<CommodityGoodsListUpdateAdapter.MyViewHolder> implements View.OnClickListener  {
    private OnItemClickListener mOnItemClickListener = null;
    private List<GoodsListEntity.DataBean> dataBeans;
    private Context mContext;

    public CommodityGoodsListUpdateAdapter(Context context, List<GoodsListEntity.DataBean> list) {
        mContext = context;
        this.dataBeans = list;
    }
    @Override
    public CommodityGoodsListUpdateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payall, parent, false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(dataBeans.get(position).getProduct_name());
        holder.retailprice.setText( "结算价 : " + dataBeans.get(position).getBalance());
        holder.Singleweight.setText( "规格 : " + dataBeans.get(position).getUnit());
        holder.Specifications.setText("产地 : " + dataBeans.get(position).getOrigin());
        GlideUtils.loadBigImgWithGlide( dataBeans.get(position).getIcon(), holder.Icon);



        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView retailprice;
        TextView Singleweight;
        TextView Specifications;
        ImageView Icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            retailprice = itemView.findViewById(R.id.retailprice);
            Singleweight = itemView.findViewById(R.id.Singleweight);
            Specifications= itemView.findViewById(R.id.Specifications);
            Icon= itemView.findViewById(R.id.pic);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void refresh(List<GoodsListEntity.DataBean> datas) {
        this.dataBeans = datas;
        this.notifyItemRangeChanged(0, datas.size());
    }
}

