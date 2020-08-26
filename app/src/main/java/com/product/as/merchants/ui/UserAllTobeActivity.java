package com.product.as.merchants.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.product.as.merchants.R;
import com.product.as.merchants.SearchBluetoothActivity;
import com.product.as.merchants.adapter.AllToBeShippedAdapter;
import com.product.as.merchants.adapter.AllToBeShippedTypeAdapter;
import com.product.as.merchants.adapter.UserAllToBeShippedAdapter;
import com.product.as.merchants.adapter.UserAllToBeShippedTypeAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.api.AppInfo;
import com.product.as.merchants.base.BaseActivityTwo;
import com.product.as.merchants.entity.OrderEnsureEntity;

import com.product.as.merchants.entity.PlatformListEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.print.PrintQueue;
import com.product.as.merchants.printutil.PrinterWriter;
import com.product.as.merchants.printutil.PrinterWriter58mm;
import com.product.as.merchants.printutil.PrinterWriter80mm;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.util.ZXingUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAllTobeActivity extends BaseActivityTwo {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address)
    TextView address;

    @Bind(R.id.Ordernumber)
    TextView Ordernumber;
    @Bind(R.id.rcypic)
    RecyclerView rcypic;
    @Bind(R.id.rcytype)
    RecyclerView rcytype;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.copy)
    TextView copy;
    @Bind(R.id.times)
    TextView times;

    @Bind(R.id.Intotal)
    TextView Intotal;




    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.tv_right)
    TextView tvRight;



    private UserAllToBeShippedAdapter toBeShippedAdapter;
    private UserAllToBeShippedTypeAdapter toBeShippedTypeAdapter;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_all_to_be;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        List<PlatformListEntity.DataBean.ListBean> dataBeanList = (List<PlatformListEntity.DataBean.ListBean>) getIntent().getExtras().getSerializable("list");


        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvRight.setText("打印小票");
        tvRight.setOnClickListener(v -> {
            if (SharedPreferencesUtils.getInt(Constants.UPLOADPICTURE, 0) == 0) {
                if (TextUtils.isEmpty(AppInfo.btAddress)) {
                    ToastUtil.showToast(UserAllTobeActivity.this, "请连接蓝牙...");
                    startActivity(new Intent(UserAllTobeActivity.this, SearchBluetoothActivity.class));
                } else {
                    DialogUtils.getInstance().showSimpleDialog(UserAllTobeActivity.this, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                        utils.setCancelable(false);
                        Button cofim = contentView.findViewById(R.id.submit);
                        Button exit = contentView.findViewById(R.id.cancel);
                        TextView content = contentView.findViewById(R.id.content);
                        content.setText("确定进行打印?");
                        exit.setOnClickListener(v2 -> {
                            utils.close();
                        });

                        cofim.setOnClickListener(v2 -> {

                            ApiInterfaceTwo.ApiFactory.createApi().order_print_ticket(getIntent().getExtras().getString("group_no")).enqueue(new Callback<OrderEnsureEntity>() {
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

                                            printer.setAlignLeft();
                                            printer.setFontSize(0);
                                            printer.setEmphasizedOn();
                                            printer.print("配送码：" + getIntent().getExtras().getString("code"));
                                            printer.printLineFeed();
                                            printer.printLineFeed();
                                            printer.setFontSize(0);
                                            printer.setEmphasizedOn();
                                            printer.print(getIntent().getExtras().getString("deli_name"));
                                            printer.printLineFeed();
                                            printer.printLineFeed();
                                            printer.setFontSize(0);
                                            printer.setEmphasizedOn();
                                            printer.print(getIntent().getExtras().getString("deli_phone"));
                                            printer.setFontSize(0);
                                            printer.printLineFeed();
                                            printer.printLine();
                                            printer.setEmphasizedOff();
                                            printer.printLineFeed();
                                            printer.print("订单编号：" + getIntent().getExtras().getString("order_no"));
                                            printer.printLineFeed();
                                            printer.setAlignLeft();

                                            printer.print("支付时间：" + getIntent().getExtras().getString("pay_time"));
                                            printer.printLineFeed();
                                            printer.printLine();

                                            printer.setAlignLeft();
                                            printer.printInOneLine("收件人: ", getIntent().getExtras().getString("rec_name"), 0);
                                            printer.printLineFeed();

                                            printer.printInOneLine("电话: ", getIntent().getExtras().getString("rec_tel"), 0);
                                            printer.printLineFeed();

                                            printer.print("地址: " + getIntent().getExtras().getString("rec_add"));
                                            printer.printLineFeed();

                                            printer.print("区域:" + getIntent().getExtras().getString("rec_area"));
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
                                            printer.printInOneLine("件数：", getIntent().getExtras().getInt("count") + "", 0);
                                            printer.setAlignLeft();
                                            printer.printInOneLine("合计：", getIntent().getExtras().getString("string_order_balance") + ""
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
                startActivity(new Intent(UserAllTobeActivity.this, SearchBluetoothActivity.class));
            }
        });
        tvTitles.setText("订单详情");
        name.setText(getIntent().getExtras().getString("rec_name"));
        address.setText(getIntent().getExtras().getString("rec_add"));
        number.setText("共"+getIntent().getExtras().getInt("count")+"件");
        Ordernumber.setText("订单编号: " + getIntent().getExtras().getString("order_no"));
        Intotal.setText("共¥" + getIntent().getExtras().getString("real_balance"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcypic.setLayoutManager(layoutManager);
        copy.setOnClickListener(v -> {
            ZXingUtils.copyAddress(UserAllTobeActivity.this, getIntent().getExtras().getString("rec_name") + "   " + getIntent().getExtras().getString("rec_tel") + "   " + getIntent().getExtras().getString("rec_add"));
        });

        rcytype.setLayoutManager(new LinearLayoutManager(this));

        toBeShippedAdapter = new UserAllToBeShippedAdapter(UserAllTobeActivity.this, R.layout.item_tobeshipped, dataBeanList);
        rcypic.setAdapter(toBeShippedAdapter);

        toBeShippedTypeAdapter = new UserAllToBeShippedTypeAdapter(UserAllTobeActivity.this, R.layout.item_tobeshippedtype, dataBeanList);
        rcytype.setAdapter(toBeShippedTypeAdapter);
        times.setText(getIntent().getExtras().getString("pay_time"));

    }

}
