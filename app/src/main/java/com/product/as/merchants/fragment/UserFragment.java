package com.product.as.merchants.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.meikoz.core.base.BaseFragment;
import com.printer.command.EscCommand;
import com.printer.command.LabelCommand;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterface;

import com.product.as.merchants.bluetooth.BluetoothListActivity;
import com.product.as.merchants.bluetooth.DeviceConnFactoryManager;
import com.product.as.merchants.bluetooth.PrinterCommand;
import com.product.as.merchants.bluetooth.ThreadPool;
import com.product.as.merchants.entity.WholesalerInfoEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.AccountAssetsActivity;
import com.product.as.merchants.ui.AgentWebActivity;
import com.product.as.merchants.ui.MemberManagementActivity;
import com.product.as.merchants.ui.SettingActivity;
import com.product.as.merchants.util.GlideUtils;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.util.ZXingUtils;
import com.product.as.merchants.view.CircleImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends BaseFragment {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.headpic)
    CircleImageView headpic;
    @Bind(R.id.storename)
    TextView storename;
    @Bind(R.id.shop)
    TextView shop;
    int type = 0;


    @Override
    protected int getLayoutResource() {
        return R.layout.user_fragment;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) throws IOException {
        ivBack.setVisibility(View.GONE);
        tvTitles.setText("我的");
        ivRight.setBackgroundResource(R.mipmap.message);


        wholesalerinfo(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
    }

    public void wholesalerinfo(String sid) {
        ApiInterface.ApiFactory.createApi().wholesalerinfo(sid).enqueue(new Callback<WholesalerInfoEntity>() {
            @Override
            public void onResponse(Call<WholesalerInfoEntity> call, Response<WholesalerInfoEntity> response) {
                if (response.body().getFlag() == 1) {
                    storename.setText(response.body().getData().getName());
                    GlideUtils.loadImgWithGlide(response.body().getData().getLogo_url(), headpic);
                    shop.setText("主营:" + response.body().getData().getMajor());
                }
            }

            @Override
            public void onFailure(Call<WholesalerInfoEntity> call, Throwable t) {

            }
        });
    }


    @OnClick({R.id.Setting, R.id.My_release, R.id.transaction, R.id.User_agreement})
    public void onlick(View view) {
        switch (view.getId()) {
            case R.id.Setting:
                JumpActivityUtil.launchActivity(mContext, SettingActivity.class);
                break;
            case R.id.My_release:
                JumpActivityUtil.launchActivity(mContext, AccountAssetsActivity.class);
                break;
            case R.id.transaction:
                JumpActivityUtil.launchActivity(mContext, MemberManagementActivity.class);
                break;
            case R.id.User_agreement:
                Intent intent = new Intent(mContext, AgentWebActivity.class);
                startActivity(intent);

                break;
        }
    }


}

