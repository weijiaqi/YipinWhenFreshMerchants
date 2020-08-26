package com.product.as.merchants.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.CateListEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LecelOneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    List<CateListEntity.DataBean> rowsBeans;
    private OnMyItemClickListener myItemClickListener;

    public interface OnMyItemClickListener {
        void onItemClick(int id, String name, int positon);
    }

    public void setMyItemClickListener(OnMyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public LecelOneAdapter(Context context, List<CateListEntity.DataBean> rowsBeans) {
        this.context = context;
        this.rowsBeans = rowsBeans;
    }

    private int selectPosition;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView name;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemClick(rowsBeans.get(getAdapterPosition()).getID(), rowsBeans.get(getAdapterPosition()).getCata_name(), getAdapterPosition());
                    }
                }
            });


        }

        public void bind() {
            name.setText(rowsBeans.get(getAdapterPosition()).getCata_name());
            if (getAdapterPosition() == getSelectPosition()) {
                name.setBackgroundColor(context.getResources().getColor(R.color.color_1c));
                name.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                name.setBackgroundColor(context.getResources().getColor(R.color.white));
                name.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lievelone, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind();
    }

    @Override
    public int getItemCount() {
        return rowsBeans != null ? rowsBeans.size() : 0;
    }

}

