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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.util.TimeUtils;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.ShippedAdapter;
import com.product.as.merchants.adapter.ShippedUpdateAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.bluetooth.ThreadPool;
import com.product.as.merchants.entity.OrderListEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.GroupPurchaseActivity;
import com.product.as.merchants.ui.ToBeShippedActivity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.view.MyDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//待发货
public class ShippedPager extends BasePager implements NoticeObserver.Observer {
    RecyclerView rcyshaped;
    private ShippedAdapter shippedAdapter;
    List<OrderListEntity.DataBean> dataBeanList =new ArrayList<>();

    int page = 0;
    private TextView dates;
    private TextView count;
    private RelativeLayout rldate;
    private SmartRefreshLayout refreshLayout;
    private TimePickerView pvTime;
    int currentPosition=0;

    public ShippedPager(Context context) {
        super(context);
    }

    @Override
    public View initView(LayoutInflater layoutInflater) {
        NoticeObserver.getInstance().addObserver(this);

        View view = layoutInflater.inflate(R.layout.shiped_pager, null);
        rcyshaped = view.findViewById(R.id.rcyshaped);
        rldate = view.findViewById(R.id.rldate);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        count = view.findViewById(R.id.count);
        dates = view.findViewById(R.id.date);
        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()));

        rldate.setOnClickListener(v -> {
            pvTime.show();
        });

        rcyshaped.setLayoutManager(new LinearLayoutManager(context));
        rcyshaped.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));


        initTimePicker();
        orderlists(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, page, dates.getText().toString());



        rcyshaped.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    try {
                         currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
                        Log.e("=====currentPosition", "" + currentPosition);
                    } catch (Exception e) {
                    }
                }

            }
        });


        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            orderlists(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, page, dates.getText().toString());
            refreshLayout.finishRefresh();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            orderlists(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, page, dates.getText().toString());
            if (dataBeanList.size() >= 0) {
                rcyshaped.smoothScrollToPosition(dataBeanList.size());
            }
            refreshLayout.finishLoadMore();
        });

        return view;
    }

    @Override
    public <DATA> void initData(int type, DATA data) {

    }

    public void orderlists(String sid, int status, int page, String day) {
        ApiInterfaceTwo.ApiFactory.createApi().orderlists(sid, status, page, day).enqueue(new Callback<OrderListEntity>() {
            @Override
            public void onResponse(Call<OrderListEntity> call, Response<OrderListEntity> response) {
                if (response.body().getFlag() == 1) {
                    count.setText("待发货: " + response.body().getCount());
                    if (page == 0) {
                        dataBeanList.clear();
                    }
                    dataBeanList.addAll(response.body().getData());
                    if (shippedAdapter==null){
                        shippedAdapter=new ShippedAdapter(context,R.layout.item_shaped,dataBeanList);
                        rcyshaped.setAdapter(shippedAdapter);
                    }
                    shippedAdapter.notifyDataSetChanged();
                    shippedAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            currentPosition=position;
                            if (dataBeanList.get(position).getGroup_id() == 0) {
                                Bundle bundle = new Bundle();
                                bundle.putString("order_no", dataBeanList.get(position).getOrder_no());
                                bundle.putInt("statustype", 1);
                                JumpActivityUtil.launchActivity(context, ToBeShippedActivity.class, bundle);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("order_no", dataBeanList.get(position).getGroup_no());
                                bundle.putInt("statustype", 1);
                                JumpActivityUtil.launchActivity(context, GroupPurchaseActivity.class, bundle);
                            }
                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<OrderListEntity> call, Throwable t) {

            }
        });
    }

    @Override
    public <T> void update(int what, T t) {
       if (what == Constants.ALLREFRESH) {
           dataBeanList.get(currentPosition).setIs_print_ticket(1);
           shippedAdapter.notifyItemChanged(currentPosition);
        }
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(context, (date, v) -> {
            dates.setText(getTime(date));
            page = 0;

            orderlists(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, page, dates.getText().toString());

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
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }


}
