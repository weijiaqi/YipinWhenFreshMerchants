package com.product.as.merchants.platform;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meikoz.core.base.BaseFragment;
import com.meikoz.core.util.TimeUtils;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.MyAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderProcuresEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.PurchaseDetailsActivity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatformStockFragment extends BaseFragment implements NoticeObserver.Observer {
    @Bind(R.id.date)
    TextView dates;
    @Bind(R.id.rldate)
    RelativeLayout rldate;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    private TimePickerView pvTime;
    List<List<OrderProcuresEntity.DataBean.ListBean>> dataBeanList = new ArrayList<>();

    List<String> dataBeanList2 = new ArrayList<>();
    MyAdapter adapter;
    private int first=0;

    @Override
    protected int getLayoutResource() {
        return R.layout.platform_stock_fragment;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) throws IOException {
        ivBack.setVisibility(View.GONE);
        tvTitles.setText("备货");
        NoticeObserver.getInstance().addObserver(this);
        initTimePicker();

        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()));
        rldate.setOnClickListener(v -> {
            pvTime.show();
        });
        order_procure(dates.getText().toString());

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            order_procure(dates.getText().toString());
            refreshLayout.finishRefresh();
        });


        expandableListView.setOnGroupClickListener((expandableListView, arg1, groupPosition, arg3) -> {                    //组(一级列表)点击事件       默认return false;若设为true,表示点击列表不会展开
            // TODO Auto-generated method stub
            first = groupPosition;
            return false;
        });
    }

    public void order_procure(String day) {
        ApiInterfaceTwo.ApiFactory.createApi().order_procure(day).enqueue(new Callback<OrderProcuresEntity>() {
            @Override
            public void onResponse(Call<OrderProcuresEntity> call, Response<OrderProcuresEntity> response) {
                if (response.body().getFlag() == 1) {
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        if (dataBeanList.size() > 0) {
                            dataBeanList.clear();
                        }
                        if (dataBeanList2.size() > 0) {
                            dataBeanList2.clear();
                        }

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            dataBeanList2.add(response.body().getData().get(i).getSaler_name());

                            Collections.sort(response.body().getData().get(i).getList(), (o1, o2) -> {
                                return o2.getParent_id() - o1.getParent_id();
                            });
                            dataBeanList.add(response.body().getData().get(i).getList());
                        }
                        if (adapter == null) {
                            adapter = new MyAdapter(mContext, dataBeanList2, dataBeanList);
                        }
                        adapter.notifyDataSetChanged();
                        expandableListView.setAdapter(adapter);
                        expandableListView.expandGroup(first);
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView expandableListView, View v, int groupPosition, int childPosition, long id) {
                                Log.e("11111--------", dataBeanList.get(groupPosition).get(childPosition).getProduct_name());
                                Bundle bundle = new Bundle();
                                bundle.putString("date", dataBeanList.get(groupPosition).get(childPosition).getDay());
                                bundle.putString("products_id", dataBeanList.get(groupPosition).get(childPosition).getProducts_id() + "");
                                bundle.putString("sid", dataBeanList.get(groupPosition).get(childPosition).getSaler_id() + "");
                                JumpActivityUtil.launchActivity(mContext, PurchaseDetailsActivity.class, bundle);
                                return true;
                            }
                        });


                    }

                }
            }

            @Override
            public void onFailure(Call<OrderProcuresEntity> call, Throwable t) {

            }
        });
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(mContext, (date, v) -> {
            dates.setText(getTime(date));
            order_procure(getTime(date));

        })
                .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(view -> Log.i("pvTime", "onCancelClickListener"))
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public <T> void update(int what, T t) {
        if (what == Constants.BEIHUO) {
            order_procure(dates.getText().toString());

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }
}


