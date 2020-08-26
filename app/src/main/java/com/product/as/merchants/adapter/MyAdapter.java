package com.product.as.merchants.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderEnsureEntity;
import com.product.as.merchants.entity.OrderProcuresEntity;
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

public class MyAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mGroup;
    private List<List<OrderProcuresEntity.DataBean.ListBean>> mItemList;
    private final LayoutInflater mInflater;

    public MyAdapter(Context context, List<String> group, List<List<OrderProcuresEntity.DataBean.ListBean>> itemList) {
        this.mContext = context;
        this.mGroup = group;
        this.mItemList = itemList;
        mInflater = LayoutInflater.from(context);
    }

    //父项的个数
    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    //某个父项的子项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return mItemList.get(groupPosition).size();
    }

    //获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mGroup.get(groupPosition);
    }

    //获得某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemList.get(groupPosition).get(childPosition);
    }

    //父项的Id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    //获取父项的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_platformfirst, parent, false);
        }
        String group = mGroup.get(groupPosition);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.tv_group);
        ImageView iv_group = (ImageView) convertView.findViewById(R.id.iv_group);
        tvGroup.setText(group);
        //控制是否展开图标
        if (isExpanded) {
            iv_group.setImageResource(R.mipmap.expand_iv_up);
        } else {
            iv_group.setImageResource(R.mipmap.expand_iv_down);
        }
        return convertView;
    }

    //获取子项的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final OrderProcuresEntity.DataBean.ListBean child = mItemList.get(groupPosition).get(childPosition);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_platform, parent, false);
        }

        ImageView pic = convertView.findViewById(R.id.pic);
        GlideUtils.loadBigImgWithGlide(child.getPro_pic(), pic);
        TextView name = convertView.findViewById(R.id.name);
        name.setText(child.getProduct_name());
        TextView number = convertView.findViewById(R.id.number);
        number.setText("x" + child.getCount());

        TextView replenishment = convertView.findViewById(R.id.replenishment);
        if (child.getIs_replenish() == 1) {
            replenishment.setText("(补货)");
        }
        TextView region = convertView.findViewById(R.id.region);
        region.setText(child.getOrigin());
        TextView product_name = convertView.findViewById(R.id.product_name);
        product_name.setText(child.getUnit());

        TextView textView = convertView.findViewById(R.id.status);
        int status = child.getIs_choice();
        if (status == 0) {
            textView.setBackgroundResource(R.drawable.bg_12blue_border);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setText("确认备货");
        } else {
            textView.setBackgroundResource(R.drawable.bg_12empty_border);
            textView.setTextColor(mContext.getResources().getColor(R.color.color_27));
            textView.setText("已备货");
        }
        textView.setOnClickListener(c -> {
            if (status == 0) {
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
                        orderupdate_procure(child.getSaler_id() + "", child.getDay(), child.getProducts_id() + "", child.getIs_replenish());
                        utils.close();
                    });
                });

            }

        });

        return convertView;
    }

    //子项是否可选中,如果要设置子项的点击事件,需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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

