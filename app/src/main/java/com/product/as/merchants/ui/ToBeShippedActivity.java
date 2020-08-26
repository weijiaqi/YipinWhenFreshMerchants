package com.product.as.merchants.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.base.BaseActivity;
import com.meikoz.core.util.TimeUtils;
import com.printer.command.EscCommand;
import com.printer.command.LabelCommand;
import com.product.as.merchants.R;
import com.product.as.merchants.SearchBluetoothActivity;
import com.product.as.merchants.adapter.SelectDriverAdapter;
import com.product.as.merchants.adapter.ToBeShippedAdapter;
import com.product.as.merchants.adapter.ToBeShippedTypeAdapter;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.api.AppInfo;
import com.product.as.merchants.base.BaseActivityTwo;

import com.product.as.merchants.entity.ExpressListEntity;

import com.product.as.merchants.entity.OrderEnsureEntity;
import com.product.as.merchants.entity.OrderInfoEntity;
import com.product.as.merchants.entity.OrderUpdateEntity;
import com.product.as.merchants.model.Constants;


import com.product.as.merchants.print.PrintQueue;
import com.product.as.merchants.printutil.PrinterWriter;
import com.product.as.merchants.printutil.PrinterWriter58mm;
import com.product.as.merchants.printutil.PrinterWriter80mm;

import com.product.as.merchants.util.CustomPopWindow;

import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.PermissionUtil;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.util.ZXingUtils;
import com.product.as.merchants.util.permission.PermissionFail;
import com.product.as.merchants.util.permission.PermissionSuccess;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//待发货详情
public class ToBeShippedActivity extends BaseActivityTwo {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.update)
    TextView update;
    @Bind(R.id.Ordernumber)
    TextView Ordernumber;
    @Bind(R.id.rcypic)
    RecyclerView rcypic;
    @Bind(R.id.rcytype)
    RecyclerView rcytype;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.times)
    TextView times;
    @Bind(R.id.express)
    TextView express;
    @Bind(R.id.Intotal)
    TextView Intotal;

    @Bind(R.id.zixingcode)
    EditText zixingcode;
    @Bind(R.id.llexpress)
    RelativeLayout llexpress;
    @Bind(R.id.Driver)
    TextView Driver;
    @Bind(R.id.selectDriver)
    RelativeLayout selectDriver;
    @Bind(R.id.viewvs)
    View viewvs;
    @Bind(R.id.Driverphone)
    TextView Driverphone;
    @Bind(R.id.rlDriverphone)
    RelativeLayout rlDriverphone;
    @Bind(R.id.llexpressname)
    RelativeLayout llexpressname;
    @Bind(R.id.expressname)
    EditText expressname;
    @Bind(R.id.viewsexpress)
    View viewsexpress;
    @Bind(R.id.cofim)
    TextView cofim;
    BluetoothAdapter mAdapter;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.tv_right)
    TextView tvRight;

    @Bind(R.id.textback)
    TextView textback;
    @Bind(R.id.Orderremark)
    TextView Orderremark;
    @Bind(R.id.Receiving)
    TextView Receiving;

    private ToBeShippedAdapter toBeShippedAdapter;
    private ToBeShippedTypeAdapter toBeShippedTypeAdapter;
    List<OrderInfoEntity.DataBean.ListBean> dataBeanList = new ArrayList<>();

    private CustomPopWindow popWindow;

    private int Delivery = 1;
    private OrderInfoEntity.DataBean dataBean;
    private static final int CAMERA = 3;
    private static final int REQUEST_CODE = 0x001;
    private SelectDriverAdapter selectDriverAdapter;
    private List<ExpressListEntity.DataBean> dataBeanLists;
    private String deli_id;
    private int page = 0;


    int PERMISSION_REQUEST_COARSE_LOCATION = 2;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_to_be_shipped;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {


        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvRight.setText("打印小票");
        tvRight.setOnClickListener(v -> {

            if (SharedPreferencesUtils.getInt(Constants.UPLOADPICTURE, 0) == 0) {
                if (TextUtils.isEmpty(AppInfo.btAddress)) {
                    ToastUtil.showToast(ToBeShippedActivity.this, "请连接蓝牙...");
                    startActivity(new Intent(ToBeShippedActivity.this, SearchBluetoothActivity.class));
                } else {

                    DialogUtils.getInstance().showSimpleDialog(ToBeShippedActivity.this, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                        utils.setCancelable(false);
                        Button cofim = contentView.findViewById(R.id.submit);
                        Button exit = contentView.findViewById(R.id.cancel);
                        TextView content = contentView.findViewById(R.id.content);
                        content.setText("确定进行打印?");
                        exit.setOnClickListener(v2 -> {
                            utils.close();
                        });

                        cofim.setOnClickListener(v2 -> {
                            ApiInterfaceTwo.ApiFactory.createApi().update_print_ticket(dataBean.getGroup_no(), dataBean.getSaler_id() + "").enqueue(new Callback<OrderEnsureEntity>() {
                                @Override
                                public void onResponse(Call<OrderEnsureEntity> call, Response<OrderEnsureEntity> response) {
                                    if (response.body().getFlag() == 1) {
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
                                            printer.setEmphasizedOn();
                                            printer.setFontSize(0);
                                            printer.print("聚鲜台");
                                            printer.printLineFeed();
                                            printer.printLine();
                                            printer.setEmphasizedOff();
                                            printer.printLineFeed();

                                            printer.setFontSize(0);
                                            printer.setAlignLeft();
                                            printer.print("订单编号：" + dataBean.getGroup_no());
                                            printer.printLineFeed();
                                            printer.setAlignLeft();

                                            printer.print("支付时间：" + dataBean.getPay_time());
                                            printer.printLineFeed();
                                            printer.printLine();

                                            printer.setAlignLeft();
                                            printer.printInOneLine("收件人: ", dataBean.getRec_name(), 0);
                                            printer.printLineFeed();

                                            printer.printInOneLine("电话: ", dataBean.getRec_tel(), 0);
                                            printer.printLineFeed();

                                            printer.print("地址: " + dataBean.getRec_add());
                                            printer.printLineFeed();

                                            printer.print("区域:" + dataBean.getRec_area());
                                            printer.printLineFeed();
                                            printer.printLine();

                                            printer.setAlignLeft();
                                            printer.printInOneLine("名称", "数量", "单价", 0);
                                            printer.printLineFeed();
                                            for (int i = 0; i < dataBeanList.size(); i++) {
                                                printer.setAlignLeft();
                                                printer.printInOneLine(dataBeanList.get(i).getProduct_name(), "x" + dataBeanList.get(i).getSale_amount() + "", dataBeanList.get(i).getPro_price() + "", 0);
                                                printer.printLineFeed();
                                            }

                                            printer.printLine();
                                            printer.setAlignLeft();
                                            printer.printInOneLine("件数：", dataBean.getCount() + "", 0);
                                            printer.setAlignLeft();
                                            printer.printInOneLine("合计：", dataBean.getOrder_price() + ""
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
                                            printer.feedPaperCutPartial();
                                            data.add(printer.getDataAndClose());
                                            PrintQueue.getQueue(getApplicationContext()).add(data);
                                            if (getIntent().getExtras().getInt("statustype") == 1) {
                                                NoticeObserver.getInstance().notifyObservers(Constants.ALLREFRESH);
                                            } else if (getIntent().getExtras().getInt("statustype") == 2) {
                                                NoticeObserver.getInstance().notifyObservers(Constants.ALLREFRESH2);
                                            } else {
                                                NoticeObserver.getInstance().notifyObservers(Constants.ALLREFRESH3);
                                            }

                                        } catch (Exception e) {

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderEnsureEntity> call, Throwable t) {

                                }
                            });

                            utils.close();
                        });
                    });






                }


            }else {
                startActivity(new Intent(ToBeShippedActivity.this, SearchBluetoothActivity.class));
            }
        });
        switch (getIntent().getExtras().getInt("statustype")) {
            case 1:
                tvTitles.setText("待发货");
                break;
            case 2:
                tvTitles.setText("已发货");
                break;
            case 3:
                tvTitles.setText("已完结");
                break;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcypic.setLayoutManager(layoutManager);


        rcytype.setLayoutManager(new LinearLayoutManager(this));
        orderinfo(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), getIntent().getExtras().getString("order_no"));

        //6.0以上的手机要地理位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }

    }


    public void orderinfo(String sid, String order_no) {
        ApiInterfaceTwo.ApiFactory.createApi().orderinfo(sid, order_no).enqueue(new Callback<OrderInfoEntity>() {
            @Override
            public void onResponse(Call<OrderInfoEntity> call, Response<OrderInfoEntity> response) {
                if (response.body().getFlag() == 1) {
                    dataBean = response.body().getData();
                    name.setText(response.body().getData().getRec_name() + "   " + response.body().getData().getRec_tel());
                    address.setText(response.body().getData().getRec_add());
                    Ordernumber.setText("订单号：" + response.body().getData().getOrder_no());
                    number.setText("共" + response.body().getData().getCount() + "件");
                    Intotal.setText("共¥" + response.body().getData().getOrder_price());
                    Receiving.setText(dataBean.getRec_area());
                    Orderremark.setText(response.body().getData().getRemark());
                    if (response.body().getData().getDeli_status().equals("1")) {
                        Delivery = 1;
                        express.setText("快递");
                        switch (getIntent().getExtras().getInt("statustype")) {
                            case 1:
                                llexpressname.setVisibility(View.VISIBLE);
                                viewsexpress.setVisibility(View.VISIBLE);
                                llexpress.setVisibility(View.VISIBLE);
                                selectDriver.setVisibility(View.GONE);
                                viewvs.setVisibility(View.GONE);
                                rlDriverphone.setVisibility(View.GONE);
                                break;
                            case 2:
                                cofim.setVisibility(View.GONE);
                                llexpressname.setVisibility(View.GONE);
                                viewsexpress.setVisibility(View.GONE);
                                llexpress.setVisibility(View.GONE);
                                selectDriver.setVisibility(View.GONE);
                                viewvs.setVisibility(View.GONE);
                                rlDriverphone.setVisibility(View.GONE);
                                break;
                            case 3:
                                cofim.setVisibility(View.GONE);
                                llexpressname.setVisibility(View.GONE);
                                viewsexpress.setVisibility(View.GONE);
                                llexpress.setVisibility(View.GONE);
                                selectDriver.setVisibility(View.GONE);
                                viewvs.setVisibility(View.GONE);
                                rlDriverphone.setVisibility(View.GONE);
                                break;
                        }
                    } else {
                        express.setText("物流");
                        Delivery = 2;
                        switch (getIntent().getExtras().getInt("statustype")) {
                            case 1:

                                selectDriver.setVisibility(View.VISIBLE);
                                llexpress.setVisibility(View.GONE);
                                viewvs.setVisibility(View.GONE);
                                rlDriverphone.setVisibility(View.GONE);
                                llexpressname.setVisibility(View.GONE);
                                viewsexpress.setVisibility(View.GONE);
                                break;
                            case 2:
                                cofim.setVisibility(View.GONE);
                                selectDriver.setVisibility(View.GONE);
                                llexpress.setVisibility(View.GONE);
                                viewvs.setVisibility(View.GONE);
                                rlDriverphone.setVisibility(View.GONE);
                                llexpressname.setVisibility(View.GONE);
                                viewsexpress.setVisibility(View.GONE);
                                break;
                            case 3:
                                cofim.setVisibility(View.GONE);
                                selectDriver.setVisibility(View.GONE);
                                llexpress.setVisibility(View.GONE);
                                viewvs.setVisibility(View.GONE);
                                rlDriverphone.setVisibility(View.GONE);
                                llexpressname.setVisibility(View.GONE);
                                viewsexpress.setVisibility(View.GONE);
                                break;
                        }

                    }
                    switch (response.body().getData().getOrder_status()) {
                        case 0:
                            status.setText("待付款");
                            break;
                        case 1:
                            status.setText("待发货");
                            break;
                        case 2:
                            status.setText("待收货");
                            break;
                        case 3:
                            status.setText("已完成");
                            break;
                        case 4:
                            status.setText("支付过期");
                            break;
                        case 5:
                            status.setText("取消订单");
                            break;
                    }
                    dataBeanList.addAll(response.body().getData().getList());

                    toBeShippedAdapter = new ToBeShippedAdapter(ToBeShippedActivity.this, R.layout.item_tobeshipped, dataBeanList);
                    rcypic.setAdapter(toBeShippedAdapter);

                    toBeShippedTypeAdapter = new ToBeShippedTypeAdapter(ToBeShippedActivity.this, R.layout.item_tobeshippedtype, dataBeanList);
                    rcytype.setAdapter(toBeShippedTypeAdapter);
                    times.setText(response.body().getData().getPay_time());

                }
            }

            @Override
            public void onFailure(Call<OrderInfoEntity> call, Throwable t) {

            }
        });
    }


    @OnClick({R.id.zxing, R.id.cofim, R.id.selectDriver, R.id.copy})
    public void onclick(View view) {
        switch (view.getId()) {


            case R.id.zxing:
                PermissionUtil.needPermission(this, CAMERA, Manifest.permission.CAMERA);
                break;
            //    case R.id.cofim:
//
//                if (express.getText().toString().length() == 1) {
//                    ToastUtil.show("请选择发货方式！", 25);
//                    return;
//                }
//
//                if (Delivery == 1) {  //快递
//                    if (TextUtils.isEmpty(expressname.getText().toString())) {
//                        ToastUtil.show("输入快递公司名称！", 25);
//                        return;
//                    }
//                    if (TextUtils.isEmpty(zixingcode.getText().toString())) {
//                        ToastUtil.show("输入或扫一扫快递单号！", 25);
//                        return;
//                    }
//                    orderupdatedriver(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), dataBean.getGroup_no(), expressname.getText().toString(), zixingcode.getText().toString(), 1, String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")), String.valueOf(SharedPreferencesUtils.getSp(Constants.USERNAME, "")), String.valueOf(SharedPreferencesUtils.getSp(Constants.TEL, "")));
//                } else {
//                    if (Driver.getText().toString().length() == 1) {
//                        ToastUtil.show("请选择司机！", 25);
//                        return;
//                    }
//                    orderupdatedriver2(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), dataBean.getGroup_no(), deli_id, Driver.getText().toString(), Driverphone.getText().toString(), 2, String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")), String.valueOf(SharedPreferencesUtils.getSp(Constants.USERNAME, "")), String.valueOf(SharedPreferencesUtils.getSp(Constants.TEL, "")));
//                }
//                break;
            case R.id.selectDriver:
                View contentView = LayoutInflater.from(this).inflate(R.layout.layout_recycler_view, null);
                //处理popWindow 显示内容
                initPopView(contentView);
                popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                        .setBgDarkAlpha(0.7f) // 控制亮度
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                        .setOnDissmissListener(() -> Log.e("TAG", "onDismiss"))
                        .create()
                        .showAtLocation(ll, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.copy:
                ZXingUtils.copyAddress(ToBeShippedActivity.this, dataBean.getRec_name() + "   " + dataBean.getRec_tel() + "   " + dataBean.getRec_add());
                break;
        }
    }


    @PermissionSuccess(requestCode = CAMERA)
    private void grantPermissionSuccess() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @PermissionFail(requestCode = CAMERA)
    private void grantPersmissionFail() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    //处理扫描结果（在界面上显示）
                    if (null != data) {
                        Bundle bundle = data.getExtras();
                        if (bundle == null) {
                            return;
                        }
                        if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                            String result = bundle.getString(CodeUtils.RESULT_STRING);
                            zixingcode.setText(result);
                        } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                            Toast.makeText(ToBeShippedActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;

            }
        }
    }


    private void initPopView(View view) {
        ApiInterface.ApiFactory.createApi().expresslist(page, Receiving.getText().toString()).enqueue(new Callback<ExpressListEntity>() {
            @Override
            public void onResponse(Call<ExpressListEntity> call, Response<ExpressListEntity> response) {
                if (response.body().getFlag() == 1) {
                    dataBeanLists = response.body().getData();
                    selectDriverAdapter = new SelectDriverAdapter(ToBeShippedActivity.this, R.layout.item_selectdriver, dataBeanLists);
                    RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ToBeShippedActivity.this));
                    recyclerView.setAdapter(selectDriverAdapter);
                    selectDriverAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            Driver.setText(dataBeanLists.get(position).getNick_name());
                            viewvs.setVisibility(View.VISIBLE);
                            rlDriverphone.setVisibility(View.VISIBLE);
                            Driverphone.setText(dataBeanLists.get(position).getMobile());
                            popWindow.dissmiss();
                            deli_id = dataBeanLists.get(position).getUid() + "";
                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ExpressListEntity> call, Throwable t) {

            }
        });
    }
}
