package com.product.as.merchants.base;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangjie on 2017/9/4.
 * Email:githubgrr@163.com
 */

public abstract class BaseRecycleViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected RecycleViewItemListener itemListener;
    protected List<T> datas = new ArrayList<T>();


    public List<T> getDatas() {
        if (datas == null) {
            datas = new ArrayList<T>();
        }
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    /**
     * 清除所有集合中的数据并更新listview
     */
    public void clearAndNotify() {
        datas.clear();
        notifyDataSetChanged();
    }

    public void setItemListener(RecycleViewItemListener listener) {
        this.itemListener = listener;
    }
}
