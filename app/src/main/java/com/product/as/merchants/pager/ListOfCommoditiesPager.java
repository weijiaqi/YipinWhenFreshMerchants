package com.product.as.merchants.pager;

import android.app.Dialog;
import android.content.Context;
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
import com.meikoz.core.util.TimeUtils;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.DistributionAdapter;
import com.product.as.merchants.adapter.ListOfCommoditiesAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.DeliveryEntity;
import com.product.as.merchants.entity.OrderDeliveryEntity;
import com.product.as.merchants.model.Constants;
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

public class ListOfCommoditiesPager extends BasePager {
    SmartRefreshLayout refreshLayout;
    RecyclerView rcydistribution;
    private TimePickerView pvTime;
    TextView dates;
    private ListOfCommoditiesAdapter listOfCommoditiesAdapter;
    List<DeliveryEntity.DataBean> dataBeanList = new ArrayList<>();
    public ListOfCommoditiesPager(Context context) {
        super(context);
    }

    @Override
    public View initView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.activity_listof, null);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rcydistribution = view.findViewById(R.id.rcydistribution);
        dates = view.findViewById(R.id.date);
        initTimePicker();
        rcydistribution.setLayoutManager(new LinearLayoutManager(context));
        rcydistribution.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));
        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()));
        delivery_products(TimeUtils.millisDateString(System.currentTimeMillis()), String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")));
        dates.setOnClickListener(v -> {
            pvTime.show();
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            delivery_products(dates.getText().toString(), String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")));
            refreshLayout.finishRefresh();
        });
        return view;
    }

    @Override
    public <DATA> void initData(int type, DATA data) {

    }

    public void delivery_products(String day, String uid) {
        ApiInterfaceTwo.ApiFactory.createApi().delivery_products(day, uid).enqueue(new Callback<DeliveryEntity>() {
            @Override
            public void onResponse(Call<DeliveryEntity> call, Response<DeliveryEntity> response) {
                if (response.body().getFlag() == 1) {
                    rcydistribution.setVisibility(View.VISIBLE);
                    if (dataBeanList.size() > 0) {
                        dataBeanList.clear();
                    }
                    dataBeanList.addAll(response.body().getData());

                    if (listOfCommoditiesAdapter == null) {
                        listOfCommoditiesAdapter = new ListOfCommoditiesAdapter(context, R.layout.item_listofcommod, dataBeanList);
                        rcydistribution.setAdapter(listOfCommoditiesAdapter);
                    } else {
                        listOfCommoditiesAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeliveryEntity> call, Throwable t) {

            }
        });
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(context, (date, v) -> {
            dates.setText(getTime(date));
            delivery_products(getTime(date), String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")));
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
