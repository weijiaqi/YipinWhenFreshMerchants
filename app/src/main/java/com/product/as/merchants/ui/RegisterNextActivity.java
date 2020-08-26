package com.product.as.merchants.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.meikoz.core.util.MD5Util;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.entity.SendSmsEntity;
import com.product.as.merchants.entity.SignupEntity;
import com.product.as.merchants.util.CountDownButtonHelper;
import com.product.as.merchants.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNextActivity extends BaseActivity {

    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.code)
    TextView code;
    @Bind(R.id.edWriteCode)
    EditText edWriteCode;
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.cofimpwd)
    EditText cofimpwd;
    private CountDownButtonHelper countHelper;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register_next;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        countHelper = new CountDownButtonHelper(this, code, "(60秒)", 60, 1, CountDownButtonHelper.TYPE_USRE_LOGIN);
        tvTitles.setText("商家注册");
        String mobile = getIntent().getExtras().getString("phone");
        phone.setText(mobile.substring(0, 3) + "XXXXX" + mobile.substring(7, mobile.length()));
        GETCODE(mobile);
    }

    public void GETCODE(String mobile) {
        ApiInterface.ApiFactory.createApi().sendsms(mobile).enqueue(new Callback<SignupEntity>() {
            @Override
            public void onResponse(Call<SignupEntity> call, Response<SignupEntity> response) {
                if (response.body().getFlag() == 1) {
                    countHelper.start();
                    ToastUtil.show(getText(R.string.send_success), 25);
                    return;
                } else {
                    ToastUtil.showShort(response.body().getMsg());
                    return;
                }
            }

            @Override
            public void onFailure(Call<SignupEntity> call, Throwable t) {

            }
        });
    }


    @OnClick({R.id.cofim})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.cofim:
                if (TextUtils.isEmpty(edWriteCode.getText().toString())) {
                    ToastUtil.show("请输入验证码！", 25);
                    return;
                }
                if (TextUtils.isEmpty(pwd.getText().toString())) {
                    ToastUtil.show("请输入密码！", 25);
                    return;
                }
                if (pwd.getText().toString().length() < 8) {
                    ToastUtil.show("密码最小位数为8位！", 25);
                    return;
                }
                if (TextUtils.isEmpty(cofimpwd.getText().toString())) {
                    ToastUtil.show("请输入确认密码！", 25);
                    return;
                }
                if (!pwd.getText().toString().equals(cofimpwd.getText().toString())) {
                    ToastUtil.show("密码输入不一致！", 25);
                    return;
                }
                register(getIntent().getExtras().getString("phone"), edWriteCode.getText().toString(), MD5Util.md5(pwd.getText().toString()));

                break;
        }
    }

    public void register(String mobile, String code, String pwd) {
        ApiInterface.ApiFactory.createApi().register(mobile, code, pwd).enqueue(new Callback<SendSmsEntity>() {
            @Override
            public void onResponse(Call<SendSmsEntity> call, Response<SendSmsEntity> response) {
                if (response.body().getFlag() == 1) {
                    ToastUtil.show("注册成功!",25);
                    startfinishActivity(LoginActivity.class, null);
                }else {
                        ToastUtil.showShort(response.body().getMsg());
                    return;
                }
            }

            @Override
            public void onFailure(Call<SendSmsEntity> call, Throwable t) {

            }
        });
    }

}
