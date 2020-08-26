package com.product.as.merchants.ui;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.BluetoothUtil;
import com.product.as.merchants.util.SharedPreferencesUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListActivity extends BaseActivity {

    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.btn_goto_setting)
    Button mBtnSetting;
    @Bind(R.id.lv_paired_devices)
    ListView mLvPairedDevices;
    public static DeviceListAdapter mAdapter;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_list;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        tvTitles.setText("连接小票打印机");


        mAdapter = new DeviceListAdapter(this);
        mLvPairedDevices.setAdapter(mAdapter);
        mLvPairedDevices.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ListActivity.this,ToBeShippedActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
            finish();
            SharedPreferencesUtils.putInt(Constants.POSTION,position);
            mAdapter.notifyDataSetChanged();
        });
        mBtnSetting.setOnClickListener(view -> startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS)));

    }
    @Override
    protected void onResume() {
        super.onResume();
        fillAdapter();
    }


    class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

        public DeviceListAdapter(Context context) {
            super(context, 0);
        }

        @SuppressLint("MissingPermission")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BluetoothDevice device = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
            }

            TextView tvDeviceName = (TextView) convertView.findViewById(R.id.tv_device_name);

            tvDeviceName.setText(device.getName());

            return convertView;
        }
    }

    /**
     * 从所有已配对设备中找出打印设备并显示
     */
    private void fillAdapter() {
        //推荐使用 BluetoothUtil.getPairedPrinterDevices()
        List<BluetoothDevice> printerDevices = BluetoothUtil.getPairedDevices();
        mAdapter.clear();
        mAdapter.addAll(printerDevices);
        refreshButtonText(printerDevices);
    }


    private void refreshButtonText(List<BluetoothDevice> printerDevices) {
        if (printerDevices.size() > 0) {
            mBtnSetting.setVisibility(View.GONE);
        } else {
            mBtnSetting.setText("还未配对打印机，去设置");
        }
    }

}
