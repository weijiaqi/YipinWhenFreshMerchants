package com.product.as.merchants.pager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.util.TimeUtils;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.DeliveryOrderAdapter;

import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.DeliveryOrderEntity;

import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.ListDetailActivity;
import com.product.as.merchants.ui.PlatformConstActivity;
import com.product.as.merchants.util.JumpActivityUtil;

import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.view.MyDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverListPager extends BasePager {

    SmartRefreshLayout refreshLayout;
    RecyclerView rcydistribution;
    private TimePickerView pvTime;
    TextView dates;
    List<DeliveryOrderEntity.DataBean> dataBeanList=new ArrayList<>();
    private DeliveryOrderAdapter distributionAdapter;
    public DriverListPager(Context context) {
        super(context);
    }

    @Override
    public View initView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.activity_courier, null);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rcydistribution = view.findViewById(R.id.rcydistribution);

        dates = view.findViewById(R.id.date);
        initTimePicker();
        rcydistribution.setLayoutManager(new LinearLayoutManager(context));
        rcydistribution.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));
        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()));
        order_delivery(dates.getText().toString());

        dates.setOnClickListener(v -> {
            pvTime.show();
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            order_delivery(dates.getText().toString());
            refreshLayout.finishRefresh();
        });

        return view;
    }

    @Override
    public <DATA> void initData(int type, DATA data) {

    }


    public void order_delivery(String day) {
        ApiInterfaceTwo.ApiFactory.createApi().order_delivery(day).enqueue(new Callback<DeliveryOrderEntity>() {
            @Override
            public void onResponse(Call<DeliveryOrderEntity> call, Response<DeliveryOrderEntity> response) {
                if (response.body().getFlag() == 1) {

                    if (dataBeanList.size() > 0) {
                        dataBeanList.clear();
                    }
                    dataBeanList.addAll(response.body().getData());
                    if (distributionAdapter == null) {
                        distributionAdapter = new DeliveryOrderAdapter(context, R.layout.item_delivery, dataBeanList);
                        rcydistribution.setAdapter(distributionAdapter);
                    }
                    distributionAdapter.notifyDataSetChanged();

                    distributionAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            SharedPreferencesUtils.saveSp(Constants.DELIID,dataBeanList.get(position).getDeli_id()+"");
                            SharedPreferencesUtils.saveSp(Constants.DATES,dates.getText().toString());
                            JumpActivityUtil.launchActivity(context, PlatformConstActivity.class);
                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DeliveryOrderEntity> call, Throwable t) {

            }
        });
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(context, (date, v) -> {
            dates.setText(getTime(date));
            order_delivery(dates.getText().toString());
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
}
