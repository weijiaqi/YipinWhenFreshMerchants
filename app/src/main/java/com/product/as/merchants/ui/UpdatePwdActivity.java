package com.product.as.merchants.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.meikoz.core.util.MD5Util;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.entity.ExpressListEntity;
import com.product.as.merchants.entity.SendSmsEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;

import java.io.IOException;
import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//修改密码
public class UpdatePwdActivity extends BaseActivity {

    @Bind(R.id.cofimupdate)
    TextView cofimupdate;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.Oldpassword)
    EditText Oldpassword;
    @Bind(R.id.Newpassword)
    EditText Newpassword;
    @Bind(R.id.CofimNewpassword)
    EditText CofimNewpassword;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        tvTitles.setText("修改密码");
        cofimupdate.setOnClickListener(v -> {
            if (TextUtils.isEmpty(Oldpassword.getText().toString())) {
                ToastUtil.show("输入旧密码！", 25);
                return;
            }
            if (TextUtils.isEmpty(Newpassword.getText().toString())) {
                ToastUtil.show("输入新密码！", 25);
                return;
            }
            if (TextUtils.isEmpty(CofimNewpassword.getText().toString())) {
                ToastUtil.show("确认新密码！", 25);
                return;
            }

            if (!Newpassword.getText().toString().equals(CofimNewpassword.getText().toString())) {
                ToastUtil.show("两次密码输入不一致！", 25);
                return;
            }
            pwdreset(String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")), MD5Util.md5(Oldpassword.getText().toString()), MD5Util.md5(Newpassword.getText().toString()));
        });
    }


    public void pwdreset(String uid, String Oldpassword, String Newpassword) {
        ApiInterface.ApiFactory.createApi().pwdreset(uid, Oldpassword, Newpassword).enqueue(new Callback<SendSmsEntity>() {
            @Override
            public void onResponse(Call<SendSmsEntity> call, Response<SendSmsEntity> response) {
                if (response.body().getFlag() == 1) {
                    ToastUtil.show("修改成功", 25);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SendSmsEntity> call, Throwable t) {

            }
        });
    }
}
