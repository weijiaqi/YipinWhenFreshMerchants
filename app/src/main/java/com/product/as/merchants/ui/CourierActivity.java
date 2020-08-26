package com.product.as.merchants.ui;

import android.app.Dialog;
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
import com.product.as.merchants.api.ApiInterfaceThree;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderDeliveryEntity;
import com.product.as.merchants.entity.OrderEnsureEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.view.MyDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

/**
 * 物流展示
 */
public class CourierActivity extends BaseActivity {

    @Bind(R.id.date)
    TextView dates;
    @Bind(R.id.rcydistribution)
    RecyclerView rcydistribution;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private TimePickerView pvTime;
    private DistributionAdapter distributionAdapter;
    List<OrderDeliveryEntity.DataBean> dataBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_courier;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setVisibility(View.GONE);
        tvTitles.setText("物流清单");
        initTimePicker();
        rcydistribution.setLayoutManager(new LinearLayoutManager(context));
        rcydistribution.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));
        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()));


        dates.setOnClickListener(v -> {
            pvTime.show();
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {

            refreshLayout.finishRefresh();
        });
    }



    public void orderensure(String sid, String group_no) {
        ApiInterfaceThree.ApiFactory.createApi().orderensure(sid, group_no).enqueue(new Callback<OrderEnsureEntity>() {
            @Override
            public void onResponse(Call<OrderEnsureEntity> call, Response<OrderEnsureEntity> response) {
                if (response.body().getFlag() == 1) {
                    ToastUtil.show(response.body().getMsg(), 200);

                }
            }

            @Override
            public void onFailure(Call<OrderEnsureEntity> call, Throwable t) {

            }
        });
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(context, (date, v) -> {
            dates.setText(getTime(date));

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
