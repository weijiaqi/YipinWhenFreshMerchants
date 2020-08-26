package com.product.as.merchants.pager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.product.as.merchants.adapter.PurchaselistAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsStatusEntity;
import com.product.as.merchants.entity.OrderEnsureEntity;
import com.product.as.merchants.entity.OrderProcureEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.PurchaseDetailsActivity;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.view.MyDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//采购清单
public class PurchaselistPager extends BasePager  implements NoticeObserver.Observer{
    private TextView dates;
    private RelativeLayout rldate;
    private RecyclerView rcyPurchase;
    private TimePickerView pvTime;
    private SmartRefreshLayout refreshLayout;


    List<OrderProcureEntity.DataBean> dataBeanList = new ArrayList<>();
    List<OrderProcureEntity.DataBean> dataBeanList1 = new ArrayList<>();
    List<OrderProcureEntity.DataBean> dataBeanList2 = new ArrayList<>();
    private PurchaselistAdapter purchaselistAdapter;

    public PurchaselistPager(Context context) {
        super(context);
    }

    @Override
    public View initView(LayoutInflater layoutInflater) {
        NoticeObserver.getInstance().addObserver(this);
        initTimePicker();
        View view = layoutInflater.inflate(R.layout.purchaselist_pager, null);
        dates = view.findViewById(R.id.date);
        rldate = view.findViewById(R.id.rldate);
        rcyPurchase = view.findViewById(R.id.rcyPurchase);
        refreshLayout = view.findViewById(R.id.refreshLayout);



        rcyPurchase.setLayoutManager(new LinearLayoutManager(context));
        rcyPurchase.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));
        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()));
        rldate.setOnClickListener(v -> {
            pvTime.show();
        });
        orderprocure(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")),dates.getText().toString());
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            orderprocure(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), dates.getText().toString());
            refreshLayout.finishRefresh();
        });


//        rlFruits.setOnClickListener(v -> {
//            for (int i = 0; i < dataBeanList.size(); i++) {
//                if (dataBeanList.get(i).getParent_id() == 1) {
//                    dataBeanList1.add(dataBeanList.get(i));
//                }
//            }
//            NoticeObserver.getInstance().notifyObservers(Constants.Chuandi, dataBeanList1);
//        });

//        rlVegetables.setOnClickListener(v -> {
//            for (int i = 0; i < dataBeanList.size(); i++) {
//                if (dataBeanList.get(i).getParent_id() != 1) {
//                    dataBeanList2.add(dataBeanList.get(i));
//                }
//            }
//            NoticeObserver.getInstance().notifyObservers(Constants.Chuandi3,dataBeanList2);
//        });
        return view;
    }

    @Override
    public <DATA> void initData(int type, DATA data) {

    }

    public void orderprocure(String sid, String day) {
        ApiInterfaceTwo.ApiFactory.createApi().orderprocure(sid, day).enqueue(new Callback<OrderProcureEntity>() {
            @Override
            public void onResponse(Call<OrderProcureEntity> call, Response<OrderProcureEntity> response) {
                if (response.body().getFlag() == 1) {
                    if (response.body().getData() != null) {
                        if (dataBeanList.size() > 0) {
                            dataBeanList.clear();
                        }
                        dataBeanList.addAll(response.body().getData());
                        Collections.sort(dataBeanList, (o1, o2) -> {

                            return o2.getParent_id()-o1.getParent_id();

                        });
                        if (purchaselistAdapter == null) {
                            purchaselistAdapter = new PurchaselistAdapter(context, R.layout.item_purchaselist, dataBeanList);
                            rcyPurchase.setAdapter(purchaselistAdapter);
                        }
                        purchaselistAdapter.notifyDataSetChanged();
                        purchaselistAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                                Bundle bundle=new Bundle();
                                bundle.putString("date",dataBeanList.get(position).getDay());
                                bundle.putString("products_id",dataBeanList.get(position).getProducts_id()+"");
                                JumpActivityUtil.launchActivity(context,PurchaseDetailsActivity.class,bundle);
                            }

                            @Override
                            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                                return false;
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderProcureEntity> call, Throwable t) {

            }
        });
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(context, (date, v) -> {
            dates.setText(getTime(date));
            orderprocure(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), getTime(date));
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
        if (what==Constants.BEIHUO){
            orderprocure(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), dates.getText().toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }
}
