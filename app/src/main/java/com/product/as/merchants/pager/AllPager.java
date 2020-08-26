package com.product.as.merchants.pager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
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
import com.product.as.merchants.SearchBluetoothActivity;
import com.product.as.merchants.adapter.AllOrderAdapter;

import com.product.as.merchants.api.ApiInterfaceTwo;

import com.product.as.merchants.api.AppInfo;
import com.product.as.merchants.entity.OrderListsEntity;

import com.product.as.merchants.model.Constants;
import com.product.as.merchants.platform.PlatformOrderFragment;
import com.product.as.merchants.print.PrintQueue;
import com.product.as.merchants.printutil.PrinterWriter;
import com.product.as.merchants.printutil.PrinterWriter58mm;
import com.product.as.merchants.printutil.PrinterWriter80mm;
import com.product.as.merchants.ui.AllGroupActivity;
import com.product.as.merchants.ui.AllToBeActivity;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.util.UiUtils;
import com.product.as.merchants.view.MyDecoration;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPager extends BasePager implements NoticeObserver.Observer {
    RecyclerView rcyshaped;
    private RelativeLayout rldate;
    List<OrderListsEntity.DataBean> dataBeanList = new ArrayList<>();
    private TextView dates;

    private TimePickerView pvTime;
    private AllOrderAdapter allOrderAdapter;
    private RelativeLayout rlFruits;
    private int page = -1;
    int currentPosition = 0;
    private TextView count;
    EditText search;

    public AllPager(Context context) {
        super(context);
    }

    @Override
    public View initView(LayoutInflater layoutInflater) {
        NoticeObserver.getInstance().addObserver(this);
        View view = layoutInflater.inflate(R.layout.all_pager, null);
        rcyshaped = view.findViewById(R.id.rcyshaped);
        rlFruits = view.findViewById(R.id.rlFruits);
        rcyshaped.setLayoutManager(new LinearLayoutManager(context));
        rcyshaped.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));
        rldate = view.findViewById(R.id.rldate);
        dates = view.findViewById(R.id.date);
        search = view.findViewById(R.id.search);
        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()));
        count = view.findViewById(R.id.count);
        initTimePicker();
        rldate.setOnClickListener(v -> {
            pvTime.show();
        });


        rlFruits.setOnClickListener(v -> {
            if (SharedPreferencesUtils.getInt(Constants.UPLOADPICTURE, 0) == 0) {
                if (TextUtils.isEmpty(AppInfo.btAddress)) {
                    ToastUtil.showToast(context, "请连接蓝牙...");
                    context.startActivity(new Intent(context, SearchBluetoothActivity.class));
                } else {
                    DialogUtils.getInstance().showSimpleDialog(context, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                        utils.setCancelable(false);
                        Button cofim = contentView.findViewById(R.id.submit);
                        Button exit = contentView.findViewById(R.id.cancel);
                        TextView content = contentView.findViewById(R.id.content);
                        content.setText("确定进行打印?");
                        exit.setOnClickListener(v2 -> {
                            utils.close();
                        });

                        cofim.setOnClickListener(v2 -> {

                            new Process().run();
                            utils.close();
                        });
                    });


                }
            } else {
                context.startActivity(new Intent(context, SearchBluetoothActivity.class));
            }
        });

        order_lists(-1, dates.getText().toString(), search.getText().toString());

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

        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                order_lists(-1, dates.getText().toString(), search.getText().toString());
                UiUtils.hideSoftKeyboard((Activity) context);

            }
            return false;
        });
        return view;
    }

    @Override
    public <DATA> void initData(int type, DATA data) {

    }


    class Process implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < dataBeanList.size(); i++) {
                if (dataBeanList.get(i).getOrder_type() == 1) {
                    ArrayList<byte[]> data = new ArrayList<>();
                    try {
                        PrinterWriter printer;
                        printer = PrinterWriter58mm.TYPE_58 == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT) : new PrinterWriter80mm(PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT);
                        printer.setAlignCenter();
                        data.add(printer.getDataAndReset());
                        printer.setAlignLeft();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.setAlignCenter();
                        printer.setFontSize(1);
                        printer.print("聚鲜台");
                        printer.printLineFeed();
                        printer.setFontSize(0);
                        printer.printLineFeed();
                        printer.printLine();
                        printer.printLineFeed();
                        printer.printLine();
                        printer.printLineFeed();

                        printer.setAlignLeft();
                        printer.setFontSize(1);
                        printer.print("配送码：" + dataBeanList.get(i).getCode());
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.setFontSize(1);

                        printer.print(dataBeanList.get(i).getDeli_name());
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.setFontSize(1);

                        printer.print(dataBeanList.get(i).getDeli_phone());
                        printer.setFontSize(0);
                        printer.printLineFeed();
                        printer.printLine();
                        printer.printLineFeed();
                        printer.print("订单编号：" + dataBeanList.get(i).getOrder_no());
                        printer.printLineFeed();
                        printer.setAlignLeft();

                        printer.print("支付时间：" + dataBeanList.get(i).getPay_time());
                        printer.printLineFeed();
                        printer.printLine();

                        printer.setAlignLeft();
                        printer.printInOneLine("收件人: ", dataBeanList.get(i).getRec_name(), 0);
                        printer.printLineFeed();

                        printer.printInOneLine("电话: ", dataBeanList.get(i).getRec_tel(), 0);
                        printer.printLineFeed();

                        printer.print("地址: " + dataBeanList.get(i).getRec_add());
                        printer.printLineFeed();

                        printer.print("区域:" + dataBeanList.get(i).getRec_area());
                        printer.printLineFeed();
                        printer.printLine();

                        printer.setAlignLeft();
                        printer.printInOneLine("商品", "单价", 0);
                        printer.printLineFeed();
                        printer.setAlignLeft();
                        printer.printInOneLine(dataBeanList.get(i).getList().get(0).getProduct_name(), dataBeanList.get(i).getList().get(0).getPro_price() + "", 0);
                        printer.setAlignLeft();
                        printer.printInOneLine("姓名", "电话", "数量", 0);
                        printer.printLineFeed();
                        for (int j = 0; j < dataBeanList.get(i).getUser().size(); j++) {
                            printer.setAlignLeft();
                            printer.printInOneLine(dataBeanList.get(i).getUser().get(j).getNick_name(), dataBeanList.get(i).getUser().get(j).getMobile(), dataBeanList.get(i).getUser().get(j).getCount() + "", 0);
                            printer.printLineFeed();
                        }

                        printer.printLineFeed();
                        printer.printLine();
                        printer.setAlignLeft();
                        printer.printInOneLine("件数：", dataBeanList.get(i).getCount() + "", 0);
                        printer.setAlignLeft();
                        printer.printInOneLine("合计：", dataBeanList.get(i).getString_order_balance() + ""
                                , 0);
                        printer.printLineFeed();
                        printer.printLine();
                        printer.printLineFeed();
                        printer.setAlignCenter();
                        printer.print("果蔬生鲜一站式采购团购平台");
                        printer.printLineFeed();
                        printer.print("聚鲜台客服：" + "400-8271-368");
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.feedPaperCutPartial();
                        data.add(printer.getDataAndClose());
                        PrintQueue.getQueue(context).add(data);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {

                    }
                } else {
                    ArrayList<byte[]> data = new ArrayList<>();
                    try {
                        PrinterWriter printer;
                        printer = PrinterWriter58mm.TYPE_58 == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT) : new PrinterWriter80mm(PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT);
                        printer.setAlignCenter();
                        data.add(printer.getDataAndReset());
                        printer.setAlignLeft();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.setAlignCenter();

                        printer.setFontSize(1);
                        printer.print("聚鲜台");
                        printer.printLineFeed();
                        printer.setFontSize(0);
                        printer.printLineFeed();
                        printer.printLine();
                        printer.printLineFeed();
                        printer.printLine();
                        printer.printLineFeed();

                        printer.setAlignLeft();
                        printer.setFontSize(1);

                        printer.print("配送码：" + dataBeanList.get(i).getCode());
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.setFontSize(1);

                        printer.print(dataBeanList.get(i).getDeli_name());
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.setFontSize(1);

                        printer.print(dataBeanList.get(i).getDeli_phone());
                        printer.setFontSize(0);
                        printer.printLineFeed();
                        printer.printLine();
                        printer.printLineFeed();
                        printer.print("订单编号：" + dataBeanList.get(i).getOrder_no());
                        printer.printLineFeed();
                        printer.setAlignLeft();

                        printer.print("支付时间：" + dataBeanList.get(i).getPay_time());
                        printer.printLineFeed();
                        printer.printLine();

                        printer.setAlignLeft();
                        printer.printInOneLine("收件人: ", dataBeanList.get(i).getRec_name(), 0);
                        printer.printLineFeed();

                        printer.printInOneLine("电话: ", dataBeanList.get(i).getRec_tel(), 0);
                        printer.printLineFeed();

                        printer.print("地址: " + dataBeanList.get(i).getRec_add());
                        printer.printLineFeed();

                        printer.print("区域:" + dataBeanList.get(i).getRec_area());
                        printer.printLineFeed();
                        printer.printLine();

                        printer.setAlignLeft();
                        printer.printInOneLine("名称", "数量", "单价", 0);
                        printer.printLineFeed();
                        for (int j = 0; j < dataBeanList.get(i).getList().size(); j++) {
                            printer.setAlignLeft();
                            printer.printInOneLine(dataBeanList.get(i).getList().get(j).getProduct_name(), "x" + dataBeanList.get(i).getList().get(j).getSale_amount() + "", dataBeanList.get(i).getList().get(j).getPro_price() + "", 0);
                            printer.printLineFeed();
                        }

                        printer.printLine();
                        printer.setAlignLeft();
                        printer.printInOneLine("件数：", dataBeanList.get(i).getCount() + "", 0);
                        printer.setAlignLeft();
                        printer.printInOneLine("合计：", dataBeanList.get(i).getString_order_balance() + ""
                                , 0);
                        printer.printLineFeed();
                        printer.printLine();
                        printer.printLineFeed();
                        printer.setAlignCenter();
                        printer.print("果蔬生鲜一站式采购团购平台");
                        printer.printLineFeed();
                        printer.print("聚鲜台客服：" + "400-8271-368");
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.printLineFeed();
                        printer.feedPaperCutPartial();
                        data.add(printer.getDataAndClose());
                        PrintQueue.getQueue(context).add(data);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                    }
                }
            }


        }
    }

    public void order_lists(int page, String date, String search) {
        ApiInterfaceTwo.ApiFactory.createApi().order_lists(page, date, search).enqueue(new Callback<OrderListsEntity>() {
            @Override
            public void onResponse(Call<OrderListsEntity> call, Response<OrderListsEntity> response) {
                if (response.body().getFlag() == 1) {
                    count.setText("x" + response.body().getCount());
                    if (page == -1) {
                        dataBeanList.clear();
                    }
                    dataBeanList.addAll(response.body().getData());
                    if (allOrderAdapter == null) {
                        allOrderAdapter = new AllOrderAdapter(context, R.layout.item_allpager, dataBeanList);
                        rcyshaped.setAdapter(allOrderAdapter);
                    }
                    allOrderAdapter.notifyDataSetChanged();
                    allOrderAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            currentPosition = position;
                            if (dataBeanList.get(position).getOrder_type() == 0) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("count", dataBeanList.get(position).getCount());
                                bundle.putInt("order_type", dataBeanList.get(position).getOrder_type());
                                bundle.putString("real_balance", dataBeanList.get(position).getReal_balance() + "");
                                bundle.putString("rec_name", dataBeanList.get(position).getRec_name());
                                bundle.putString("rec_tel", dataBeanList.get(position).getRec_tel());
                                bundle.putString("order_no", dataBeanList.get(position).getOrder_no());
                                bundle.putString("rec_add", dataBeanList.get(position).getRec_add());
                                bundle.putString("group_no", dataBeanList.get(position).getGroup_no());
                                bundle.putString("code", dataBeanList.get(position).getCode());
                                bundle.putString("deli_name", dataBeanList.get(position).getDeli_name());
                                bundle.putString("deli_phone", dataBeanList.get(position).getDeli_phone());
                                bundle.putString("pay_time", dataBeanList.get(position).getPay_time());
                                bundle.putString("rec_area", dataBeanList.get(position).getRec_area());
                                bundle.putString("string_order_balance", dataBeanList.get(position).getString_order_balance());
                                bundle.putSerializable("list", (Serializable) dataBeanList.get(position).getList());
                                JumpActivityUtil.launchActivity(context, AllToBeActivity.class, bundle);
                            } else {

                                Bundle bundle = new Bundle();
                                bundle.putInt("count", dataBeanList.get(position).getCount());
                                bundle.putInt("order_type", dataBeanList.get(position).getOrder_type());
                                bundle.putString("real_balance", dataBeanList.get(position).getReal_balance() + "");
                                bundle.putString("rec_name", dataBeanList.get(position).getRec_name());
                                bundle.putString("rec_tel", dataBeanList.get(position).getRec_tel());
                                bundle.putString("rec_add", dataBeanList.get(position).getRec_add());
                                bundle.putString("group_no", dataBeanList.get(position).getGroup_no());
                                bundle.putString("order_no", dataBeanList.get(position).getOrder_no());
                                bundle.putString("code", dataBeanList.get(position).getCode());
                                bundle.putString("deli_name", dataBeanList.get(position).getDeli_name());
                                bundle.putString("deli_phone", dataBeanList.get(position).getDeli_phone());
                                bundle.putString("pay_time", dataBeanList.get(position).getPay_time());
                                bundle.putString("rec_area", dataBeanList.get(position).getRec_area());
                                bundle.putString("string_order_balance", dataBeanList.get(position).getString_order_balance());
                                bundle.putSerializable("list", (Serializable) dataBeanList.get(position).getList());
                                bundle.putSerializable("userlist", (Serializable) dataBeanList.get(position).getUser());
                                JumpActivityUtil.launchActivity(context, AllGroupActivity.class, bundle);
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
            public void onFailure(Call<OrderListsEntity> call, Throwable t) {

            }
        });
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(context, (date, v) -> {
            dates.setText(getTime(date));
            page = -1;
            order_lists(page, dates.getText().toString(), search.getText().toString());
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }
}
