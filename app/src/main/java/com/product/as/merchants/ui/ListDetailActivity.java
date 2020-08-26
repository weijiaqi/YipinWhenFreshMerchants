package com.product.as.merchants.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.base.BaseActivity;
import com.meikoz.core.util.TimeUtils;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.DistributionAdapter;
import com.product.as.merchants.adapter.ListDetailAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderDeliveryEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.view.MyDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDetailActivity extends BaseActivity implements NoticeObserver.Observer {

    @Bind(R.id.date)
    TextView dates;
    @Bind(R.id.rcydistribution)
    RecyclerView rcydistribution;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    private ListDetailAdapter distributionAdapter;
    List<OrderDeliveryEntity.DataBean> dataBeanList = new ArrayList<>();
    private TimePickerView pvTime;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_list_detail;
    }


    @Override
    protected void onInitialization(Bundle bundle) throws IOException {

        ivBack.setOnClickListener(v->{finish();});
        tvTitles.setText("配送清单");
        NoticeObserver.getInstance().addObserver(this);
        initTimePicker();
        rcydistribution.setLayoutManager(new LinearLayoutManager(context));
        rcydistribution.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));
        dates.setText(getIntent().getExtras().getString("dates"));
        orderdelivery(dates.getText().toString(),getIntent().getExtras().getString("deli_id"));

        dates.setOnClickListener(v -> {
            pvTime.show();
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            orderdelivery(dates.getText().toString(),getIntent().getExtras().getString("deli_id"));
            refreshLayout.finishRefresh();
        });
    }


    public void orderdelivery(String day, String deli_id) {
        ApiInterfaceTwo.ApiFactory.createApi().orderdelivery(day, deli_id).enqueue(new Callback<OrderDeliveryEntity>() {
            @Override
            public void onResponse(Call<OrderDeliveryEntity> call, Response<OrderDeliveryEntity> response) {
                if (response.body().getFlag() == 1) {
                    rcydistribution.setVisibility(View.VISIBLE);
                    if (dataBeanList.size() > 0) {
                        dataBeanList.clear();
                    }
                    dataBeanList.addAll(response.body().getData());
                    if (distributionAdapter == null) {
                        distributionAdapter = new ListDetailAdapter(context, R.layout.item_listdetail, dataBeanList);
                        rcydistribution.setAdapter(distributionAdapter);
                    }
                    distributionAdapter.notifyDataSetChanged();

                    distributionAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            if (dataBeanList.get(position).getStatus() == 2) {

                                Bundle bundle = new Bundle();
                                bundle.putString("group_no", dataBeanList.get(position).getGroup_no());
                                bundle.putString("rec_name", dataBeanList.get(position).getRec_name());
                                bundle.putString("address", dataBeanList.get(position).getRec_add());
                                bundle.putString("number", dataBeanList.get(position).getDeli_com());
                                bundle.putString("sale_id", dataBeanList.get(position).getSaler_id() + "");
                                JumpActivityUtil.launchActivity(context, DeliveryDetailsActivity.class, bundle);

                            } else {

                            }
                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });
                } else {
                    rcydistribution.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OrderDeliveryEntity> call, Throwable t) {

            }
        });
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(context, (date, v) -> {
            dates.setText(getTime(date));
            orderdelivery(getTime(date), getIntent().getExtras().getString("deli_id"));
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
        if (what == Constants.RENSURE) {
            orderdelivery(dates.getText().toString(), getIntent().getExtras().getString("deli_id"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }


}
