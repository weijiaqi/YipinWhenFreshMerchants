package com.product.as.merchants.ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.product.as.merchants.R;
import com.product.as.merchants.SearchBluetoothActivity;
import com.product.as.merchants.api.AppInfo;
import com.product.as.merchants.base.BaseActivityTwo;
import com.product.as.merchants.bt.BluetoothActivity;

import com.product.as.merchants.print.PrintQueue;
import com.product.as.merchants.printutil.PrinterWriter;
import com.product.as.merchants.printutil.PrinterWriter58mm;
import com.product.as.merchants.printutil.PrinterWriter80mm;

import com.product.as.merchants.util.ToastUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivityTwo {


    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.button5)
    Button button5;

    boolean mBtEnable = true;
    int PERMISSION_REQUEST_COARSE_LOCATION=2;
    /**
     * bluetooth adapter
     */
    BluetoothAdapter mAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_test;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ButterKnife.bind(this);
        //6.0以上的手机要地理位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        button4.setOnClickListener(v->{
            startActivity(new Intent(TestActivity.this, SearchBluetoothActivity.class));
        });
        button5.setOnClickListener(v->{
            if (TextUtils.isEmpty(AppInfo.btAddress)){
                ToastUtil.showToast(TestActivity.this,"请连接蓝牙...");
                startActivity(new Intent(TestActivity.this,SearchBluetoothActivity.class));
            }else {

                ArrayList<byte[]> data = new ArrayList<>();
                try {
                    PrinterWriter printer;
                    printer= PrinterWriter58mm.TYPE_58 == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT) : new PrinterWriter80mm(PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT);
                    printer.setAlignCenter();
                    data.add(printer.getDataAndReset());


                    printer.setAlignLeft();
                    printer.printLine();
                    printer.printLineFeed();

                    printer.printLineFeed();
                    printer.setAlignCenter();
                    printer.setEmphasizedOn();
                    printer.setFontSize(1);
                    printer.print("好吃点你就多吃点");
                    printer.printLineFeed();
                    printer.setEmphasizedOff();
                    printer.printLineFeed();

                    printer.printLineFeed();
                    printer.setFontSize(0);
                    printer.setAlignCenter();
                    printer.print("订单编号：" + "546545645465456454");
                    printer.printLineFeed();

                    printer.setAlignCenter();
                    printer.print(new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                            .format(new Date(System.currentTimeMillis())));
                    printer.printLineFeed();
                    printer.printLine();

                    printer.printLineFeed();
                    printer.setAlignLeft();
                    printer.print("订单状态: " + "已接单");
                    printer.printLineFeed();
                    printer.print("用户昵称: " +"周末先生");
                    printer.printLineFeed();
                    printer.print("用餐人数: " + "10人");
                    printer.printLineFeed();
                    printer.print("用餐桌号:" + "A3" + "号桌");
                    printer.printLineFeed();
                    printer.print("预定时间：" + "2017-10-1 17：00");
                    printer.printLineFeed();
                    printer.print("预留时间：30分钟");
                    printer.printLineFeed();
                    printer.print("联系方式：" + "18094111545454");
                    printer.printLineFeed();
                    printer.printLine();
                    printer.printLineFeed();

                    printer.setAlignLeft();
                    printer.print("备注：" + "记得留位置");
                    printer.printLineFeed();
                    printer.printLine();

                    printer.printLineFeed();

                    printer.setAlignCenter();
                    printer.print("菜品信息");
                    printer.printLineFeed();
                    printer.setAlignCenter();
                    printer.printInOneLine("菜名", "数量", "单价", 0);
                    printer.printLineFeed();
                    for (int i = 0; i < 3; i++) {

                        printer.printInOneLine("干锅包菜", "X" + 3, "￥" + 30, 0);
                        printer.printLineFeed();
                    }
                    printer.printLineFeed();
                    printer.printLine();
                    printer.printLineFeed();
                    printer.setAlignLeft();
                    printer.printInOneLine("菜品总额：", "￥" + 100, 0);


                    printer.setAlignLeft();
                    printer.printInOneLine("优惠金额：", "￥" +"0.00"
                            , 0);
                    printer.printLineFeed();

                    printer.setAlignLeft();
                    printer.printInOneLine("订金/退款：", "￥" + "0.00"
                            , 0);
                    printer.printLineFeed();


                    printer.setAlignLeft();
                    printer.printInOneLine("总计金额：", "￥" +90, 0);
                    printer.printLineFeed();

                    printer.printLine();
                    printer.printLineFeed();
                    printer.setAlignCenter();
                    printer.print("谢谢惠顾，欢迎再次光临！");
                    printer.printLineFeed();
                    printer.printLineFeed();
                    printer.printLineFeed();
                    printer.feedPaperCutPartial();

                    data.add(printer.getDataAndClose());
                    PrintQueue.getQueue(getApplicationContext()).add(data);
                } catch (Exception e) {

                }
            }

        });
    }

}
